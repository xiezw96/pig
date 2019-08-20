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
package com.pig4cloud.pig.settings.machineTemplateDetail.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.settings.machineTemplateDetail.entity.MachineTemplateDetail;
import com.pig4cloud.pig.settings.machineTemplateDetail.service.MachineTemplateDetailService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 补货模板明细
 *
 * @author zhuzubin
 * @date 2019-07-08 15:39:06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/machinetemplatedetail")
public class MachineTemplateDetailController {

  private final  MachineTemplateDetailService machineTemplateDetailService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param machineTemplateDetail 补货模板明细
   * @return
   */
  @GetMapping("/page")
  public R<IPage<MachineTemplateDetail>> getMachineTemplateDetailPage(Page<MachineTemplateDetail> page, MachineTemplateDetail machineTemplateDetail) {
    return  new R<>(machineTemplateDetailService.getMachineTemplateDetailPage(page,machineTemplateDetail));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<MachineTemplateDetail> getById(@PathVariable("id") Integer id){
    return new R<>(machineTemplateDetailService.getById(id));
  }

  /**
   * 新增记录
   * @param machineTemplateDetail
   * @return R
   */
  @SysLog("新增补货模板明细")
  @PostMapping
  public R save(@RequestBody MachineTemplateDetail machineTemplateDetail){
    return new R<>(machineTemplateDetailService.save(machineTemplateDetail));
  }

  /**
   * 修改记录
   * @param machineTemplateDetail
   * @return R
   */
  @SysLog("修改补货模板明细")
  @PutMapping
  public R update(@RequestBody MachineTemplateDetail machineTemplateDetail){
    return new R<>(machineTemplateDetailService.updateById(machineTemplateDetail));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除补货模板明细")
  @DeleteMapping("/{id}")
  public R removeById(@PathVariable Integer id){
    return new R<>(machineTemplateDetailService.removeById(id));
  }

}
