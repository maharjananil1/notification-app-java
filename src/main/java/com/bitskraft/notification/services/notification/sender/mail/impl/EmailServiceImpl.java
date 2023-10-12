package com.bitskraft.notification.services.notification.sender.mail.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.bitskraft.notification.common.constants.AppConfigs;
import com.bitskraft.notification.common.enums.NotificationType;
import com.bitskraft.notification.common.enums.Status;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.NotFoundException;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.NotificationFailed;
import com.bitskraft.notification.dao.entity.notifications.NotificationEntity;
import com.bitskraft.notification.dao.entity.notifications.NotificationSettingEntity;
import com.bitskraft.notification.dao.entity.template.TemplateEntity;
import com.bitskraft.notification.dao.repo.NotificationRepository;
import com.bitskraft.notification.dao.repo.NotificationSettingsRepository;
import com.bitskraft.notification.dao.repo.TemplateRepository;
import com.bitskraft.notification.domain.BaseResponse;
import com.bitskraft.notification.domain.notification.NotificationDTO;
import com.bitskraft.notification.services.NotificationRequest;
import com.bitskraft.notification.services.notification.sender.NotificationHelper;
import com.bitskraft.notification.services.notification.sender.mail.EmailService;
import com.bitskraft.notification.util.Jsons;
import com.bitskraft.notification.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/** created on: 10/2/23 created by: Anil Maharjan */
@Service
public class EmailServiceImpl implements EmailService {
  @Autowired private NotificationRepository notificationRepository;
  @Autowired private NotificationSettingsRepository notificationSettingsRepository;
  @Autowired private TemplateRepository templateRepository;

  @Value("${user-defined.custom.is_save}")
  private boolean isSave;

  @Override
  public BaseResponse send(NotificationRequest request) {
    NotificationDTO notificationDTO = (NotificationDTO) request;
    NotificationSettingEntity notificationSetting =
        notificationSettingsRepository
            .findByNotificationTypeAndDefaultOptionAndDeletedIsFalse(NotificationType.MAIL, true)
            .orElseThrow(() -> new NotFoundException("Notification Setting not found."));
    Properties properties = this.getProperties(notificationSetting);
    Authenticator auth = this.getAuthenticator(notificationSetting);
    Session session = Session.getInstance(properties, auth);
    NotificationEntity notification =
        NotificationHelper.getNotificationEntity(
            notificationDTO, notificationSetting.getSender(), NotificationType.MAIL);
    if (isSave) notification = this.notificationRepository.save(notification);
    this.sendEmail(session, notificationDTO, notificationSetting.getSender(), notification);
    notification.setStatus(Status.SUCCESS);
    if (isSave) this.notificationRepository.save(notification);
    BaseResponse baseResponse = new BaseResponse();
    baseResponse.setMessage("Sent Successfully");
    baseResponse.setStatus(HttpStatus.OK);
    return baseResponse;
  }

  private Properties getProperties(NotificationSettingEntity notificationSetting) {
    Properties properties = new Properties();
    properties.put("mail.smtp.host", notificationSetting.getHost());
    properties.put("mail.smtp.port", notificationSetting.getPort());
    properties.put("mail.smtp.auth", "true");
    properties.put("mail.smtp.starttls.enable", notificationSetting.isUseSSL());
    properties.put("mail.smtp.ssl.checkserveridentity", notificationSetting.isUseSSL());
    properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
    return properties;
  }

  private Authenticator getAuthenticator(NotificationSettingEntity notificationSetting) {
    Authenticator auth;
    auth =
        new Authenticator() {
          @Override
          protected PasswordAuthentication getPasswordAuthentication() {
            return new PasswordAuthentication(
                notificationSetting.getUsername(), notificationSetting.getPassword());
          }
        };
    return auth;
  }

  private void sendEmail(
      Session session,
      NotificationDTO notificationDTO,
      String from,
      NotificationEntity notification) {
    Set<String> receivers = new HashSet<>();
    if (!Util.isBlankOrNull(notificationDTO.getTemplateId())) {
      TemplateEntity template =
          this.templateRepository
              .findByTemplateIdAndNotificationTypeAndDeletedIsFalse(
                  notificationDTO.getTemplateId(), NotificationType.MAIL)
              .orElseThrow(() -> new NotFoundException("Template not found"));
      Util.validateAllDynamicFieldsExists(
          Util.decodeString(template.getBody()), notificationDTO.getData());
      notificationDTO.setBody(
          Util.replaceField(Util.decodeString(template.getBody()), notificationDTO.getData()));
      notificationDTO.setTitle(template.getTitle());
      notification.setTemplateId(template.getTemplateId());
    }
    try {
      MimeMessage msg = new MimeMessage(session);
      msg.addHeader("Content-type", "text/HTML; charset=UTF-8");
      msg.addHeader("format", "flowed");
      msg.addHeader("Content-Transfer-Encoding", "8bit");
      msg.setFrom(new InternetAddress(from, false));
      msg.setReplyTo(InternetAddress.parse(from, false));
      msg.setSubject(notificationDTO.getTitle(), "UTF-8");
      msg.setContent(notificationDTO.getBody(), "text/html");
      msg.setSentDate(new Date());
      if (notificationDTO.isHasAttachment()
          && notificationDTO.getAttachments() != null
          && !notificationDTO.getAttachments().isEmpty()) {
        Multipart multipart = new MimeMultipart();
        MimeBodyPart textBodyPart = new MimeBodyPart();
        textBodyPart.setContent(notificationDTO.getBody(), "text/html");
        multipart.addBodyPart(textBodyPart);
        for (MultipartFile attachment : notificationDTO.getAttachments()) {
          MimeBodyPart mimeBodyPart = new MimeBodyPart();
          InputStream inputStream = attachment.getInputStream();
          byte[] buffer = new byte[inputStream.available()];
          inputStream.read(buffer);
          String fileName =
              System.currentTimeMillis() + Math.random() + attachment.getOriginalFilename();
          File target = new File(AppConfigs.TEMP_LOCATION + fileName);

          try (OutputStream outStream = new FileOutputStream(target)) {
            outStream.write(buffer);
          }
          mimeBodyPart.attachFile(new File(AppConfigs.TEMP_LOCATION + fileName));
          multipart.addBodyPart(mimeBodyPart);
        }
        msg.setContent(multipart);
      }
      if (!notificationDTO.getCc().isEmpty()) {
        msg.setRecipients(
            Message.RecipientType.CC,
            InternetAddress.parse(Util.commaSeparatedAddress(notificationDTO.getCc()), false));
        receivers.addAll(notificationDTO.getCc());
      }
      if (!notificationDTO.getBcc().isEmpty()) {
        msg.setRecipients(
            Message.RecipientType.BCC,
            InternetAddress.parse(Util.commaSeparatedAddress(notificationDTO.getBcc()), false));
        receivers.addAll(notificationDTO.getBcc());
      }
      notificationDTO
          .getTo()
          .forEach(
              receiver -> {
                try {
                  msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(receiver));
                  receivers.add(receiver);
                  Transport.send(msg);
                } catch (MessagingException e) {
                  throw new NotificationFailed("Failed to send notifications.");
                }
              });
      notification.setReceivers(Jsons.toJsonObj(receivers));
    } catch (Exception e) {
      notification.setStatus(Status.FAILED);
      notification.setReason(e.getMessage());
      if (isSave) this.notificationRepository.save(notification);
      throw new NotificationFailed("Failed to send notifications.");
    }
  }
}
