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
package com.pig4cloud.pig.order.order.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.order.order.entity.AgentOrder;
import com.pig4cloud.pig.order.order.entity.GoodsOrder;
import com.pig4cloud.pig.order.order.entity.LogisticsOrder;
import com.pig4cloud.pig.order.order.entity.Order;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;

/**
 * 订单管理
 *
 * @author zhuzubin
 * @date 2019-04-05 23:06:00
 */
public interface OrderMapper extends BaseMapper<Order> {
	/**
	 * 订单管理简单分页查询
	 *
	 * @param order 订单管理
	 * @return
	 */
	IPage<Order> getOrderPage(Page page, @Param("order") Order order);

	/**
	 * 查询商品订单数据
	 * <p>Title: getGoodsOrderPage</p>
	 * <p>Description: </p>
	 *
	 * @return com.baomidou.mybatisplus.core.metadata.IPage<com.pig4cloud.pig.order.order.entity.GoodsOrder>
	 * @date 2019年06月27日
	 * @author 余新引
	 */
	IPage<GoodsOrder> getGoodsOrderPage(@Param("page") Page<GoodsOrder> page, @Param("order") GoodsOrder goodsOrder);

	/**
	 * 查询全部代理商采购记录
	 * <p>Title: getAllAgentOrderPage</p>
	 * <p>Description: </p>
	 *
	 * @date 2019年06月27日
	 * @author 余新引
	 */
	IPage<AgentOrder> getAllAgentOrderPage(Page<AgentOrder> page, @Param("agentOrder") AgentOrder agentOrder);

	/**
	 * 获取指定日期和类型的用户订单总金额
	 * <p>Title: getOrderDayMoneySum</p>
	 * <p>Description: </p>
	 *
	 * @return java.lang.Integer
	 * @date 2019年04月16日
	 * @author 余新引
	 */
	BigDecimal getOrderDayMoneySum(@Param("date") String date, @Param("userId") Integer userId);


	/**
	 * 获取指定日期和类型的用户订单数量
	 * <p>Title: getOrderDayMoneySum</p>
	 * <p>Description: </p>
	 *
	 * @return java.lang.Integer
	 * @date 2019年04月16日
	 * @author 余新引
	 */
	int getOrderDaySum(@Param("date") String date, @Param("payStatus") Integer payStatus, @Param("userId") Integer userId);

	IPage<LogisticsOrder> getLogisticsOrderPage(Page<LogisticsOrder> page, @Param("order") LogisticsOrder order);
}
