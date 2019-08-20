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
package com.pig4cloud.pig.order.order.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.service.AgentService;
import com.pig4cloud.pig.accountmanage.user.entity.User;
import com.pig4cloud.pig.accountmanage.user.service.UserService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.order.order.commons.OrderConstants;
import com.pig4cloud.pig.order.order.dto.AdminGoodsOrderDTO;
import com.pig4cloud.pig.order.order.dto.AgentCartOrderDTO;
import com.pig4cloud.pig.order.order.dto.AgentGoodsOrderDTO;
import com.pig4cloud.pig.order.order.dto.GoodsOrderDTO;
import com.pig4cloud.pig.order.order.dto.LogisticsSendDTO;
import com.pig4cloud.pig.order.order.dto.UnifiedOrderDTO;
import com.pig4cloud.pig.order.order.dto.UserCartOrderDTO;
import com.pig4cloud.pig.order.order.dto.UserGoodsOrderDTO;
import com.pig4cloud.pig.order.order.dto.UserMachineOrderDTO;
import com.pig4cloud.pig.order.order.entity.AgentOrder;
import com.pig4cloud.pig.order.order.entity.GoodsOrder;
import com.pig4cloud.pig.order.order.entity.LogisticsOrder;
import com.pig4cloud.pig.order.order.entity.Order;
import com.pig4cloud.pig.order.order.service.OrderService;
import com.pig4cloud.pig.pay.weixin.notify.handler.WXPayNotifyHandler;
import com.pig4cloud.pig.utils.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.Map;


/**
 * 订单管理
 *
 * @author zhuzubin
 * @date 2019-04-05 23:06:00
 */
@Api(value = "order", tags = "订单管理服务")
@RestController
@AllArgsConstructor
@RequestMapping("/order")
@Slf4j
public class OrderController {

	private final WXPayNotifyHandler notifyHandler;
	private final OrderService orderService;
	private final AgentService agentService;
	private final UserService userService;

	@ApiOperation(value = "根据购物车生成代理商采购单", notes = "用于代理商采购时提交订单，消费者者下单请勿使用此服务")
	@PostMapping("/agent")
	@SysLog("新增采购单")
	public R<String> createAgentOrderByCartId(@RequestBody AgentCartOrderDTO agentOrder) {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		return new R(orderService.createAgentOrderByUserCart(agentOrder, SecurityUtils.getUser().getId()));
	}

	@ApiOperation(value = "根据购物车返回采购单总价", notes = "根据购物车返回采购单总价，消费者者下单请勿使用此服务")
	@PostMapping("/agent/totalmoney")
	public R<String> getTotalMoneyByCartId(@RequestBody AgentCartOrderDTO agentOrder) {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		return new R(orderService.getAgentTotalMoneyByCartId(agentOrder, SecurityUtils.getUser().getId()));
	}

	@ApiOperation(value = "根据购物车生成代理商采购单物流费", notes = "根据购物车生成代理商采购单物流费，消费者者下单请勿使用此服务")
	@PostMapping("/logistics/agent")
	public R<BigDecimal> getLogisticsPriceByCartId(@RequestBody AgentCartOrderDTO agentOrder) {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		return new R(orderService.getTotalLogisticsPriceByCartId(agentOrder, SecurityUtils.getUser().getId()));
	}


	@ApiOperation(value = "代理商直接购买商品", notes = "代理商直接购买商品，没有添加到购物车")
	@PostMapping("/agent/bygoods")
	@SysLog("新增采购单")
	public R<String> createAgentOrderByGoods(@RequestBody AgentGoodsOrderDTO goodsOrderDTO) {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		goodsOrderDTO.setAdminId(null);
		return new R(orderService.createAgentOrderByGoods(goodsOrderDTO, SecurityUtils.getUser().getId()));
	}

	@ApiOperation(value = "代理商直接购买订单总价", notes = "代理商直接购买订单总价，没有添加到购物车")
	@PostMapping("/agent/bygoods/totalmoney")
	public R<BigDecimal> getTotalMoneyByGoods(@RequestBody AgentGoodsOrderDTO goodsOrderDTO) {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		goodsOrderDTO.setAdminId(null);
		return new R(orderService.getAgentTotalMoneyByGoods(goodsOrderDTO));
	}

	@ApiOperation("总部新建采购单")
	@PostMapping("/admin/bygoods")
	@SysLog("新增采购单")
	@PreAuthorize("@pms.hasPermission('agent_order_list')")
	public R<String> addAgentOrderByGoods(@RequestBody AdminGoodsOrderDTO dto) {
		Agent agent = agentService.getById(dto.getAgentId());
		if (agent == null) {
			return new R(Boolean.FALSE, "代理商不存在");
		}
		GoodsOrderDTO goodsOrderDTO = new GoodsOrderDTO();
		goodsOrderDTO.setAddressId(dto.getAddressId());
		goodsOrderDTO.setGoods(dto.getGoods());
		goodsOrderDTO.setAdminId(SecurityUtils.getUser().getId());
		return new R(orderService.createAgentOrderByGoods(goodsOrderDTO, agent.getAgentId()));
	}

	@ApiOperation(value = "代理商直接购买商品生成物流费", notes = "代理商直接购买商品生成物流费，没有添加到购物车")
	@PostMapping("/logistics/agent/bygoods")
	public R<String> getLogisticsPriceByGoods(@RequestBody AgentGoodsOrderDTO goodsOrderDTO) {
		return new R(orderService.getTotalLogisticsPriceByGoods(goodsOrderDTO));
	}


	@ApiOperation(value = "根据购物车生成消费订单", notes = "用于消费者购买时提交订单")
	@PostMapping("/user")
	@SysLog("新增消费订单")
	public R<String> createUserOrderByUserCart(@RequestBody UserCartOrderDTO userOrder) {
		return new R(orderService.createUserOrderByUserCart(userOrder, userOrder.getOpenid()));
	}

	@ApiOperation(value = "柜子设备下单", notes = "柜子设备下单")
	@PostMapping("/user/bymachine")
	@SysLog("新增消费订单")
	public R<Map<String, String>> createUserOrderByMachine(@RequestBody UserMachineOrderDTO userOrder, HttpServletRequest request) {
		if (StringUtils.isEmpty(userOrder.getQrStr()) || userOrder.getQrStr().indexOf("-") < 0)
			return new R(Boolean.FALSE, "扫描的二维码不正确");
		String ip = IpUtil.getIpAddr(request);
		return new R(orderService.createUserOrderByMachine(userOrder, userOrder.getOpenid(), ip));
	}

	@ApiOperation(value = "消费者直接购买商品", notes = "消费者直接购买商品，没有添加到购物车")
	@PostMapping("/user/bygoods")
	@SysLog("新增消费订单")
	public R<String> createUserOrderByGoods(@RequestBody UserGoodsOrderDTO goodsOrder) {
		return new R(orderService.createUserOrderByGoods(goodsOrder, goodsOrder.getOpenid()));
	}

	@ApiOperation("支付前统一下单")
	@PostMapping("/unifiedorder")
	public R<Map<String, String>> unifiedOrder(@RequestBody UnifiedOrderDTO dto, HttpServletRequest request) {
		if (dto == null) {
			return new R(Boolean.FALSE, "请求参数为空");
		}
		if (StringUtils.isEmpty(dto.getOpenid())) {
			return new R(Boolean.FALSE, "openid为空，请通过微信公从号入口登录下单");
		}
		if (StringUtils.isEmpty(dto.getOrdercode())) {
			return new R(Boolean.FALSE, "订单编号为空");
		}
		Order order = orderService.getOne(Wrappers.<Order>query().lambda().eq(Order::getCode, dto.getOrdercode()));
		if (order == null) {
			return new R(Boolean.FALSE, "订单不存在");
		}
		if (order.getPayStatus() != null && order.getPayStatus() == 1) {
			return new R(Boolean.FALSE, "订单已完成支付");
		}
		String ip = IpUtil.getIpAddr(request);
		return new R<>(orderService.unifiedOrder(order, dto.getOpenid(), ip));
	}

	@ApiOperation("查询代理商采购记录")
	@GetMapping("/agent/page")
	public R<IPage<Order>> getAgentOrderPage(Page<Order> page, Order order) {
		order.setType(OrderConstants.AGENT);
		order.setCreatorId(SecurityUtils.getUser().getId());
		return new R<>(orderService.getOrderPage(page, order));
	}

	@ApiOperation("查询商品订单记录")
	@GetMapping("/goods/page")
	public R<IPage<GoodsOrder>> getGoodsOrderPage(Page<GoodsOrder> page, GoodsOrder goodsOrder) {
		return new R<>(orderService.getGoodsOrderPage(page, goodsOrder));
	}

	@ApiOperation("查询发货订单")
	@GetMapping("/logistics/page")
	public R<IPage<LogisticsOrder>> getLogisticsOrderPage(Page<LogisticsOrder> page, LogisticsOrder order) {
		return new R<>(orderService.getLogisticsOrderPage(page, order));
	}

	@ApiOperation("订单发货")
	@PutMapping("/logistics/send")
	public R<Boolean> logisticsSend(@RequestBody LogisticsSendDTO dto) {
		Order order = orderService.getById(dto.getId());
		if (order == null)
			return new R(Boolean.FALSE, "订单不存在");
		order.setLogisticsCode(dto.getLogisticsCode());
		order.setLogisticsCompany(dto.getLogisticsCompany());
		order.setStatus(2);
		order.setSendTime(LocalDateTime.now());
		order.setLogisticsOperatorId(SecurityUtils.getUser().getId());
		return new R<>(orderService.updateById(order));
	}

	@ApiOperation("查询全部代理商采购记录")
	@GetMapping("/allagent/page")
	public R<IPage<AgentOrder>> getAllAgentOrderPage(Page<AgentOrder> page, AgentOrder agentOrder) {
		return new R<>(orderService.getAllAgentOrderPage(page, agentOrder));
	}

	@DeleteMapping("/agent/{code}")
	@ApiOperation("根据订单编号删除订单")
	public R<Boolean> removeByCode(@PathVariable String code) {
		try {
			Order order = orderService.getOne(Wrappers.<Order>query().lambda().eq(Order::getCode, code), false);
			if (order == null)
				return new R(Boolean.FALSE, "订单不存在");
			if (order.getPayStatus() != null && order.getPayStatus() == 1)
				return new R(Boolean.FALSE, "订单已支付，不允许删除");
			if (order.getCreatorId() == null || !order.getCreatorId().equals(SecurityUtils.getUser().getId()))
				return new R(Boolean.FALSE, "不能删除别人的订单");
			return new R(orderService.remove(order.getId()));
		} catch (Exception e) {
			String msg = "删除采购单失败";
			log.error(msg + "：" + e.getMessage(), e);
			return new R(Boolean.FALSE, msg);
		}
	}

	@DeleteMapping("/user/bymachine/{code}")
	@ApiOperation("根据订单编号删除订单")
	public R<Boolean> removeUserMachineOrderByCode(@PathVariable String code, @RequestParam String openid) {
		try {
			Order order = orderService.getOne(Wrappers.<Order>query().lambda().eq(Order::getCode, code));
			User user = userService.getOne(Wrappers.<User>query().lambda().eq(User::getWxOpenid, openid), false);
			if (order == null)
				return new R(Boolean.FALSE, "订单不存在");
			if (order.getType() != OrderConstants.USER || order.getMachineId() == null)
				return new R(Boolean.FALSE, "只能删除线下消费订单");
			if (order.getPayStatus() != null && order.getPayStatus() == 1)
				return new R(Boolean.FALSE, "订单已支付，不允许删除");
			if (order.getCreatorId() == null || !order.getCreatorId().equals(user.getId()))
				return new R(Boolean.FALSE, "不能删除别人的订单");
			return new R(orderService.remove(order.getId()));
		} catch (Exception e) {
			String msg = "删除线下消费订单失败";
			log.error(msg + "：" + e.getMessage(), e);
			return new R(Boolean.FALSE, msg);
		}
	}


	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R<Order> getById(@PathVariable("id") Integer id) {
		return new R<>(orderService.getById(id));
	}

	@ApiOperation(value = "支付通知回调", notes = "支付通知回调")
	@PostMapping("/wxpay/notify")
	public ResponseEntity<String> notify(@RequestBody String xml) {
		return notifyHandler.notify(xml, notifyEntity -> {
			Order order = orderService.getOne((Wrappers.<Order>query().lambda().eq(Order::getCode, notifyEntity.getOut_trade_no())));
			// 订单状态未变更，防止重复通知
			if (order != null && order.getPayStatus() == null || 0 == order.getPayStatus()) {
				// 验证订单价格，防止假通知
				BigDecimal total = order.getTotalMoney().multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP);
				if (total.compareTo(notifyEntity.getTotal_fee()) == 0) {
					orderService.updateByWXPayNotify(notifyEntity, order);
				}
			} else {
				return false;
			}
			return true;
		});
	}
}
