package com.bitskraft.notification.controllers;

import com.bitskraft.notification.domain.BaseResponse;
import com.bitskraft.notification.domain.notificationsetting.NotificationSettingDTO;
import com.bitskraft.notification.services.notification.setting.NotificationSettingService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** created on: 10/6/23 created by: Anil Maharjan */
@RestController
@RequestMapping(value = "notification/setting")
@Api(
    value = "Notification Setting",
    tags = {"Notification Setting"})
public class NotificationSettingController {
  @Autowired private NotificationSettingService notificationSettingService;

  @PostMapping
  @ApiOperation("REST API to save Notification Settings")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "notificationSettingDTO",
        dataType = "NotificationSettingDTO",
        paramType = "body",
        value = "Notification Setting Dto")
  })
  public ResponseEntity<BaseResponse> saveSettings(
      @RequestBody NotificationSettingDTO notificationSettingDTO) {
    BaseResponse baseResponse = notificationSettingService.save(notificationSettingDTO);
    return new ResponseEntity<>(baseResponse, baseResponse.getStatus());
  }

  @GetMapping(value = "{id}")
  @ApiOperation("REST API to get notification setting details")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", dataType = "String", paramType = "path", value = "id"),
  })
  public ResponseEntity<BaseResponse> getSettingDetail(@PathVariable("id") String id) {
    BaseResponse baseResponse = this.notificationSettingService.getDetail(id);
    return new ResponseEntity<>(baseResponse, baseResponse.getStatus());
  }

  @GetMapping
  @ApiOperation("REST API to get all notification settings")
  public ResponseEntity<BaseResponse> getAllSettings() {
    BaseResponse baseResponse = this.notificationSettingService.getAll();
    return new ResponseEntity<>(baseResponse, baseResponse.getStatus());
  }

  @PutMapping(value = "{id}")
  @ApiOperation("REST API to update notification setting")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", dataType = "String", paramType = "path", value = "id"),
    @ApiImplicitParam(
        name = "notificationSettingDTO",
        dataType = "NotificationSettingDTO",
        paramType = "body",
        value = "Notification Setting DTO"),
  })
  public ResponseEntity<BaseResponse> updateSetting(
      @PathVariable("id") String id,
      @Valid @RequestBody NotificationSettingDTO notificationSettingDTO) {
    BaseResponse baseResponse = this.notificationSettingService.update(id, notificationSettingDTO);
    return new ResponseEntity<>(baseResponse, baseResponse.getStatus());
  }

  @DeleteMapping(value = "{id}")
  @ApiOperation("REST API to delete notification setting")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", dataType = "String", paramType = "path", value = "id"),
  })
  public ResponseEntity<BaseResponse> deleteSetting(@PathVariable("id") String id) {
    BaseResponse baseResponse = this.notificationSettingService.delete(id);
    return new ResponseEntity<>(baseResponse, baseResponse.getStatus());
  }
}
