package com.bitskraft.notification.services.notification.sender.mail;

import com.bitskraft.notification.domain.BaseResponse;
import com.bitskraft.notification.services.NotificationRequest;

/** created on: 10/9/23 created by: Anil Maharjan */
public interface EmailService {
  BaseResponse send(NotificationRequest request);
}
