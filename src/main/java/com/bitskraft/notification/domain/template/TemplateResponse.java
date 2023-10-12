package com.bitskraft.notification.domain.template;

import java.util.List;
import lombok.Data;
import com.bitskraft.notification.domain.BaseResponse;

/** created on: 10/9/23 created by: Anil Maharjan */
@Data
public class TemplateResponse extends BaseResponse {
  private TemplateDTO template;
  private List<TemplateDTO> templateList;
}
