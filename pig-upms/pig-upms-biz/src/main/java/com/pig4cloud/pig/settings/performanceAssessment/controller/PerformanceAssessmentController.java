/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.settings.performanceAssessment.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.settings.performanceAssessment.entity.PerformanceAssessment;
import com.pig4cloud.pig.settings.performanceAssessment.service.PerformanceAssessmentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 绩效考核
 *
 * @author zhuzubin
 * @date 2019-04-05 11:29:11
 */
@RestController
@AllArgsConstructor
@RequestMapping("/performanceassessment")
public class PerformanceAssessmentController {

  private final  PerformanceAssessmentService performanceAssessmentService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param performanceAssessment 绩效考核
   * @return
   */
  @GetMapping("/page")
  public R<IPage<PerformanceAssessment>> getPerformanceAssessmentPage(Page<PerformanceAssessment> page, PerformanceAssessment performanceAssessment) {
    return  new R<>(performanceAssessmentService.getPerformanceAssessmentPage(page,performanceAssessment));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<PerformanceAssessment> getById(@PathVariable("id") Integer id){
    return new R<>(performanceAssessmentService.getById(id));
  }

  /**
   * 新增记录
   * @param performanceAssessment
   * @return R
   */
  @SysLog("新增绩效考核")
  @PostMapping
//  @PreAuthorize("@pms.hasPermission('performanceAssessment_performanceassessment_add')")
  public R save(@RequestBody PerformanceAssessment performanceAssessment){
    return new R<>(performanceAssessmentService.save(performanceAssessment));
  }

  /**
   * 修改记录
   * @param performanceAssessment
   * @return R
   */
  @SysLog("修改绩效考核")
  @PutMapping
//  @PreAuthorize("@pms.hasPermission('performanceAssessment_performanceassessment_edit')")
  public R update(@RequestBody PerformanceAssessment performanceAssessment){
    return new R<>(performanceAssessmentService.updateById(performanceAssessment));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除绩效考核")
  @DeleteMapping("/{id}")
//  @PreAuthorize("@pms.hasPermission('performanceAssessment_performanceassessment_del')")
  public R removeById(@PathVariable Integer id){
    return new R<>(performanceAssessmentService.removeById(id));
  }

}
