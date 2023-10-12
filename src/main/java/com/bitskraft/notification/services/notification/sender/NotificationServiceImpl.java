package com.bitskraft.notification.services.notification.sender;

import com.bitskraft.notification.services.NotificationRequest;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.InvalidException;
import com.bitskraft.notification.domain.BaseResponse;
import com.bitskraft.notification.services.notification.sender.mail.EmailService;
import com.bitskraft.notification.services.notification.sender.sms.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/** created on: 10/2/23 created by: Anil Maharjan */
@Service
public class NotificationServiceImpl implements NotificationService {
  @Autowired private SmsService smsService;
  @Autowired private EmailService emailService;

  @Override
  public BaseResponse send(NotificationRequest request) {
    if (request.getNotificationType() == null)
      throw new InvalidException("Notification type invalid");
    switch (request.getNotificationType()) {
      case SMS:
        return smsService.send(request);
      case MAIL:
        return emailService.send(request);
      default:
        throw new InvalidException("Notification type invalid");
    }
  }
}
