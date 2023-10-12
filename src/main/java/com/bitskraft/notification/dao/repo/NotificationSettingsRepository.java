package com.bitskraft.notification.dao.repo;

import java.util.Optional;

import com.bitskraft.notification.common.enums.NotificationType;
import com.bitskraft.notification.dao.entity.notifications.NotificationSettingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** created on: 10/2/23 created by: Anil Maharjan */
@Repository
public interface NotificationSettingsRepository
    extends JpaRepository<NotificationSettingEntity, String> {
  Optional<NotificationSettingEntity> findByIdAndDeletedIsFalse(String id);

  Optional<NotificationSettingEntity> findByNotificationTypeAndDefaultOptionAndDeletedIsFalse(
          NotificationType notificationType, boolean isDefault);
}
