package com.bitskraft.notification.dao.entity.template;

import com.bitskraft.notification.domain.template.TemplateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.stream.Collectors;

/** created on: 10/6/23 created by: Anil Maharjan */
@Mapper(componentModel = "spring")
public interface TemplateEntityMapper {
  TemplateDTO toDto(TemplateEntity templateEntity);

  @Mapping(target = "templateId", source = "templateId")
  TemplateEntity toEntity(TemplateDTO templateDTO);

  default List<TemplateDTO> toDtos(List<TemplateEntity> templateEntityList) {
    return templateEntityList.stream().map(this::toDto).collect(Collectors.toList());
  }
}
