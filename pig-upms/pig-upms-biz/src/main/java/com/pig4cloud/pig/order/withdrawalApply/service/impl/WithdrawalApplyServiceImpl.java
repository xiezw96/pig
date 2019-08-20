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
package com.pig4cloud.pig.order.withdrawalApply.service.impl;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.service.AgentService;
import com.pig4cloud.pig.order.withdrawalApply.dto.WithdrawalApplyReqDTO;
import com.pig4cloud.pig.order.withdrawalApply.entity.WithdrawalApply;
import com.pig4cloud.pig.order.withdrawalApply.mapper.WithdrawalApplyMapper;
import com.pig4cloud.pig.order.withdrawalApply.service.WithdrawalApplyService;
import com.pig4cloud.pig.settings.message.entity.Message;
import com.pig4cloud.pig.settings.message.service.MessageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 提现申请
 *
 * @author zhuzubin
 * @date 2019-04-05 23:23:54
 */
@Service("withdrawalApplyService")
@AllArgsConstructor
public class WithdrawalApplyServiceImpl extends ServiceImpl<WithdrawalApplyMapper, WithdrawalApply> implements WithdrawalApplyService {
	private final AgentService agentService;
	private final MessageService messageService;

	/**
	 * 提现申请简单分页查询
	 *
	 * @param withdrawalApply 提现申请
	 * @return
	 */
	@Override
	public IPage<WithdrawalApply> getWithdrawalApplyPage(Page<WithdrawalApply> page, WithdrawalApply withdrawalApply) {
		return baseMapper.getWithdrawalApplyPage(page, withdrawalApply);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean requestWithdrawalApply(Agent agent, WithdrawalApplyReqDTO reqDTO) {
		// 创建申请单
		WithdrawalApply req = WithdrawalApply.builder()
			.code(generateOrderCode(agent.getAgentId()))
			.total(reqDTO.getReqMoney())
			.userId(agent.getAgentId())
			.reqTime(LocalDateTime.now()).build();
		boolean result = this.save(req);
		if (result) {
			// 减少代理商可提现金额
			BigDecimal newMoney = agent.getWithdrawalPrice().subtract(reqDTO.getReqMoney());
			Agent updateAgent = new Agent();
			updateAgent.setAgentId(agent.getAgentId());
			updateAgent.setWithdrawalPrice(newMoney);
			result = agentService.updateById(updateAgent);
			if (!result) {
				throw new RuntimeException("申请出错了");
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
	public boolean audit(WithdrawalApply ap) {
		// 更新申请单数据
		boolean result = updateById(ap);
		// 更新代理商可提现金额
		// 提现申请单被拒绝，可结算金额恢复减少的部分
		Agent agent = agentService.getById(ap.getUserId());
		if (result && 2 == ap.getAuditStatus()) {
			if (agent != null) {
				agent.setWithdrawalPrice(agent.getWithdrawalPrice().add(ap.getTotal()));
			}
			result = agentService.updateById(agent);
			if (!result) {
				throw new RuntimeException("审核失败");
			}
		}
		if (result) {
			// 发送系统消息
			Message message = new Message();
			message.setCreatorId(ap.getAuditorId());
			message.setUserId(agent.getAgentId());
			message.setCreateDate(ap.getAuditTime());
			String reqTime = ap.getReqTime().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_MINUTE_PATTERN));
			message.setTitle("您" + reqTime + "提交的提现申请有新消息");
			String flag = ap.getAuditStatus() == 2 ? "拒绝" : "通过";
			String remark = StringUtils.isEmpty(ap.getRemark()) ? "" : ap.getRemark();
			message.setContent("您" + reqTime + "提交的提现申请审核" + flag + "；" + remark);
			messageService.save(message);
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean remit(WithdrawalApply ap) {
		// 更新申请单数据
		boolean result = updateById(ap);
		// 更新代理商可提现金额
		// 提现申请单被拒绝，可结算金额恢复减少的部分
		if (result) {
			Agent agent = agentService.getById(ap.getUserId());
			// 发送系统消息
			Message message = new Message();
			message.setCreatorId(ap.getRemitUserId());
			message.setUserId(agent.getAgentId());
			message.setCreateDate(ap.getRemitTime());
			String reqTime = ap.getReqTime().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_MINUTE_PATTERN));
			message.setTitle("您" + reqTime + "提交的提现申请有新消息");
			message.setContent("您" + reqTime + "提交的提现申请已打款；");
			messageService.save(message);
		}
		return result;
	}
}
