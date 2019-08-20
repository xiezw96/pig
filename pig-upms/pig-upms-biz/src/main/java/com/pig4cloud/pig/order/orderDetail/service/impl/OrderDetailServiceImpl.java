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
package com.pig4cloud.pig.order.orderDetail.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.order.order.service.OrderService;
import com.pig4cloud.pig.order.orderDetail.dto.AgentOrderDetailDTO;
import com.pig4cloud.pig.order.orderDetail.entity.OrderDetail;
import com.pig4cloud.pig.order.orderDetail.entity.UserOrderDetail;
import com.pig4cloud.pig.order.orderDetail.mapper.OrderDetailMapper;
import com.pig4cloud.pig.order.orderDetail.service.OrderDetailService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 订单明细管理
 *
 * @author zhuzubin
 * @date 2019-04-05 23:08:09
 */
@Service("orderDetailService")
@AllArgsConstructor
public class OrderDetailServiceImpl extends ServiceImpl<OrderDetailMapper, OrderDetail> implements OrderDetailService {
	private final OrderService orderService;

	/**
	 * 订单明细管理简单分页查询
	 *
	 * @param orderDetail 订单明细管理
	 * @return
	 */
	@Override
	public IPage<OrderDetail> getOrderDetailPage(Page<OrderDetail> page, OrderDetail orderDetail) {
		return baseMapper.getOrderDetailPage(page, orderDetail);
	}

	@Override
	public IPage<UserOrderDetail> getUserOrderDetailPage(IPage<UserOrderDetail> page, UserOrderDetail userOrderDetail) {
		return baseMapper.getUserOrderDetailPage(page, userOrderDetail);
	}

	@Override
	public AgentOrderDetailDTO getAgentOrderDetail(Integer orderId) {
		AgentOrderDetailDTO orderDetailDTO = baseMapper.getOrderInfoById(orderId);
		if (orderDetailDTO == null)
			throw new RuntimeException("订单不存在");
		orderDetailDTO.setDetailList(list(Wrappers.<OrderDetail>query().lambda().eq(OrderDetail::getOrderId, orderId)));
		return orderDetailDTO;
	}
}
