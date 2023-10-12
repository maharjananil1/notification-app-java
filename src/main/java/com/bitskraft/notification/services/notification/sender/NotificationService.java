package com.bitskraft.notification.services.notification.sender;

import com.bitskraft.notification.services.NotificationRequest;
import com.bitskraft.notification.domain.BaseResponse;

/** created on: 10/2/23 created by: Anil Maharjan */
public interface NotificationService {
  BaseResponse send(NotificationRequest request);
}
