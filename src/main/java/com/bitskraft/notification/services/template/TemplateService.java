package com.bitskraft.notification.services.template;

import com.bitskraft.notification.domain.BaseResponse;
import com.bitskraft.notification.domain.template.TemplateDTO;

/** created on: 10/6/23 created by: Anil Maharjan */
public interface TemplateService {
  BaseResponse save(TemplateDTO templateDTO);

  BaseResponse getDetail(String id);

  BaseResponse getAll();

  BaseResponse update(String id, TemplateDTO templateDTO);

  BaseResponse delete(String id);
}
