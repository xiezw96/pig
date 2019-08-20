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
package com.pig4cloud.pig.reports.controller;

import cn.hutool.core.date.DateUtil;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.service.AgentService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.order.order.service.OrderService;
import com.pig4cloud.pig.order.settlementDetail.entity.SettlementMoney;
import com.pig4cloud.pig.order.settlementDetail.service.SettlementDetailService;
import com.pig4cloud.pig.reports.dto.AgentAppReportDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.Optional;


/**
 * 总部报表
 *
 * @author zhuzubin
 * @date 2019-04-05 23:06:00
 */
@Api(value = "agentappreport", tags = "代理端App报表查询服务")
@RestController
@AllArgsConstructor
@RequestMapping("/agentapp")
public class AgentAppReportController {

	private final OrderService orderService;
	private final AgentService agentService;
	private final SettlementDetailService settlementDetailService;

	@GetMapping("/data_report")
	@ApiOperation(value = "代理商App首页报表", notes = "代理商App首页报表")
	public R<AgentAppReportDTO> getDataReport() {
		Integer userId = SecurityUtils.getUser().getId();
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			throw new RuntimeException("代理商查询出错了");
		}
		String format = "yyyy-MM-dd";
		double todaySaleStatement = orderService.getOrderDayMoneySum(DateUtil.format(new Date(), format), userId).setScale(2, RoundingMode.HALF_UP).doubleValue();
		double yesterdaySaleStatement = orderService.getOrderDayMoneySum(DateUtil.yesterday().toString(format), userId).setScale(2, RoundingMode.HALF_UP).doubleValue();
		SettlementMoney sum = settlementDetailService.getAgentSettlementMoney(agent.getAgentId());
		double settlementMoney = sum == null ? 0 : Optional.ofNullable(sum.getOriginalSettlementPrice()).orElse(new BigDecimal(0)).setScale(2, RoundingMode.HALF_UP).doubleValue();
		double sumMoney = agent.getWithdrawalPrice() == null ? 0 : agent.getWithdrawalPrice().setScale(0, RoundingMode.HALF_UP).doubleValue();
		return new R(AgentAppReportDTO.builder()
			// 今日流水
			.todaySaleStatement(todaySaleStatement)
			// 昨日流水
			.yesterdaySaleStatement(yesterdaySaleStatement)
			// 今日订单数
			.todayOrderNum(orderService.getOrderDaySum(DateUtil.format(new Date(), format), null, userId))
			// 昨日订单数
			.yesterdayOrderNum(orderService.getOrderDaySum(DateUtil.yesterday().toString(format), null, userId))
			// 资产总额
			.sumMoney(sumMoney)
			// 可结算金额
			.settlementMoney(settlementMoney).build());
	}

}
