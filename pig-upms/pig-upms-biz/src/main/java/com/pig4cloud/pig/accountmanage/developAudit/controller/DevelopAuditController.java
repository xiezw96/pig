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
package com.pig4cloud.pig.accountmanage.developAudit.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.service.AgentService;
import com.pig4cloud.pig.accountmanage.developAudit.dto.DevelopAuditDTO;
import com.pig4cloud.pig.accountmanage.developAudit.dto.DevelopAuditDetailDTO;
import com.pig4cloud.pig.accountmanage.developAudit.dto.DevelopAuditReqDTO;
import com.pig4cloud.pig.accountmanage.developAudit.dto.DevelopAwardDataDTO;
import com.pig4cloud.pig.accountmanage.developAudit.entity.DevelopAudit;
import com.pig4cloud.pig.accountmanage.developAudit.service.DevelopAuditService;
import com.pig4cloud.pig.accountmanage.developAuditDetail.entity.DevelopAuditDetail;
import com.pig4cloud.pig.accountmanage.developAuditDetail.service.DevelopAuditDetailService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.settings.commission.service.CommissionService;
import com.pig4cloud.pig.settings.developAward.entity.DevelopAward;
import com.pig4cloud.pig.settings.developAward.service.DevelopAwardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;


/**
 * 发展奖励申请
 *
 * @author zhuzubin
 * @date 2019-04-05 23:38:35
 */
@Api(value = "developaudit", tags = "发展奖励申请管理")
@RestController
@AllArgsConstructor
@RequestMapping("/developaudit")
public class DevelopAuditController {
	private final AgentService agentService;
	private final DevelopAwardService developAwardService;
	private final DevelopAuditService developAuditService;
	private final DevelopAuditDetailService developAuditDetailService;
	private final CommissionService commissionService;

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	/**
	 * 简单分页查询
	 *
	 * @param page         分页对象
	 * @param developAudit 发展收益审核申请
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<DevelopAudit>> getDevelopAuditPage(Page<DevelopAudit> page, DevelopAudit developAudit) {
		return new R<>(developAuditService.getDevelopAuditPage(page, developAudit));
	}

	@ApiOperation(value = "查询代理商发展收益数据", notes = "包含累计奖励和待结算奖励")
	@GetMapping("/agent/data")
	public R<DevelopAwardDataDTO> getAgentDevelopData() {
		DevelopAward award = developAwardService.list().stream().findFirst().orElse(null);
		if (award == null) {
			return new R(Boolean.FALSE, "未设置发展奖励，请联系管理员");
		}
		Integer agentId = SecurityUtils.getUser().getId();
		BigDecimal awardMoney = award.getAwardMoney();
		List<Integer> settlementAgentIds = developAuditService.getDevelopSettlementAgentId(agentId);
		BigDecimal commission = commissionService.getCommission();
		if (settlementAgentIds == null || settlementAgentIds.isEmpty()) {
			awardMoney = new BigDecimal(0);
		} else {
			awardMoney = awardMoney.multiply(new BigDecimal(settlementAgentIds.size()));
		}
		return new R(DevelopAwardDataDTO.builder()
			.settlementCount(settlementAgentIds.size())
			.sumMonty(developAuditService.getDevelopSumMonty(agentId))
			.commission(commissionService.getCommissionValue(commission, awardMoney).setScale(2, RoundingMode.HALF_UP))
			.originalSettlementMonty(awardMoney)
			.settlementMonty(commissionService.subtractCommission(commission, awardMoney).setScale(2, RoundingMode.HALF_UP)).build());
	}

	@ApiOperation(value = "查询代理商发展奖励申请单")
	@GetMapping("/page/agent")
	public R<IPage<DevelopAudit>> getAgentDevelopAuditPage(Page<DevelopAudit> page, DevelopAudit developAudit) {
		developAudit.setAgentId(SecurityUtils.getUser().getId());
		return new R<>(developAuditService.getDevelopAuditPage(page, developAudit));
	}

	@ApiOperation("根据申请单ID查询发展奖励申请单详细信息")
	@GetMapping("/info/{id}")
	public R<DevelopAuditDetailDTO> getById(
		@PathVariable
		@ApiParam("申请单ID") Integer id) {
		DevelopAudit developAudit = developAuditService.getById(id);
		if (developAudit == null) {
			return new R(Boolean.FALSE, "申请单不存在");
		}
		Page<DevelopAuditDetail> page = new Page<>();
		page.setSize(999999);
		IPage<DevelopAuditDetail> detailResult = developAuditDetailService.getDevelopAuditDetailPage(page, DevelopAuditDetail.builder().developId(id).build());
		DevelopAuditDetailDTO result = DevelopAuditDetailDTO.builder()
			.developAuditDetails(detailResult.getRecords())
			.build();
		BeanUtils.copyProperties(developAudit, result);
		return new R(result);
	}

	/**
	 * 审核结算申请单
	 *
	 * @param dto
	 * @return R
	 */
	@ApiOperation(value = "审核发展奖励申请单", notes = "审核发展奖励申请单")
	@PutMapping("/audit")
	@PreAuthorize("@pms.hasPermission('develop_audit_list')")
	public R<Boolean> audit(@RequestBody DevelopAuditDTO dto) {
		DevelopAudit deva = developAuditService.getById(dto.getId());
		if (deva.getAuditStatus() != 0) {
			return new R(Boolean.FALSE, "申请单已审核");
		}
		deva.setAuditStatus(dto.getStatus());
		deva.setRemark(dto.getRemark());
		deva.setCreatorId(SecurityUtils.getUser().getId());
		deva.setCreateDate(LocalDateTime.now());
		return new R<>(developAuditService.audit(deva));
	}

	@ApiOperation(value = "代理商发起发展审核请求")
	@PostMapping("/request")
	public R requestAgentDevelopAudit(@RequestBody DevelopAuditReqDTO reqDTO) {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		if (agent.getAuditStatus() != 1) {
			return new R(Boolean.FALSE, "您当前还未通过审核，无法结算");
		}
		if (StringUtils.isEmpty(agent.getTransactionPassword())) {
			return new R(Boolean.FALSE, "未设置交易密码，请至个人中心进行设置");
		}
		if (!ENCODER.matches(reqDTO.getTransactionPwd(), agent.getTransactionPassword())) {
			return new R(Boolean.FALSE, "密码错误");
		}
		DevelopAward award = developAwardService.list().stream().findFirst().orElse(null);
		if (award == null) {
			return new R(Boolean.FALSE, "未设置发展奖励，请联系管理员");
		}
		BigDecimal awardMoney = award.getAwardMoney();
		List<Integer> settlementAgentIds = developAuditService.getDevelopSettlementAgentId(agent.getAgentId());
		if (settlementAgentIds.size() <= 0) {
			return new R(Boolean.FALSE, "您没有可结算奖励");
		}
		BigDecimal totalMoney = awardMoney.multiply(new BigDecimal(settlementAgentIds.size()));
		return new R(developAuditService.requestAgentDevelopAudit(settlementAgentIds, totalMoney, awardMoney, agent, reqDTO));
	}

}
