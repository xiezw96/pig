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
package com.pig4cloud.pig.order.returnOrderPic.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.order.returnOrderPic.entity.SalesReturnPic;
import com.pig4cloud.pig.order.returnOrderPic.service.SalesReturnPicService;
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
 * 退货订单图片
 *
 * @author yuxinyin
 * @date 2019-04-17 19:07:36
 */
@RestController
@AllArgsConstructor
@RequestMapping("/salesreturnpic")
public class SalesReturnPicController {

  private final  SalesReturnPicService salesReturnPicService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param salesReturnPic 退货订单图片
   * @return
   */
  @GetMapping("/page")
  public R<IPage<SalesReturnPic>> getSalesReturnPicPage(Page<SalesReturnPic> page, SalesReturnPic salesReturnPic) {
    return  new R<>(salesReturnPicService.getSalesReturnPicPage(page,salesReturnPic));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<SalesReturnPic> getById(@PathVariable("id") Integer id){
    return new R<>(salesReturnPicService.getById(id));
  }

  /**
   * 新增记录
   * @param salesReturnPic
   * @return R
   */
  @SysLog("新增退货订单图片")
  @PostMapping
//  @PreAuthorize("@pms.hasPermission('returnOrderPic_salesreturnpic_add')")
  public R save(@RequestBody SalesReturnPic salesReturnPic){
    return new R<>(salesReturnPicService.save(salesReturnPic));
  }

  /**
   * 修改记录
   * @param salesReturnPic
   * @return R
   */
  @SysLog("修改退货订单图片")
  @PutMapping
//  @PreAuthorize("@pms.hasPermission('returnOrderPic_salesreturnpic_edit')")
  public R update(@RequestBody SalesReturnPic salesReturnPic){
    return new R<>(salesReturnPicService.updateById(salesReturnPic));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除退货订单图片")
  @DeleteMapping("/{id}")
//  @PreAuthorize("@pms.hasPermission('returnOrderPic_salesreturnpic_del')")
  public R removeById(@PathVariable Integer id){
    return new R<>(salesReturnPicService.removeById(id));
  }

}
