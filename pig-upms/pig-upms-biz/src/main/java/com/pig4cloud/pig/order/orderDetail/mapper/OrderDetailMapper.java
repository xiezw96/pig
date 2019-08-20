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
package com.pig4cloud.pig.order.orderDetail.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.order.orderDetail.dto.AgentOrderDetailDTO;
import com.pig4cloud.pig.order.orderDetail.entity.OrderDetail;
import com.pig4cloud.pig.order.orderDetail.entity.UserOrderDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单明细管理
 *
 * @author zhuzubin
 * @date 2019-04-05 23:08:09
 */
public interface OrderDetailMapper extends BaseMapper<OrderDetail> {
	/**
	 * 订单明细管理简单分页查询
	 *
	 * @param orderDetail 订单明细管理
	 * @return
	 */
	IPage<OrderDetail> getOrderDetailPage(Page page, @Param("orderDetail") OrderDetail orderDetail);

	/**
	 * 查询消费订单明细
	 * <p>Title: getUserOrderDetailPage</p>
	 * <p>Description: </p>
	 *
	 * @return com.baomidou.mybatisplus.core.metadata.IPage<com.pig4cloud.pig.order.orderDetail.entity.UserOrderDetail>
	 * @date 2019年04月28日
	 * @author 余新引
	 */
	IPage<UserOrderDetail> getUserOrderDetailPage(IPage page, @Param("userOrderDetail") UserOrderDetail userOrderDetail);


	/**
	 * 根据订单ID查询明细
	 * <p>Title: getOrderDetailsByOrderId</p>
	 * <p>Description: </p>
	 *
	 * @return java.util.List<com.pig4cloud.pig.order.orderDetail.entity.OrderDetail>
	 * @date 2019年04月21日
	 * @author 余新引
	 */
	List<OrderDetail> getOrderDetailsByOrderId(@Param("orderId") Integer orderId);

	AgentOrderDetailDTO getOrderInfoById(@Param("orderId") Integer orderId);
}
