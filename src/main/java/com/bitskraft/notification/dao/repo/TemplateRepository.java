package com.bitskraft.notification.dao.repo;

import java.util.Optional;

import com.bitskraft.notification.common.enums.NotificationType;
import com.bitskraft.notification.dao.entity.template.TemplateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** created on: 10/6/23 created by: Anil Maharjan */
@Repository
public interface TemplateRepository extends JpaRepository<TemplateEntity, String> {
  Optional<TemplateEntity> findByTemplateIdAndNotificationTypeAndDeletedIsFalse(
      String templateId, NotificationType notificationType);

  Optional<TemplateEntity> findByIdAndDeletedIsFalse(String id);
}
