package com.bitskraft.notification.domain.notification;

import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.bitskraft.notification.services.NotificationRequest;
import lombok.Data;
import com.bitskraft.notification.common.enums.NotificationType;
import org.springframework.web.multipart.MultipartFile;

/** created on: 10/2/23 created by: Anil Maharjan */
@Data
public class NotificationDTO implements NotificationRequest {
  @NotBlank(message = "Notification type is required")
  private NotificationType notificationType;

  @NotNull(message = "Notification to is required")
  private Set<String> to;

  private Set<String> bcc;
  private Set<String> cc;
  private String from;
  private boolean hasAttachment;
  private String title;
  private String body;
  private String templateId;
  private List<MultipartFile> attachments;
  private Map<String, String> data;
}
