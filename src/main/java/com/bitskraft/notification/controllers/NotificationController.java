package com.bitskraft.notification.controllers;

import java.util.List;

import com.bitskraft.notification.domain.BaseResponse;
import com.bitskraft.notification.domain.notification.NotificationDTO;
import com.bitskraft.notification.services.notification.sender.NotificationService;
import com.bitskraft.notification.util.Jsons;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

/** created on: 10/2/23 created by: Anil Maharjan */
@Controller
@RequestMapping(value = "notification")
public class NotificationController {
  @Autowired private NotificationService notificationService;

  @PostMapping(value = "send")
  public ResponseEntity<BaseResponse> sendNotification(
      @RequestPart("notificationBody") String request,
      @Nullable @RequestPart("attachments") List<MultipartFile> fileList) {
    NotificationDTO notificationDTO = Jsons.fromJson(request, NotificationDTO.class);
    if (fileList != null && !fileList.isEmpty()) {
      notificationDTO.setAttachments(fileList);
    }
    BaseResponse response = notificationService.send(notificationDTO);
    return new ResponseEntity<>(response, response.getStatus());
  }
}
