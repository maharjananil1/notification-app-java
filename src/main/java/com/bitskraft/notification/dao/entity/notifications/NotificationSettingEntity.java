package com.bitskraft.notification.dao.entity.notifications;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.bitskraft.notification.common.enums.NotificationType;
import com.bitskraft.notification.common.enums.ServiceType;
import com.bitskraft.notification.common.interfaces.AbstractEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/** created on: 10/2/23 created by: Anil Maharjan */
@Data
@Entity
public class NotificationSettingEntity extends AbstractEntity {
  @Id
  @GeneratedValue(generator = "custom")
  @GenericGenerator(name = "custom", strategy = "com.bitskraft.notification.util.UUIDGenerator")
  private String id;

  private String name;
  private String sender;

  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;

  @Enumerated(EnumType.STRING)
  private ServiceType serviceType;

  private boolean defaultOption;
  private String provider;
  private String host;
  private String port;
  private String username;
  private String password;
  private String protocol;
  private boolean useSSL;
  private String apiKey;
  private String apiUrl;
}
