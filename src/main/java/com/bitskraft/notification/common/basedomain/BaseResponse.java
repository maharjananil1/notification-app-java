package com.bitskraft.notification.common.basedomain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

/** created on: 10/6/23 created by: Anil Maharjan */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse {
  private String message;
  private HttpStatus status;
}
