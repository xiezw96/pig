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
package com.pig4cloud.pig.order.orderVoucher.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.order.orderVoucher.entity.OrderVoucher;
import com.pig4cloud.pig.order.orderVoucher.mapper.OrderVoucherMapper;
import com.pig4cloud.pig.order.orderVoucher.service.OrderVoucherService;

/**
 * 订单代金券
 *
 * @author zhuzubin
 * @date 2019-04-05 23:09:30
 */
@Service("orderVoucherService")
public class OrderVoucherServiceImpl extends ServiceImpl<OrderVoucherMapper, OrderVoucher> implements OrderVoucherService {

  /**
   * 订单代金券简单分页查询
   * @param orderVoucher 订单代金券
   * @return
   */
  @Override
  public IPage<OrderVoucher> getOrderVoucherPage(Page<OrderVoucher> page, OrderVoucher orderVoucher){
      return baseMapper.getOrderVoucherPage(page,orderVoucher);
  }

}
