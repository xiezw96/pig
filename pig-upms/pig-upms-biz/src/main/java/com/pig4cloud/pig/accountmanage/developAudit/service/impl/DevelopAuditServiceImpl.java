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
package com.pig4cloud.pig.accountmanage.developAudit.service.impl;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.service.AgentService;
import com.pig4cloud.pig.accountmanage.developAudit.dto.DevelopAuditReqDTO;
import com.pig4cloud.pig.accountmanage.developAudit.entity.DevelopAudit;
import com.pig4cloud.pig.accountmanage.developAudit.mapper.DevelopAuditMapper;
import com.pig4cloud.pig.accountmanage.developAudit.service.DevelopAuditService;
import com.pig4cloud.pig.accountmanage.developAuditDetail.entity.DevelopAuditDetail;
import com.pig4cloud.pig.accountmanage.developAuditDetail.service.DevelopAuditDetailService;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 发展奖励申请
 *
 * @author zhuzubin
 * @date 2019-04-05 23:38:35
 */
@Service("developAuditService")
@AllArgsConstructor
public class DevelopAuditServiceImpl extends ServiceImpl<DevelopAuditMapper, DevelopAudit> implements DevelopAuditService {
	private final DevelopAuditDetailService developAuditDetailService;
	private final AgentService agentService;
	private final MessageService messageService;
	private final CommissionService commissionService;

	/**
	 * 发展奖励申请简单分页查询
	 *
	 * @param developAudit 发展奖励申请
	 * @return
	 */
	@Override
	public IPage<DevelopAudit> getDevelopAuditPage(Page<DevelopAudit> page, DevelopAudit developAudit) {
		return baseMapper.getDevelopAuditPage(page, developAudit);
	}

	@Override
	public BigDecimal getDevelopSumMonty(Integer agentId) {
		return baseMapper.getDevelopSumMonty(agentId);
	}

	@Override
	public List<Integer> getDevelopSettlementAgentId(Integer agentId) {
		return baseMapper.getDevelopSettlementAgentId(agentId);
	}

	@Override
	public boolean requestAgentDevelopAudit(List<Integer> settlementAgentIds, BigDecimal totalMoney, BigDecimal awardMoney, Agent agent, DevelopAuditReqDTO reqDTO) {
		BigDecimal commission = commissionService.getCommission();
		// 创建发展结算申请单
		DevelopAudit developAudit = DevelopAudit.builder()
			.agentId(agent.getAgentId())
			.agentName(agent.getName())
			.totalMoney(commissionService.subtractCommission(commission, totalMoney))
			.originalTotalMoney(totalMoney)
			.commission(commissionService.getCommissionValue(commission, totalMoney))
			.developNum(settlementAgentIds.size())
			.auditStatus(0)
			.reqTime(LocalDateTime.now())
			.build();
		boolean result = this.save(developAudit);
		// 创建发展结算明细
		if (result) {
			result = developAuditDetailService.saveBatch(settlementAgentIds.stream().map((agentId) ->
				DevelopAuditDetail.builder()
					.agentId(agentId)
					.awardMoney(awardMoney)
					.developId(developAudit.getId()).build()
			).collect(Collectors.toList()));
			if (!result) {
				throw new RuntimeException("申请失败");
			}
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean audit(DevelopAudit deva) {
		// 更新申请单数据
		boolean result = updateById(deva);
		// 更新代理商可提现金额
		Agent agent = agentService.getById(deva.getAgentId());
		if (result && 1 == deva.getAuditStatus()) {
			if (agent != null) {
				BigDecimal withdrawalPrice = Optional.ofNullable(agent.getWithdrawalPrice()).orElse(new BigDecimal(0));
				agent.setWithdrawalPrice(withdrawalPrice.add(deva.getTotalMoney()).setScale(2, RoundingMode.HALF_UP));
			}
			boolean agnetResult = agentService.updateById(agent);
			if (!agnetResult) {
				throw new RuntimeException("审核失败");
			}
		}
		if (result) {
			// 发送系统消息
			Message message = new Message();
			message.setCreatorId(deva.getCreatorId());
			message.setUserId(agent.getAgentId());
			message.setCreateDate(deva.getCreateDate());
			String reqTime = deva.getReqTime().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_MINUTE_PATTERN));
			message.setTitle("您" + reqTime + "提交的发展奖励申请有新消息");
			String flag = deva.getAuditStatus() == 2 ? "拒绝" : "通过";
			String remark = StringUtils.isEmpty(deva.getRemark()) ? "" : deva.getRemark();
			message.setContent("您" + reqTime + "提交的发展奖励申请,已审核" + flag + "；" + remark);
			messageService.save(message);
		}
		return result;
	}

}
