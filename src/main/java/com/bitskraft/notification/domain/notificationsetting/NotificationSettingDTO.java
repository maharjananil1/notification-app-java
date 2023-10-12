package com.bitskraft.notification.domain.notificationsetting;

import com.fasterxml.jackson.annotation.JsonAlias;
import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import com.bitskraft.notification.common.enums.NotificationType;
import com.bitskraft.notification.common.enums.ServiceType;

/** created on: 10/6/23 created by: Anil Maharjan */
@Data
@Builder
public class NotificationSettingDTO {
  private String id;

  @NotBlank(message = "Name is required")
  private String name;

  @NotBlank(message = "Sender is required")
  private String sender;

  @NotBlank(message = "Notification type is required")
  private NotificationType notificationType;

  @NotBlank(message = "Service type is required")
  private ServiceType serviceType;

  @JsonAlias(value = "isDefault")
  private boolean defaultOption = false;

  @NotBlank(message = "Provider is required")
  private String provider;

  @NotBlank(message = "Host is required")
  private String host;

  @NotBlank(message = "Port is required")
  private String port;

  @NotBlank(message = "Username is required")
  private String username;

  @NotBlank(message = "Password is required")
  private String password;

  @NotBlank(message = "Protocol is required")
  private String protocol;

  @NotBlank(message = "SSL is required")
  private boolean useSSL;

  private String apiKey;
  private String apiUrl;
  private String createdBy;
  private String lastModifiedBy;
  private String deletedBy;
  private boolean active;
  private boolean deleted;
}
