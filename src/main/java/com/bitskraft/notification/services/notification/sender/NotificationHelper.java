package com.bitskraft.notification.services.notification.sender;

import com.bitskraft.notification.common.enums.NotificationType;
import com.bitskraft.notification.common.enums.Status;
import com.bitskraft.notification.dao.entity.notifications.NotificationEntity;
import com.bitskraft.notification.domain.notification.NotificationDTO;
import com.bitskraft.notification.util.Jsons;

/** created on: 10/12/23 created by: Anil Maharjan */
public class NotificationHelper {
  public static NotificationEntity getNotificationEntity(
      NotificationDTO notificationRequest, String sender, NotificationType notificationType) {
    NotificationEntity notification = new NotificationEntity();
    notification.setMessage(notificationRequest.getBody());
    notification.setReceivers(Jsons.toJsonObj(notificationRequest.getTo()));
    notification.setSender(sender);
    notification.setTemplateId(notificationRequest.getTemplateId());
    notification.setNotificationType(notificationType);
    notification.setStatus(Status.PENDING);
    return notification;
  }
}
