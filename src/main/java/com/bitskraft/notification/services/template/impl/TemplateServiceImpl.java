package com.bitskraft.notification.services.template.impl;

import java.util.List;
import java.util.Optional;

import com.bitskraft.notification.services.template.TemplateService;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.DataMissingException;
import com.bitskraft.notification.common.exceptionhandlers.exceptions.NotFoundException;
import com.bitskraft.notification.dao.entity.template.TemplateEntity;
import com.bitskraft.notification.dao.entity.template.TemplateEntityMapper;
import com.bitskraft.notification.dao.repo.TemplateRepository;
import com.bitskraft.notification.domain.BaseResponse;
import com.bitskraft.notification.domain.template.TemplateDTO;
import com.bitskraft.notification.domain.template.TemplateResponse;
import com.bitskraft.notification.util.Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

/** created on: 10/6/23 created by: Anil Maharjan */
@Service
public class TemplateServiceImpl implements TemplateService {
  @Autowired private TemplateRepository templateRepository;
  @Autowired private TemplateEntityMapper templateEntityMapper;

  @Override
  public BaseResponse save(TemplateDTO templateDTO) {
    TemplateEntity entity =
        this.templateRepository.save(this.templateEntityMapper.toEntity(templateDTO));
    BaseResponse baseResponse = new BaseResponse();
    baseResponse.setId(entity.getId());
    baseResponse.setMessage("Successful");
    baseResponse.setStatus(HttpStatus.CREATED);
    return baseResponse;
  }

  @Override
  public BaseResponse getDetail(String id) {
    Optional<TemplateEntity> templateEntity = this.templateRepository.findByIdAndDeletedIsFalse(id);
    if (templateEntity.isEmpty()) throw new NotFoundException("Template not found");
    TemplateResponse response = new TemplateResponse();
    response.setId(templateEntity.get().getId());
    response.setTemplate(this.templateEntityMapper.toDto(templateEntity.get()));
    response.setMessage("Successful");
    response.setStatus(HttpStatus.OK);
    return response;
  }

  @Override
  public BaseResponse getAll() {
    List<TemplateEntity> templateEntities = this.templateRepository.findAll();
    TemplateResponse response = new TemplateResponse();
    response.setTemplateList(this.templateEntityMapper.toDtos(templateEntities));
    response.setMessage("Successful");
    response.setStatus(HttpStatus.OK);
    return response;
  }

  @Override
  public BaseResponse update(String id, TemplateDTO templateDTO) {
    if (Util.isBlankOrNull(id)) {
      throw new DataMissingException("Id missing");
    }
    Optional<TemplateEntity> templateEntity = this.templateRepository.findByIdAndDeletedIsFalse(id);
    if (templateEntity.isEmpty()) throw new NotFoundException("Template not found");
    templateEntity.get().setNotificationType(templateDTO.getNotificationType());
    templateEntity.get().setBody(templateDTO.getBody());
    templateEntity.get().setTitle(templateDTO.getTitle());
    templateEntity.get().setActive(templateDTO.isActive());
    TemplateEntity updateEntity = this.templateRepository.save(templateEntity.get());
    TemplateResponse response = new TemplateResponse();
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
    Optional<TemplateEntity> templateEntity = this.templateRepository.findByIdAndDeletedIsFalse(id);
    if (templateEntity.isEmpty()) throw new NotFoundException("Template not found");
    templateEntity.get().setActive(false);
    templateEntity.get().setDeleted(true);
    TemplateEntity updateEntity = this.templateRepository.save(templateEntity.get());
    TemplateResponse response = new TemplateResponse();
    response.setId(updateEntity.getId());
    response.setMessage("Successful");
    response.setStatus(HttpStatus.OK);
    return response;
  }
}
