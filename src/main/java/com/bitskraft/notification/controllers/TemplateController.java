package com.bitskraft.notification.controllers;

import com.bitskraft.notification.domain.BaseResponse;
import com.bitskraft.notification.domain.template.TemplateDTO;
import com.bitskraft.notification.services.template.TemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "notification/template")
@Api(
    value = "Template",
    tags = {"Template"})
public class TemplateController {
  @Autowired private TemplateService templateService;

  @PostMapping
  @ApiOperation("REST API to save Template")
  @ApiImplicitParams({
    @ApiImplicitParam(
        name = "templateDto",
        dataType = "TemplateDTO",
        paramType = "body",
        value = "Template Dto")
  })
  public ResponseEntity<BaseResponse> saveTemplate(@RequestBody TemplateDTO templateDTO) {
    BaseResponse baseResponse = this.templateService.save(templateDTO);
    return new ResponseEntity<>(baseResponse, baseResponse.getStatus());
  }

  @GetMapping(value = "{id}")
  @ApiOperation("REST API to get Template details")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", dataType = "String", paramType = "path", value = "id"),
  })
  public ResponseEntity<BaseResponse> getTemplateDetail(@PathVariable("id") String id) {
    BaseResponse baseResponse = this.templateService.getDetail(id);
    return new ResponseEntity<>(baseResponse, baseResponse.getStatus());
  }

  @GetMapping
  @ApiOperation("REST API to get all Template")
  public ResponseEntity<BaseResponse> getAllTemplates() {
    BaseResponse baseResponse = this.templateService.getAll();
    return new ResponseEntity<>(baseResponse, baseResponse.getStatus());
  }

  @PutMapping(value = "{id}")
  @ApiOperation("REST API to update Template")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", dataType = "String", paramType = "path", value = "id"),
    @ApiImplicitParam(
        name = "templateDTO",
        dataType = "TemplateDTO",
        paramType = "body",
        value = "Template DTO"),
  })
  public ResponseEntity<BaseResponse> updateTemplate(
      @PathVariable("id") String id, @RequestBody TemplateDTO templateDTO) {
    BaseResponse baseResponse = this.templateService.update(id, templateDTO);
    return new ResponseEntity<>(baseResponse, baseResponse.getStatus());
  }

  @DeleteMapping(value = "{id}")
  @ApiOperation("REST API to delete Template")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "id", dataType = "String", paramType = "path", value = "id"),
  })
  public ResponseEntity<BaseResponse> deleteTemplate(@PathVariable("id") String id) {
    BaseResponse baseResponse = this.templateService.delete(id);
    return new ResponseEntity<>(baseResponse, baseResponse.getStatus());
  }
}
