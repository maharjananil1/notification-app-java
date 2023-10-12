package com.bitskraft.notification.domain.notificationsetting;

import java.util.List;
import lombok.Data;
import com.bitskraft.notification.domain.BaseResponse;

/** created on: 10/9/23 created by: Anil Maharjan */
@Data
public class NotificationSettingResponse extends BaseResponse {
  private NotificationSettingDTO notificationSetting;
  private List<NotificationSettingDTO> notificationSettingList;
}
