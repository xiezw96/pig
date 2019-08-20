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
package com.pig4cloud.pig.settings.voucherGrantRecord.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.settings.voucherGrantRecord.entity.VoucherGrantRecord;
import com.pig4cloud.pig.settings.voucherGrantRecord.service.VoucherGrantRecordService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;


/**
 * 代金券发放记录
 *
 * @author zhuzubin
 * @date 2019-04-05 11:22:32
 */
@Api(value = "voucher", tags = "代金券发放记录管理")
@RestController
@AllArgsConstructor
@RequestMapping("/vouchergrantrecord")
public class VoucherGrantRecordController {

  private final  VoucherGrantRecordService voucherGrantRecordService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param voucherGrantRecord 代金券发放记录
   * @return
   */
  @ApiOperation(value="简单分页查询",notes="简单分页查询")
  @GetMapping("/page")
  public R<IPage<VoucherGrantRecord>> getVoucherGrantRecordPage(Page<VoucherGrantRecord> page, VoucherGrantRecord voucherGrantRecord) {
    return  new R<>(voucherGrantRecordService.getVoucherGrantRecordPage(page,voucherGrantRecord));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
  @GetMapping("/{id}")
  public VoucherGrantRecord getById(@PathVariable("id") Integer id){
    return voucherGrantRecordService.getVoucherGrantRecordById(id);
  }

  /**
   * 新增记录
   * @param voucherGrantRecord
   * @return R
   */
  @ApiOperation(value = "新增记录", notes = "新增记录")
  @SysLog("新增代金券发放记录")
  @PostMapping
//  @PreAuthorize("@pms.hasPermission('voucherGrantRecord_vouchergrantrecord_add')")
  public R save(@RequestBody VoucherGrantRecord voucherGrantRecord){
    return new R<>(voucherGrantRecordService.save(voucherGrantRecord));
  }

  /**
   * 修改记录
   * @param voucherGrantRecord
   * @return R
   */
  @ApiOperation(value = "修改记录", notes = "修改记录")
  @SysLog("修改代金券发放记录")
  @PutMapping
//  @PreAuthorize("@pms.hasPermission('voucherGrantRecord_vouchergrantrecord_edit')")
  public R update(@RequestBody VoucherGrantRecord voucherGrantRecord){
    return new R<>(voucherGrantRecordService.updateById(voucherGrantRecord));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @ApiOperation(value = "通过id删除一条记录", notes = "通过id删除一条记录")
  @SysLog("删除代金券发放记录")
  @DeleteMapping("/{id}")
//  @PreAuthorize("@pms.hasPermission('voucherGrantRecord_vouchergrantrecord_del')")
  public R removeById(@PathVariable Integer id){
    return new R<>(voucherGrantRecordService.removeById(id));
  }

}
