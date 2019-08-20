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
package com.pig4cloud.pig.order.orderDetail.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.entity.AgentReverseChain;
import com.pig4cloud.pig.accountmanage.agent.service.AgentService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.order.orderDetail.dto.AgentOrderDetailDTO;
import com.pig4cloud.pig.order.orderDetail.entity.OrderDetail;
import com.pig4cloud.pig.order.orderDetail.entity.UserOrderDetail;
import com.pig4cloud.pig.order.orderDetail.service.OrderDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 订单明细管理
 *
 * @author zhuzubin
 * @date 2019-04-05 23:08:09
 */
@Api(value = "orderdetail", tags = "订单明细管理服务")
@RestController
@AllArgsConstructor
@RequestMapping("/orderdetail")
public class OrderDetailController {

	private final OrderDetailService orderDetailService;
	private final AgentService agentService;

	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R<OrderDetail> getById(@PathVariable("id") Integer id) {
		return new R<>(orderDetailService.getById(id));
	}

	@ApiOperation(value = "查询代理商采购明细", notes = "查询代理商采购明细")
	@GetMapping("/agentorder/{orderId}")
	public R<AgentOrderDetailDTO> getAgentOrderDetailPage(@PathVariable Integer orderId) {
		return new R(orderDetailService.getAgentOrderDetail(orderId));
	}

	@ApiOperation(value = "分页查询所有的用户订单列表", notes = "用于总部查询订单详情")
	@GetMapping("/saleorder")
	@PreAuthorize("@pms.hasPermission('orderdetail_saleorder')")
	public R<IPage<UserOrderDetail>> getAllUserOrderDetailPage(Page<UserOrderDetail> page, UserOrderDetail userOrderDetail) {
		return new R(orderDetailService.getUserOrderDetailPage(page, userOrderDetail));
	}

	@ApiOperation(value = "分页查询代理商的用户订单列表", notes = "根据当前登录的代理商进行过滤")
	@GetMapping("/agent/saleorder")
	public R<IPage<UserOrderDetail>> getAgentUserOrderDetailPage(Page<UserOrderDetail> page, UserOrderDetail userOrderDetail) {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		userOrderDetail.setUserAgentId(agent.getAgentId());
		AgentReverseChain agentChain = agentService.getAgentReverseChain(agent);
		userOrderDetail.setMachineAgentIds(agentChain.toIdList());
		IPage<UserOrderDetail> result = orderDetailService.getUserOrderDetailPage(page, userOrderDetail);
		if (result != null && result.getRecords() != null) {
			result.getRecords().stream().forEach(o -> {
				if (StringUtils.isEmpty(o.getMachineCode())) {
					o.setSource("官网");
				} else if (agent.getAgentId().equals(o.getSourceAgentId())) {
					o.setSource("自有");
				} else {
					o.setSource("下级代理");
				}
			});
		}
		return new R(result);
	}

}
