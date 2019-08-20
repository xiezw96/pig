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
package com.pig4cloud.pig.order.order.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.order.order.dto.AgentCartOrderDTO;
import com.pig4cloud.pig.order.order.dto.GoodsOrderDTO;
import com.pig4cloud.pig.order.order.dto.UserCartOrderDTO;
import com.pig4cloud.pig.order.order.dto.UserMachineOrderDTO;
import com.pig4cloud.pig.order.order.entity.AgentOrder;
import com.pig4cloud.pig.order.order.entity.GoodsOrder;
import com.pig4cloud.pig.order.order.entity.LogisticsOrder;
import com.pig4cloud.pig.order.order.entity.Order;
import com.pig4cloud.pig.pay.weixin.notify.entity.WXPayNotify;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 订单管理
 *
 * @author zhuzubin
 * @date 2019-04-05 23:06:00
 */
public interface OrderService extends IService<Order> {

	/**
	 * 订单管理简单分页查询
	 *
	 * @param order 订单管理
	 * @return
	 */
	IPage<Order> getOrderPage(Page<Order> page, Order order);

	/**
	 * 获取物流订单
	 * <p>Title: getLogisticsOrderPage</p>
	 * <p>Description: </p>
	 *
	 * @date 2019年07月03日
	 * @author 余新引
	 */
	IPage<LogisticsOrder> getLogisticsOrderPage(Page<LogisticsOrder> page, LogisticsOrder order);

	/**
	 * 获取商品订单
	 * <p>Title: getGoodsOrderPage</p>
	 * <p>Description: </p>
	 *
	 * @date 2019年06月27日
	 * @author 余新引
	 */
	IPage<GoodsOrder> getGoodsOrderPage(Page<GoodsOrder> page, GoodsOrder goodsOrder);

	/**
	 * 查询全部代理商采购记录
	 * <p>Title: getAllAgentOrderPage</p>
	 * <p>Description: </p>
	 *
	 * @date 2019年06月27日
	 * @author 余新引
	 */
	IPage<AgentOrder> getAllAgentOrderPage(Page<AgentOrder> page, AgentOrder agentOrder);

	/**
	 * 根据代理商购物车创建订单
	 * <p>Title: createAgentOrderByUserCart</p>
	 * <p>Description: </p>
	 *
	 * @param agentOrder 订单信息
	 * @param userId     下单人
	 * @return java.lang.Integer
	 * @date 2019年04月15日
	 * @author 余新引
	 */
	String createAgentOrderByUserCart(AgentCartOrderDTO agentOrder, Integer userId);

	/**
	 * 根据代理商商品创建订单(直接购买，不清空购物车)
	 * <p>Title: createAgentOrderByUserCart</p>
	 * <p>Description: </p>
	 *
	 * @param agentOrder 订单信息
	 * @param userId     下单人
	 * @return java.lang.Integer
	 * @date 2019年04月15日
	 * @author 余新引
	 */
	String createAgentOrderByGoods(GoodsOrderDTO agentOrder, Integer userId);

	/**
	 * 柜子设备下单
	 * <p>Title: createAgentOrderByUserCart</p>
	 * <p>Description: </p>
	 *
	 * @param userOrder 订单信息
	 * @param openid    下单人
	 * @return java.lang.Integer
	 * @date 2019年04月15日
	 * @author 余新引
	 */
	Map<String, String> createUserOrderByMachine(UserMachineOrderDTO userOrder, String openid, String ip);


	/**
	 * 根据用户购物车创建订单
	 * <p>Title: createAgentOrderByUserCart</p>
	 * <p>Description: </p>
	 *
	 * @param userOrder 订单信息
	 * @param openid    下单人
	 * @return java.lang.Integer
	 * @date 2019年04月15日
	 * @author 余新引
	 */
	String createUserOrderByUserCart(UserCartOrderDTO userOrder, String openid);

	/**
	 * 根据用户商品创建订单(直接购买，不清空购物车)
	 * <p>Title: createAgentOrderByUserCart</p>
	 * <p>Description: </p>
	 *
	 * @param userOrder 订单信息
	 * @param openid    下单人
	 * @return java.lang.Integer
	 * @date 2019年04月15日
	 * @author 余新引
	 */
	String createUserOrderByGoods(GoodsOrderDTO userOrder, String openid);

	/**
	 * 统一下单
	 * <p>Title: unifiedOrder</p>
	 * <p>Description: </p>
	 *
	 * @return com.pig4cloud.pig.pay.weixin.api.entity.UnifiedOrderResult
	 * @date 2019年06月21日
	 * @author 余新引
	 */
	Map<String, String> unifiedOrder(Order order, String wxOpenid, String ip);

	/**
	 * 创建订单
	 * <p>Title: createAgentOrder</p>
	 * <p>Description: </p>
	 *
	 * @return java.lang.String
	 * @date 2019年06月13日
	 * @author 余新引
	 */
	void createAgentOrder(GoodsOrderDTO agentOrder, Integer userId, Order order);

	/**
	 * 获取指定日期和类型的用户订单总金额
	 * <p>Title: getOrderDayMoneySum</p>
	 * <p>Description: </p>
	 *
	 * @param date   指定日期（yyyy-MM-dd）
	 * @param userId 下单用户
	 * @return java.lang.Integer
	 * @date 2019年04月16日
	 * @author 余新引
	 */
	BigDecimal getOrderDayMoneySum(String date, Integer userId);

	/**
	 * 获取指定日期和类型的用户订单数量
	 * <p>Title: getOrderDayMoneySum</p>
	 * <p>Description: </p>
	 *
	 * @param date      指定日期（yyyy-MM-dd）
	 * @param payStatus 支付状态
	 * @param userId    下单用户
	 * @return java.lang.Integer
	 * @date 2019年04月16日
	 * @author 余新引
	 */
	int getOrderDaySum(String date, Integer payStatus, Integer userId);

	/**
	 * 根据微信支付通知修改订单
	 * <p>Title: updateByWXPayNotify</p>
	 * <p>Description: </p>
	 *
	 * @return void
	 * @date 2019年06月13日
	 * @author 余新引
	 */
	void updateByWXPayNotify(WXPayNotify notify, Order order);

	/**
	 * 根据订单编号删除订单
	 * <p>Title: removeByCode</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年06月24日
	 * @author 余新引
	 */
	boolean remove(Integer orderId);

	/**
	 * 根据购物车计算物流总价
	 * <p>Title: getTotalLogisticsPriceByCartId</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年07月06日
	 * @author 余新引
	 */
	BigDecimal getTotalLogisticsPriceByCartId(AgentCartOrderDTO agentOrder, Integer userId);

	/**
	 * 根据商品计算物流总价
	 * <p>Title: getTotalLogisticsPriceByGoods</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年07月06日
	 * @author 余新引
	 */
	BigDecimal getTotalLogisticsPriceByGoods(GoodsOrderDTO agentOrder);

	/**
	 * 根据商品计算采购单总价
	 * <p>Title: getTotalLogisticsPriceByGoods</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年07月06日
	 * @author 余新引
	 */
	BigDecimal getAgentTotalMoneyByCartId(AgentCartOrderDTO dto, Integer userId);

	/**
	 * 根据商品计算采购单总价
	 * <p>Title: getTotalLogisticsPriceByGoods</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年07月06日
	 * @author 余新引
	 */
	BigDecimal getUserTotalMoneyByCartId(UserCartOrderDTO dto, Integer userId);

	/**
	 * 根据商品计算总价
	 * <p>Title: getTotalLogisticsPriceByGoods</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年07月06日
	 * @author 余新引
	 */
	BigDecimal getAgentTotalMoneyByGoods(GoodsOrderDTO dto);

	/**
	 * 根据商品计算总价
	 * <p>Title: getTotalLogisticsPriceByGoods</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年07月06日
	 * @author 余新引
	 */
	BigDecimal getUserTotalMoneyByGoods(GoodsOrderDTO dto);

}
