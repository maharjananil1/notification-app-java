package com.bitskraft.notification.common.exceptionhandlers;

import com.bitskraft.notification.common.exceptionhandlers.exceptions.AlreadyPresentException;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.DataMissingException;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.InvalidException;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.NotFoundException;
import com.bitskraft.notification.common.basedomain.BaseResponse;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.NotificationFailed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/** created on: 10/6/23 created by: Anil Maharjan */
@ControllerAdvice
public class ApiExceptionHandlers {
  @ExceptionHandler(value = {NotFoundException.class})
  public ResponseEntity<BaseResponse> handleNotFoundException(Exception ex) {
    BaseResponse baseResponse =
        BaseResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).build();
    return new ResponseEntity<>(baseResponse, baseResponse.getStatus());
  }

  @ExceptionHandler(
      value = {
        AlreadyPresentException.class,
        NotificationFailed.class,
        InvalidException.class,
        DataMissingException.class,
        Exception.class
      })
  public ResponseEntity<BaseResponse> handleNotificationException(Exception ex) {
    BaseResponse baseResponse =
        BaseResponse.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).build();
    return new ResponseEntity<>(baseResponse, baseResponse.getStatus());
  }
}
