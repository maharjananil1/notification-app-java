package com.bitskraft.notification.services.notification.sender.sms.impl;

import com.bitskraft.notification.common.enums.NotificationType;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.InvalidException;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.NotFoundException;
import com.bitskraft.notification.dao.entity.notifications.NotificationSettingEntity;
import com.bitskraft.notification.dao.entity.template.TemplateEntity;
import com.bitskraft.notification.dao.repo.NotificationRepository;
import com.bitskraft.notification.dao.repo.NotificationSettingsRepository;
import com.bitskraft.notification.dao.repo.TemplateRepository;
import com.bitskraft.notification.domain.BaseResponse;
import com.bitskraft.notification.domain.notification.NotificationDTO;
import com.bitskraft.notification.services.NotificationRequest;
import com.bitskraft.notification.services.notification.sender.sms.SmsService;
import com.bitskraft.notification.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * created on: 10/2/23 created by: Anil Maharjan
 */
@Service
public class SmsServiceImpl implements SmsService {
    @Autowired
    private NotificationSettingsRepository notificationSettingsRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private TemplateRepository templateRepository;

    @Value("${user-defined.custom.timeout}")
    private int timeout;

    @Value("${user-defined.custom.is_save}")
    private boolean isSave;

    @Override
    public BaseResponse send(NotificationRequest request) {
        NotificationSettingEntity notificationSetting =
                notificationSettingsRepository
                        .findByNotificationTypeAndDefaultOptionAndDeletedIsFalse(NotificationType.SMS, true)
                        .orElseThrow(() -> new NotFoundException("Notification Setting not found."));
        NotificationDTO notificationDTO = (NotificationDTO) request;
        if (!Util.isBlankOrNull(notificationDTO.getTemplateId())) {
            TemplateEntity template =
                    this.templateRepository
                            .findByTemplateIdAndNotificationTypeAndDeletedIsFalse(
                                    notificationDTO.getTemplateId(), NotificationType.SMS)
                            .orElseThrow(() -> new NotFoundException("Template not found"));
            Util.validateAllDynamicFieldsExists(
                    Util.decodeString(template.getBody()), notificationDTO.getData());
            notificationDTO.setBody(
                    Util.replaceField(Util.decodeString(template.getBody()), notificationDTO.getData()));
            notificationDTO.setTitle(template.getTitle());
        }
        switch (notificationSetting.getServiceType()) {
            case TWILIO:
                return new TwilioSmsServiceImpl(notificationRepository, notificationSetting, isSave)
                        .send(notificationDTO);
            case SPARROW:
                return new SparrowSmsServiceImpl(
                        notificationRepository, notificationSetting, timeout, isSave)
                        .send(notificationDTO);
            default:
                throw new InvalidException("Invalid service type");
        }
    }
}
