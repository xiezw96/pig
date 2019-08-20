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
package com.pig4cloud.pig.order.withdrawalApply.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.service.AgentService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.order.withdrawalApply.dto.WithdrawalApplyAuditDTO;
import com.pig4cloud.pig.order.withdrawalApply.dto.WithdrawalApplyReqDTO;
import com.pig4cloud.pig.order.withdrawalApply.entity.WithdrawalApply;
import com.pig4cloud.pig.order.withdrawalApply.service.WithdrawalApplyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
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

import java.time.LocalDateTime;


/**
 * 提现申请
 *
 * @author zhuzubin
 * @date 2019-04-05 23:23:54
 */
@Api(value = "withdrawalapply", tags = "提现申请单管理服务")
@RestController
@AllArgsConstructor
@RequestMapping("/withdrawalapply")
public class WithdrawalApplyController {
	private final AgentService agentService;
	private final WithdrawalApplyService withdrawalApplyService;

	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	/**
	 * 简单分页查询
	 *
	 * @param page            分页对象
	 * @param withdrawalApply 提现申请
	 * @return
	 */
	@ApiOperation(value = "分页查询全部代理商提现申请", notes = "查询全部代理商提现申请")
	@GetMapping("/page")
	public R<IPage<WithdrawalApply>> getWithdrawalApplyPage(Page<WithdrawalApply> page, WithdrawalApply withdrawalApply) {
		return new R<>(withdrawalApplyService.getWithdrawalApplyPage(page, withdrawalApply));
	}

	/**
	 * 分页查询代理商提现申请单
	 *
	 * @param page            分页对象
	 * @param withdrawalApply 提现申请
	 * @return
	 */
	@ApiOperation(value = "分页查询代理商提现申请单", notes = "分页查询代理商提现申请单")
	@GetMapping("/page/agenet")
	public R<IPage<WithdrawalApply>> getAgentWithdrawalApplyPage(Page<WithdrawalApply> page, WithdrawalApply withdrawalApply) {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		withdrawalApply.setUserId(agent.getAgentId());
		return new R<>(withdrawalApplyService.getWithdrawalApplyPage(page, withdrawalApply));
	}

	/**
	 * 通过id查询单条记录
	 *
	 * @param dto
	 * @return R
	 */
	@ApiOperation(value = "审核提现申请单", notes = "审核提现申请单")
	@PutMapping("/audit")
	@PreAuthorize("@pms.hasPermission('withdrawal_apply_audit_list')")
	public R<Boolean> audit(@RequestBody WithdrawalApplyAuditDTO dto) {
		WithdrawalApply ap = withdrawalApplyService.getById(dto.getId());
		if (ap.getAuditStatus() != 0) {
			return new R(Boolean.FALSE, "申请单已审核");
		}
		ap.setAuditStatus(dto.getAuditStatus());
		ap.setRemark(dto.getRemark());
		ap.setAuditorId(SecurityUtils.getUser().getId());
		ap.setAuditTime(LocalDateTime.now());
		return new R<>(withdrawalApplyService.audit(ap));
	}

	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "提现打款", notes = "提现打款")
	@PutMapping("/remit/{id}")
	public R<Boolean> remit(@PathVariable("id") Integer id) {
		WithdrawalApply ap = withdrawalApplyService.getById(id);
		if (ap.getAuditStatus() != 1) {
			return new R(Boolean.FALSE, "申请单未审核通过，请确认");
		}
		if (ap.getRemitStatus() != 0) {
			return new R(Boolean.FALSE, "申请单已打款");
		}
		ap.setRemitStatus(1);
		ap.setRemitUserId(SecurityUtils.getUser().getId());
		ap.setRemitTime(LocalDateTime.now());
		return new R<>(withdrawalApplyService.remit(ap));
	}

	@ApiOperation(value = "代理商申请提现", notes = "需要验证交易密码")
	@PostMapping("/request")
	public R requestWithdrawalApply(@RequestBody WithdrawalApplyReqDTO reqDTO) {
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
		if (agent.getWithdrawalPrice() == null || agent.getWithdrawalPrice().compareTo(reqDTO.getReqMoney()) < 0) {
			return new R(Boolean.FALSE, "可提现余额不足");
		}
		return new R(withdrawalApplyService.requestWithdrawalApply(agent, reqDTO));
	}

}


