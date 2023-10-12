package com.bitskraft.notification.util;

import java.io.Serializable;
import java.util.UUID;
import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;

/** created on: 10/2/23 created by: Anil Maharjan */
public class UUIDGenerator implements IdentifierGenerator {
  @Override
  public Serializable generate(
      SharedSessionContractImplementor sharedSessionContractImplementor, Object o)
      throws HibernateException {
    return UUID.randomUUID().toString().replace("-", "");
  }
}
