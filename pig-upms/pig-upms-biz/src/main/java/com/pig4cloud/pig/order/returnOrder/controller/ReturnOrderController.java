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
package com.pig4cloud.pig.order.returnOrder.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.order.returnOrder.entity.ReturnOrder;
import com.pig4cloud.pig.order.returnOrder.service.ReturnOrderService;
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
 * 退货订单
 *
 * @author yuxinyin
 * @date 2019-04-17 19:08:26
 */
@RestController
@AllArgsConstructor
@RequestMapping("/returnorder")
public class ReturnOrderController {

  private final  ReturnOrderService returnOrderService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param returnOrder 退货订单
   * @return
   */
  @GetMapping("/page")
  public R<IPage<ReturnOrder>> getReturnOrderPage(Page<ReturnOrder> page, ReturnOrder returnOrder) {
    return  new R<>(returnOrderService.getReturnOrderPage(page,returnOrder));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<ReturnOrder> getById(@PathVariable("id") Integer id){
    return new R<>(returnOrderService.getById(id));
  }

  /**
   * 新增记录
   * @param returnOrder
   * @return R
   */
  @SysLog("新增退货订单")
  @PostMapping
//  @PreAuthorize("@pms.hasPermission('returnOrder_returnorder_add')")
  public R save(@RequestBody ReturnOrder returnOrder){
    return new R<>(returnOrderService.save(returnOrder));
  }

  /**
   * 修改记录
   * @param returnOrder
   * @return R
   */
  @SysLog("修改退货订单")
  @PutMapping
//  @PreAuthorize("@pms.hasPermission('returnOrder_returnorder_edit')")
  public R update(@RequestBody ReturnOrder returnOrder){
    return new R<>(returnOrderService.updateById(returnOrder));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除退货订单")
  @DeleteMapping("/{id}")
//  @PreAuthorize("@pms.hasPermission('returnOrder_returnorder_del')")
  public R removeById(@PathVariable Integer id){
    return new R<>(returnOrderService.removeById(id));
  }

}
