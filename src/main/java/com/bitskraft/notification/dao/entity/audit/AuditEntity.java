package com.bitskraft.notification.dao.entity.audit;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.bitskraft.notification.common.enums.RequestType;
import org.hibernate.annotations.GenericGenerator;

/** created on: 10/2/23 created by: Anil Maharjan */
@Entity
public class AuditEntity {
  @Id
  @GeneratedValue(generator = "custom")
  @GenericGenerator(name = "custom", strategy = "com.bitskraft.notification.util.UUIDGenerator")
  private String id;

  private String newData;
  private String oldData;
  private RequestType requestType;
  private String createdOn;
  private String createdBy;
  private String entity;
}
