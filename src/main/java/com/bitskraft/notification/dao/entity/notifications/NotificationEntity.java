package com.bitskraft.notification.dao.entity.notifications;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.bitskraft.notification.common.enums.NotificationType;
import com.bitskraft.notification.common.enums.Status;
import com.bitskraft.notification.common.interfaces.AbstractEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/** created on: 10/2/23 created by: Anil Maharjan */
@Data
@Entity
public class NotificationEntity extends AbstractEntity {
  @Id
  @GeneratedValue(generator = "custom")
  @GenericGenerator(name = "custom", strategy = "com.bitskraft.notification.util.UUIDGenerator")
  private String id;

  @Column(columnDefinition = "text")
  private String message;

  private String receivers;
  private String sender;
  private NotificationType notificationType;

  @Enumerated(EnumType.STRING)
  private Status status;

  private String templateId;
  private String reason;
}
