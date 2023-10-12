package com.bitskraft.notification.services.notification.setting;

import com.bitskraft.notification.domain.BaseResponse;
import com.bitskraft.notification.domain.notificationsetting.NotificationSettingDTO;

/** created on: 10/6/23 created by: Anil Maharjan */
public interface NotificationSettingService {
  BaseResponse save(NotificationSettingDTO notificationSettingDTO);

  BaseResponse getDetail(String id);

  BaseResponse getAll();

  BaseResponse update(String id, NotificationSettingDTO notificationSettingDTO);

  BaseResponse delete(String id);
}
