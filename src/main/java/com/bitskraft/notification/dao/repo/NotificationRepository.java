package com.bitskraft.notification.dao.repo;

import com.bitskraft.notification.dao.entity.notifications.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** created on: 10/2/23 created by: Anil Maharjan */
@Repository
public interface NotificationRepository extends JpaRepository<NotificationEntity, String> {}
