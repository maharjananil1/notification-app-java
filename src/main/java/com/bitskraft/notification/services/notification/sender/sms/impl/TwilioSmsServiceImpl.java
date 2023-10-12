package com.bitskraft.notification.services.notification.sender.sms.impl;

import com.bitskraft.notification.common.enums.NotificationType;
import com.bitskraft.notification.common.enums.Status;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.NotificationFailed;
import com.bitskraft.notification.dao.entity.notifications.NotificationEntity;
import com.bitskraft.notification.dao.entity.notifications.NotificationSettingEntity;
import com.bitskraft.notification.dao.repo.NotificationRepository;
import com.bitskraft.notification.domain.BaseResponse;
import com.bitskraft.notification.domain.notification.NotificationDTO;
import com.bitskraft.notification.services.NotificationRequest;
import com.bitskraft.notification.services.notification.sender.NotificationHelper;
import com.bitskraft.notification.util.Util;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.RequiredArgsConstructor;
import com.bitskraft.notification.services.notification.sender.sms.SmsService;
import org.springframework.http.HttpStatus;

/** created on: 10/11/23 created by: Anil Maharjan */
@RequiredArgsConstructor
public class TwilioSmsServiceImpl implements SmsService {
  private final NotificationRepository notificationRepository;
  private final NotificationSettingEntity notificationSetting;
  private final boolean isSave;

  @Override
  public BaseResponse send(NotificationRequest request) {
    NotificationDTO notificationRequest = (NotificationDTO) request;
    Util.validatePhoneNumbersTwilio(((NotificationDTO) request).getTo());
    NotificationEntity notification =
        NotificationHelper.getNotificationEntity(
            notificationRequest, notificationSetting.getSender(), NotificationType.SMS);
    if (isSave) notification = this.notificationRepository.save(notification);
    Twilio.init(notificationSetting.getUsername(), notificationSetting.getPassword());
    try {
      notificationRequest
          .getTo()
          .forEach(
              receiver ->
                  Message.creator(
                          new com.twilio.type.PhoneNumber(receiver),
                          new com.twilio.type.PhoneNumber(notificationSetting.getSender()),
                          notificationRequest.getBody())
                      .create());
    } catch (Exception ex) {
      notification.setStatus(Status.FAILED);
      notification.setReason(ex.getMessage());
      if (isSave) this.notificationRepository.save(notification);
      throw new NotificationFailed("Failed to send SMS");
    }
    notification.setStatus(Status.SUCCESS);
    if (isSave) this.notificationRepository.save(notification);
    BaseResponse baseResponse = new BaseResponse();
    baseResponse.setMessage("Sent Successfully");
    baseResponse.setStatus(HttpStatus.OK);
    return baseResponse;
  }
}
