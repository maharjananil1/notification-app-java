package com.bitskraft.notification.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/** created on: 10/2/23 created by: Anil Maharjan */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse extends com.bitskraft.notification.common.basedomain.BaseResponse {
  private String id;
}
