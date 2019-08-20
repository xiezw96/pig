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
package com.pig4cloud.pig.order.agentSettlement.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.service.AgentService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.order.agentSettlement.dto.AgentSettlementAuditDTO;
import com.pig4cloud.pig.order.agentSettlement.dto.AgentSettlementSaveDTO;
import com.pig4cloud.pig.order.agentSettlement.entity.AgentSettlement;
import com.pig4cloud.pig.order.agentSettlement.service.AgentSettlementService;
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
 * 结算单
 *
 * @author zhuzubin
 * @date 2019-04-05 23:20:28
 */
@Api(value = "agentsettlement", tags = "结算单服务")
@RestController
@AllArgsConstructor
@RequestMapping("/agentsettlement")
public class AgentSettlementController {
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private final AgentSettlementService agentSettlementService;
	private final AgentService agentService;

	/**
	 * 简单分页查询
	 *
	 * @param page            分页对象
	 * @param agentSettlement 结算单
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/agent/page")
	public R<IPage<AgentSettlement>> getAgentSettlementPageByAgent(Page<AgentSettlement> page, AgentSettlement agentSettlement) {
		agentSettlement.setUserId(SecurityUtils.getUser().getId());
		return new R<>(agentSettlementService.getAgentSettlementPage(page, agentSettlement));
	}

	/**
	 * 简单分页查询
	 *
	 * @param page            分页对象
	 * @param agentSettlement 结算单
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<AgentSettlement>> getAgentSettlementPage(Page<AgentSettlement> page, AgentSettlement agentSettlement) {
		return new R<>(agentSettlementService.getAgentSettlementPage(page, agentSettlement));
	}


	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
	@GetMapping("/{id}")
	public R<AgentSettlement> getById(@PathVariable("id") Integer id) {
		return new R<>(agentSettlementService.getById(id));
	}

	/**
	 * 审核结算申请单
	 *
	 * @param dto
	 * @return R
	 */
	@ApiOperation(value = "审核结算申请单", notes = "审核结算申请单")
	@PutMapping("/audit")
	@PreAuthorize("@pms.hasPermission('settlement_audit_list')")
	public R<Boolean> audit(@RequestBody AgentSettlementAuditDTO dto) {
		AgentSettlement sett = agentSettlementService.getById(dto.getId());
		if (sett.getStatus() != 0) {
			return new R(Boolean.FALSE, "申请单已审核");
		}
		sett.setStatus(dto.getStatus());
		sett.setRemark(dto.getRemark());
		sett.setAuditorId(SecurityUtils.getUser().getId());
		sett.setAuditTime(LocalDateTime.now());
		return new R<>(agentSettlementService.audit(sett));
	}


	/**
	 * 代理商发起结算申请
	 *
	 * @param saveDTO
	 * @return R
	 */
	@ApiOperation(value = "代理商发起结算申请", notes = "代理商发起结算申请")
	@SysLog("新增结算单")
	@PostMapping
	public R save(@RequestBody AgentSettlementSaveDTO saveDTO) {
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
		if (StringUtils.isEmpty(saveDTO.getTransactionPwd())) {
			return new R(Boolean.FALSE, "密码为空");
		}
		if (!ENCODER.matches(saveDTO.getTransactionPwd(), agent.getTransactionPassword())) {
			return new R(Boolean.FALSE, "密码错误");
		}
		return new R<>(agentSettlementService.save(agent, saveDTO));
	}
}
