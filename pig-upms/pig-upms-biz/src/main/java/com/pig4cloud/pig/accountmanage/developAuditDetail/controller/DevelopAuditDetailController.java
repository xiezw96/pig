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
package com.pig4cloud.pig.accountmanage.developAuditDetail.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.developAuditDetail.entity.DevelopAuditDetail;
import com.pig4cloud.pig.accountmanage.developAuditDetail.service.DevelopAuditDetailService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
 * 发展审核明细申请
 *
 * @author zhuzubin
 * @date 2019-04-05 23:39:10
 */
@Api(value="developauditdetail", tags="发展审核明细管理")
@RestController
@AllArgsConstructor
@RequestMapping("/developauditdetail")
public class DevelopAuditDetailController {

  private final  DevelopAuditDetailService developAuditDetailService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param developAuditDetail 提现申请
   * @return
   */
  @ApiOperation(value="分页查询", notes="分页查询")
  @GetMapping("/page")
  public R<IPage<DevelopAuditDetail>> getDevelopAuditDetailPage(Page<DevelopAuditDetail> page, DevelopAuditDetail developAuditDetail) {
    return  new R<>(developAuditDetailService.getDevelopAuditDetailPage(page,developAuditDetail));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @ApiOperation(value="通过id查询单条记录", notes="通过id查询单条记录")
  @GetMapping("/{id}")
  public R<DevelopAuditDetail> getById(@PathVariable("id") Integer id){
    return new R<>(developAuditDetailService.getById(id));
  }

  /**
   * 新增记录
   * @param developAuditDetail
   * @return R
   */
  @ApiOperation(value="新增记录", notes="新增记录")
  @SysLog("新增提现申请明细")
  @PostMapping
//  @PreAuthorize("@pms.hasPermission('developAuditDetail_developauditdetail_add')")
  public R save(@RequestBody DevelopAuditDetail developAuditDetail){
    return new R<>(developAuditDetailService.save(developAuditDetail));
  }

  /**
   * 修改记录
   * @param developAuditDetail
   * @return R
   */
  @ApiOperation(value="修改记录", notes="修改记录")
  @SysLog("修改提现申请明细")
  @PutMapping
//  @PreAuthorize("@pms.hasPermission('developAuditDetail_developauditdetail_edit')")
  public R update(@RequestBody DevelopAuditDetail developAuditDetail){
    return new R<>(developAuditDetailService.updateById(developAuditDetail));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @ApiOperation(value="通过id删除一条记录", notes="通过id删除一条记录")
  @SysLog("删除提现申请明细")
  @DeleteMapping("/{id}")
//  @PreAuthorize("@pms.hasPermission('developAuditDetail_developauditdetail_del')")
  public R removeById(@PathVariable Integer id){
    return new R<>(developAuditDetailService.removeById(id));
  }

}
