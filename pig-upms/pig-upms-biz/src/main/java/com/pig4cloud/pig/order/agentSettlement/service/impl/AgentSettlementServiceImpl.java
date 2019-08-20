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
package com.pig4cloud.pig.order.agentSettlement.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.service.AgentService;
import com.pig4cloud.pig.order.agentSettlement.dto.AgentSettlementSaveDTO;
import com.pig4cloud.pig.order.agentSettlement.entity.AgentSettlement;
import com.pig4cloud.pig.order.agentSettlement.mapper.AgentSettlementMapper;
import com.pig4cloud.pig.order.agentSettlement.service.AgentSettlementService;
import com.pig4cloud.pig.order.settlementDetail.entity.SettlementMoney;
import com.pig4cloud.pig.order.settlementDetail.service.SettlementDetailService;
import com.pig4cloud.pig.settings.commission.service.CommissionService;
import com.pig4cloud.pig.settings.message.entity.Message;
import com.pig4cloud.pig.settings.message.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

/**
 * 结算单
 *
 * @author zhuzubin
 * @date 2019-04-05 23:20:28
 */
@AllArgsConstructor
@Service("agentSettlementService")
public class AgentSettlementServiceImpl extends ServiceImpl<AgentSettlementMapper, AgentSettlement> implements AgentSettlementService {
	private final SettlementDetailService settlementDetailService;
	private final AgentService agentService;
	private final MessageService messageService;
	private final CommissionService commissionService;

	/**
	 * 结算单简单分页查询
	 *
	 * @param agentSettlement 结算单
	 * @return
	 */
	@Override
	public IPage<AgentSettlement> getAgentSettlementPage(Page<AgentSettlement> page, AgentSettlement agentSettlement) {
		return baseMapper.getAgentSettlementPage(page, agentSettlement);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(Agent agent, AgentSettlementSaveDTO saveDTO) {
		SettlementMoney sum = settlementDetailService.getAgentSettlementMoney(agent.getAgentId());
		if (sum == null || sum.getSettlementPrice() == null || sum.getSettlementPrice().compareTo(new BigDecimal(0)) <= 0) {
			throw new RuntimeException("没有可结算金额，请刷新后重试");
		}
		AgentSettlement agentSettlement = AgentSettlement.builder()
			.code(generateOrderCode(agent.getAgentId()))
			.total(sum.getSettlementPrice())
			.originalTotal(sum.getOriginalSettlementPrice())
			.commission(sum.getCommission())
			.userId(agent.getAgentId())
			.reqTime(LocalDateTime.now()).build();
		boolean result = save(agentSettlement);
		if (result) {
			if (!settlementDetailService.updateSettlementDetailOrderId(agent.getAgentId(), agentSettlement.getId())) {
				throw new RuntimeException("申请失败");
			}
		}
		return result;
	}

	private String generateOrderCode(Integer agentId) {
		String header = agentId.toString();
		int fillCount = 8 - header.length();
		for (int i = 0; i < fillCount; i++) {
			header = "0" + header;
		}
		String date = DateUtil.format(new Date(), "yyyMMdd");
		return date + header + System.currentTimeMillis();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean audit(AgentSettlement sett) {
		// 更新申请单数据
		boolean result = updateById(sett);
		// 更新代理商可提现金额
		Agent agent = agentService.getById(sett.getUserId());
		if (result && 1 == sett.getStatus()) {
			if (agent != null) {
				BigDecimal withdrawalPrice = Optional.ofNullable(agent.getWithdrawalPrice()).orElse(new BigDecimal(0));
				agent.setWithdrawalPrice(withdrawalPrice.add(sett.getTotal()).setScale(2, RoundingMode.HALF_UP));
			}
			result = agentService.updateById(agent);
			if (!result) {
				throw new RuntimeException("审核失败");
			}
		}
		if (result) {
			// 发送系统消息
			Message message = new Message();
			message.setCreatorId(sett.getAuditorId());
			message.setUserId(agent.getAgentId());
			message.setCreateDate(sett.getAuditTime());
			String reqTime = sett.getReqTime().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_MINUTE_PATTERN));
			message.setTitle("您" + reqTime + "提交的结算申请有新消息");
			String flag = sett.getStatus() == 2 ? "拒绝" : "通过";
			String remark = StringUtils.isEmpty(sett.getRemark()) ? "" : sett.getRemark();
			message.setContent("您" + reqTime + "提交的结算申请审核" + flag + "；" + remark);
			messageService.save(message);
		}
		return result;
	}
}
