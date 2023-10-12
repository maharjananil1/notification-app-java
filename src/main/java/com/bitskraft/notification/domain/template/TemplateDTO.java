package com.bitskraft.notification.domain.template;

import lombok.Builder;
import lombok.Data;
import com.bitskraft.notification.common.enums.NotificationType;

/** created on: 10/6/23 created by: Anil Maharjan */
@Data
@Builder
public class TemplateDTO {
  private String id;
  private String templateId;
  private String body;
  private String title;
  private NotificationType notificationType;
  private String createdBy;
  private String lastModifiedBy;
  private String deletedBy;
  private boolean active;
  private boolean deleted;
}
