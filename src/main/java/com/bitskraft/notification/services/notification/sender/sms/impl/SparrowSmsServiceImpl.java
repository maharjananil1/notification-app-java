package com.bitskraft.notification.services.notification.sender.sms.impl;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import com.bitskraft.notification.common.enums.NotificationType;
import com.bitskraft.notification.common.enums.Status;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.NotificationFailed;
import com.bitskraft.notification.dao.entity.notifications.NotificationEntity;
import com.bitskraft.notification.dao.entity.notifications.NotificationSettingEntity;
import com.bitskraft.notification.dao.repo.NotificationRepository;
import com.bitskraft.notification.domain.BaseResponse;
import com.bitskraft.notification.domain.notification.NotificationDTO;
import com.bitskraft.notification.services.NotificationRequest;
import com.bitskraft.notification.services.notification.sender.NotificationHelper;
import com.bitskraft.notification.services.notification.sender.sms.SmsService;
import com.bitskraft.notification.util.Jsons;
import com.bitskraft.notification.util.Util;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/** created on: 10/12/23 created by: Anil Maharjan */
@RequiredArgsConstructor
public class SparrowSmsServiceImpl implements SmsService {
  private final NotificationRepository notificationRepository;
  private final NotificationSettingEntity notificationSetting;
  private final int timeout;
  private final boolean isSave;

  @Override
  public BaseResponse send(NotificationRequest request) {
    NotificationDTO notificationRequest = (NotificationDTO) request;
    Util.validatePhoneNumbers(notificationRequest.getTo());
    Map<String, String> sparrowRequest = new HashMap<>();
    sparrowRequest.put("token", notificationSetting.getPassword());
    sparrowRequest.put("from", notificationSetting.getUsername());
    sparrowRequest.put("to", Util.commaSeparatedPhoneNumber(notificationRequest.getTo()));
    sparrowRequest.put("text", notificationRequest.getBody());
    HttpClient httpClient = HttpClient.newHttpClient();
    HttpRequest httpRequest =
        HttpRequest.newBuilder()
            .uri(URI.create(notificationSetting.getApiUrl()))
            .timeout(Duration.ofSeconds(timeout))
            .header("Content-Type", "application/json")
            .POST(HttpRequest.BodyPublishers.ofString(Jsons.toJsonObj(sparrowRequest)))
            .build();
    NotificationEntity notification =
        NotificationHelper.getNotificationEntity(
            notificationRequest, notificationSetting.getSender(), NotificationType.SMS);
    if (isSave) notification = this.notificationRepository.save(notification);
    try {
      HttpResponse<String> httpResponse =
          httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
      if (httpResponse.statusCode() != 200) {
        throw new NotificationFailed("Failed to send SMS");
      }
    } catch (Exception ex) {
      notification.setStatus(Status.FAILED);
      notification.setReason(ex.getMessage());
      if (isSave) this.notificationRepository.save(notification);
      throw new NotificationFailed("Failed to send SMS");
    }
    notification.setStatus(Status.SUCCESS);
    if (isSave) this.notificationRepository.save(notification);
    BaseResponse baseResponse = new BaseResponse();
    baseResponse.setMessage("Sent Successfully");
    baseResponse.setStatus(HttpStatus.OK);
    return baseResponse;
  }
}
