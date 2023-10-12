package com.bitskraft.notification.common.interfaces;

/** created on: 9/29/23 created by: Anil Maharjan */
public interface Entity {
  String getId();

  String getCreatedBy();

  String getLastModifiedBy();

  String getDeletedBy();

  boolean isActive();

  boolean isDeleted();
}
