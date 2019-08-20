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
package com.pig4cloud.pig.order.orderVoucher.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.order.orderVoucher.entity.OrderVoucher;
import com.pig4cloud.pig.order.orderVoucher.service.OrderVoucherService;
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
 * 订单代金券
 *
 * @author zhuzubin
 * @date 2019-04-05 23:09:30
 */
@RestController
@AllArgsConstructor
@RequestMapping("/ordervoucher")
public class OrderVoucherController {

  private final  OrderVoucherService orderVoucherService;

  /**
   * 简单分页查询
   * @param page 分页对象
   * @param orderVoucher 订单代金券
   * @return
   */
  @GetMapping("/page")
  public R<IPage<OrderVoucher>> getOrderVoucherPage(Page<OrderVoucher> page, OrderVoucher orderVoucher) {
    return  new R<>(orderVoucherService.getOrderVoucherPage(page,orderVoucher));
  }


  /**
   * 通过id查询单条记录
   * @param id
   * @return R
   */
  @GetMapping("/{id}")
  public R<OrderVoucher> getById(@PathVariable("id") Integer id){
    return new R<>(orderVoucherService.getById(id));
  }

  /**
   * 新增记录
   * @param orderVoucher
   * @return R
   */
  @SysLog("新增订单代金券")
  @PostMapping
//  @PreAuthorize("@pms.hasPermission('orderVoucher_ordervoucher_add')")
  public R save(@RequestBody OrderVoucher orderVoucher){
    return new R<>(orderVoucherService.save(orderVoucher));
  }

  /**
   * 修改记录
   * @param orderVoucher
   * @return R
   */
  @SysLog("修改订单代金券")
  @PutMapping
//  @PreAuthorize("@pms.hasPermission('orderVoucher_ordervoucher_edit')")
  public R update(@RequestBody OrderVoucher orderVoucher){
    return new R<>(orderVoucherService.updateById(orderVoucher));
  }

  /**
   * 通过id删除一条记录
   * @param id
   * @return R
   */
  @SysLog("删除订单代金券")
  @DeleteMapping("/{id}")
//  @PreAuthorize("@pms.hasPermission('orderVoucher_ordervoucher_del')")
  public R removeById(@PathVariable Integer id){
    return new R<>(orderVoucherService.removeById(id));
  }

}
