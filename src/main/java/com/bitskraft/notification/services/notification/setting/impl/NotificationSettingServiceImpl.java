package com.bitskraft.notification.services.notification.setting.impl;

import java.util.List;
import java.util.Optional;

import com.bitskraft.notification.common.exceptionhandlers.exceptions.AlreadyPresentException;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.DataMissingException;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.NotFoundException;
import com.bitskraft.notification.dao.entity.notifications.NotificationSettingEntity;
import com.bitskraft.notification.dao.entity.notifications.NotificationSettingMapper;
import com.bitskraft.notification.dao.repo.NotificationSettingsRepository;
import com.bitskraft.notification.domain.BaseResponse;
import com.bitskraft.notification.domain.notificationsetting.NotificationSettingDTO;
import com.bitskraft.notification.domain.notificationsetting.NotificationSettingResponse;
import com.bitskraft.notification.services.notification.setting.NotificationSettingService;
import com.bitskraft.notification.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** created on: 10/6/23 created by: Anil Maharjan */
@Service
public class NotificationSettingServiceImpl implements NotificationSettingService {
  @Autowired private NotificationSettingsRepository notificationSettingsRepository;
  @Autowired private NotificationSettingMapper notificationSettingMapper;

  @Override
  public BaseResponse save(NotificationSettingDTO notificationSettingDTO) {
    Optional<NotificationSettingEntity> defaultNotificationSetting =
        this.notificationSettingsRepository.findByNotificationTypeAndDefaultOptionAndDeletedIsFalse(
            notificationSettingDTO.getNotificationType(), true);
    if (defaultNotificationSetting.isPresent()) {
      throw new AlreadyPresentException("Default setting already exists");
    }
    NotificationSettingEntity entity =
        this.notificationSettingsRepository.save(
            notificationSettingMapper.toEntity(notificationSettingDTO));
    BaseResponse baseResponse = new BaseResponse();
    baseResponse.setId(entity.getId());
    baseResponse.setMessage("Success");
    baseResponse.setStatus(HttpStatus.CREATED);
    return baseResponse;
  }

  @Override
  public BaseResponse getDetail(String id) {
    NotificationSettingEntity notificationSettingEntity =
        this.notificationSettingsRepository
            .findByIdAndDeletedIsFalse(id)
            .orElseThrow(() -> new NotFoundException("Notification setting not found"));
    NotificationSettingResponse response = new NotificationSettingResponse();
    response.setId(notificationSettingEntity.getId());
    response.setNotificationSetting(
        this.notificationSettingMapper.toDto(notificationSettingEntity));
    response.setMessage("Successful");
    response.setStatus(HttpStatus.OK);
    return response;
  }

  @Override
  public BaseResponse getAll() {
    List<NotificationSettingEntity> templateEntities =
        this.notificationSettingsRepository.findAll();
    NotificationSettingResponse response = new NotificationSettingResponse();
    response.setNotificationSettingList(this.notificationSettingMapper.toDtos(templateEntities));
    response.setMessage("Successful");
    response.setStatus(HttpStatus.OK);
    return response;
  }

  @Override
  public BaseResponse update(String id, NotificationSettingDTO notificationSettingDTO) {
    if (Util.isBlankOrNull(id)) {
      throw new DataMissingException("Id missing");
    }
    if (notificationSettingDTO.isDefaultOption()) {
      Optional<NotificationSettingEntity> defaultNotificationSetting =
          this.notificationSettingsRepository
              .findByNotificationTypeAndDefaultOptionAndDeletedIsFalse(
                  notificationSettingDTO.getNotificationType(), true);
      if (defaultNotificationSetting.isPresent()) {
        throw new AlreadyPresentException("Default setting already exists");
      }
    }
    NotificationSettingEntity notificationSettingEntity =
        this.notificationSettingsRepository
            .findByIdAndDeletedIsFalse(id)
            .orElseThrow(() -> new NotFoundException("Notification setting not found"));
    notificationSettingEntity.setName(notificationSettingDTO.getName());
    notificationSettingEntity.setSender(notificationSettingDTO.getSender());
    notificationSettingEntity.setNotificationType(notificationSettingDTO.getNotificationType());
    notificationSettingEntity.setServiceType(notificationSettingDTO.getServiceType());
    notificationSettingEntity.setDefaultOption(notificationSettingDTO.isDefaultOption());
    notificationSettingEntity.setProvider(notificationSettingDTO.getProvider());
    notificationSettingEntity.setHost(notificationSettingDTO.getHost());
    notificationSettingEntity.setPort(notificationSettingDTO.getPort());
    notificationSettingEntity.setUsername(notificationSettingDTO.getUsername());
    notificationSettingEntity.setPassword(notificationSettingDTO.getPassword());
    notificationSettingEntity.setProtocol(notificationSettingDTO.getProtocol());
    notificationSettingEntity.setUseSSL(notificationSettingDTO.isUseSSL());
    notificationSettingEntity.setApiKey(notificationSettingDTO.getApiKey());
    notificationSettingEntity.setApiUrl(notificationSettingDTO.getApiUrl());
    notificationSettingEntity.setActive(notificationSettingDTO.isActive());
    NotificationSettingEntity updateEntity =
        this.notificationSettingsRepository.save(notificationSettingEntity);
    NotificationSettingResponse response = new NotificationSettingResponse();
    response.setId(updateEntity.getId());
    response.setMessage("Successful");
    response.setStatus(HttpStatus.OK);
    return response;
  }

  @Override
  public BaseResponse delete(String id) {
    if (Util.isBlankOrNull(id)) {
      throw new DataMissingException("Id missing");
    }
    NotificationSettingEntity notificationSettingEntity =
        this.notificationSettingsRepository
            .findByIdAndDeletedIsFalse(id)
            .orElseThrow(() -> new NotFoundException("Notification setting not found"));
    notificationSettingEntity.setActive(false);
    notificationSettingEntity.setDeleted(true);
    NotificationSettingEntity updateEntity =
        this.notificationSettingsRepository.save(notificationSettingEntity);
    NotificationSettingResponse response = new NotificationSettingResponse();
    response.setId(updateEntity.getId());
    response.setMessage("Successful");
    response.setStatus(HttpStatus.OK);
    return response;
  }
}
