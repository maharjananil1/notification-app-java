package com.bitskraft.notification.dao.entity.notifications;

import java.util.List;
import java.util.stream.Collectors;

import com.bitskraft.notification.domain.notificationsetting.NotificationSettingDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/** created on: 10/6/23 created by: Anil Maharjan */
@Mapper(componentModel = "spring")
public interface NotificationSettingMapper {
  NotificationSettingDTO toDto(NotificationSettingEntity notificationSettingEntity);

  @Mapping(target = "host", source = "host")
  NotificationSettingEntity toEntity(NotificationSettingDTO notificationSettingDTO);

  default List<NotificationSettingDTO> toDtos(
      List<NotificationSettingEntity> notificationSettingEntityList) {
    return notificationSettingEntityList.stream().map(this::toDto).collect(Collectors.toList());
  }
}
