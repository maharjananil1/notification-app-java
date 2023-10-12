package com.bitskraft.notification.dao.entity.template;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.bitskraft.notification.common.enums.NotificationType;
import com.bitskraft.notification.common.interfaces.AbstractEntity;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

/** created on: 9/29/23 created by: Anil Maharjan */
@Data
@Entity
public class TemplateEntity extends AbstractEntity {
  @Id
  @GeneratedValue(generator = "custom")
  @GenericGenerator(name = "custom", strategy = "com.bitskraft.notification.util.UUIDGenerator")
  private String id;

  @Column(unique = true)
  private String templateId;

  @Column(columnDefinition = "text")
  private String body;

  private String title;

  @Enumerated(EnumType.STRING)
  private NotificationType notificationType;
}
