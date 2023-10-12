package com.bitskraft.notification.services;

import com.bitskraft.notification.common.enums.NotificationType;

/** created on: 10/2/23 created by: Anil Maharjan */
public interface NotificationRequest {
  NotificationType getNotificationType();
}
