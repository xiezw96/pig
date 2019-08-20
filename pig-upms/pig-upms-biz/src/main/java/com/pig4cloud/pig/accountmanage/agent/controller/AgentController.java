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
package com.pig4cloud.pig.accountmanage.agent.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentAuditDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentEditDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentInfoDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentModifyPwdDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentReferralDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentRegistDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentRequestUpdateDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentResetPwdDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentSaveDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentTransactionPwdDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentUpdateDTO;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.entity.AgentReverseChain;
import com.pig4cloud.pig.accountmanage.agent.service.AgentService;
import com.pig4cloud.pig.accountmanage.user.entity.User;
import com.pig4cloud.pig.accountmanage.user.service.UserService;
import com.pig4cloud.pig.admin.service.SMSCodeService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.goods.salesMachine.entity.SalesMachine;
import com.pig4cloud.pig.goods.salesMachine.service.SalesMachineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;


/**
 * 代理商管理
 *
 * @author zhuzubin
 * @date 2019-04-05 22:48:31
 */
@Api(value = "agent", tags = "代理商管理")
@RestController
@AllArgsConstructor
@RequestMapping("/agent")
public class AgentController {

	private final AgentService agentService;
	private final SalesMachineService salesMachineService;
	private final SMSCodeService smsCodeService;
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private final UserService userService;

	private static final String SMS_CODE_TYPE_AGENT_REGIST = "agent_regist";
	private static final String SMS_CODE_TYPE_AGENT_PAY_RESETPWD = "agent_pay_resetpwd";
	private static final String SMS_CODE_TYPE_AGENT_RESETPWD = "agent_resetpwd";

	@ApiOperation(value = "代理商注册", notes = "需要先通过短信验证，获取到smsToken")
	@PostMapping("/regist")
	public R regist(@RequestBody AgentRegistDTO agentRegistDTO) {
		if (!smsCodeService.verifyToken(agentRegistDTO.getPhone(), SMS_CODE_TYPE_AGENT_REGIST, agentRegistDTO.getSmsToken())) {
			return new R(Boolean.FALSE, "短信验证已失效，请重新验证");
		}
		return new R(agentService.registAgent(agentRegistDTO));
	}

	@ApiOperation(value = "重置密码", notes = "如果是忘记密码，手机号必填")
	@PutMapping("/resetPwd")
	public R resetPwd(@RequestBody AgentResetPwdDTO dto) {
		if (!smsCodeService.verifyToken(dto.getPhone(), SMS_CODE_TYPE_AGENT_RESETPWD, dto.getSmsToken())) {
			return new R(Boolean.FALSE, "短信验证已失效，请重新验证");
		}
		Agent agent = null;
		if (StringUtils.isNotEmpty(dto.getPhone())) {
			agent = agentService.getOne(Wrappers.<Agent>query().lambda().eq(Agent::getPhone, dto.getPhone()));
		} else {
			agent = agentService.getCurrentAgent();
		}
		if (agent == null) {
			return new R(Boolean.FALSE, "代理商不存在");
		}
		dto.setPwd(ENCODER.encode(dto.getPwd()));
		return new R(agentService.resetPwd(agent, dto));
	}

	@ApiOperation(value = "代理商推荐", notes = "代理商推荐注册")
	@PostMapping("/referral")
	public R referral(@RequestBody AgentReferralDTO agentReferralDTO) {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		return new R(agentService.referralAgent(agent, agentReferralDTO));
	}

	@GetMapping("/isexists")
	@ApiOperation("根据手机号判断代理商是否已经存在，返回数量")
	public R<Integer> getAgentCountByPhone(@RequestParam("phone") String phone) {
		// 根据手机号查询代理商帐号
		return new R(agentService.getAgentCountByPhone(phone));
	}

	/**
	 * 简单分页查询
	 *
	 * @param page  分页对象
	 * @param agent 代理商
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<Agent>> getAgentPage(Page<Agent> page, Agent agent) {
		return new R<>(agentService.getAgentPage(page, agent));
	}

	/**
	 * 查询当前代理商
	 *
	 * @return R
	 */
	@ApiOperation(value = "查询当前代理商", notes = "查询当前代理商")
	@GetMapping
	public R<Agent> getMy() {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null)
			return new R(Boolean.FALSE, "查询代理商信息错误，请联系管理员");
		return new R<>(agent);
	}

	/**
	 * 通过id查询单条记录
	 *
	 * @param agentId
	 * @return R
	 */
	@ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
	@GetMapping("/{agentId}")
	public R<Agent> getById(@PathVariable("agentId") Integer agentId) {
		return new R<>(agentService.getById(agentId));
	}

	/**
	 * 通过id查询代理商明细
	 *
	 * @param agentId
	 * @return R
	 */
	@ApiOperation(value = "通过id查询代理商明细", notes = "通过id查询代理商明细")
	@GetMapping("/info/{agentId}")
	public R getInfoById(@PathVariable("agentId") Integer agentId) {
		Agent agent = agentService.getById(agentId);
		if (agent == null) {
			return new R(Boolean.FALSE, "代理商不存在");
		}
		AgentInfoDTO infoDTO = new AgentInfoDTO();
		BeanUtils.copyProperties(agent, infoDTO);
		// 查询已激活设备
		List<SalesMachine> activeSalesMachines = salesMachineService.list(Wrappers.<SalesMachine>query().lambda().eq(SalesMachine::getBelongsUser, agentId).in(SalesMachine::getStatus, Arrays.asList(1, 2)));
		infoDTO.setActiveSalesMachines(activeSalesMachines.size());
		// 查询待激活设备
		List<SalesMachine> inactiveSalesMachines = salesMachineService.list(Wrappers.<SalesMachine>query().lambda().eq(SalesMachine::getBelongsUser, agentId).in(SalesMachine::getStatus, Arrays.asList(1, 2)));
		infoDTO.setInactiveSalesMachines(inactiveSalesMachines.size());
		// 查询已激活发展代理商
		List<Agent> activeDevelopAgents = agentService.list(Wrappers.<Agent>query().lambda().eq(Agent::getReferrerId, agentId).eq(Agent::getAuditStatus, 1));
		infoDTO.setActiveDevelopAgents(activeDevelopAgents.size());
		// 查询待激活发展代理商
		List<Agent> inactiveDevelopAgents = agentService.list(Wrappers.<Agent>query().lambda().eq(Agent::getReferrerId, agentId).eq(Agent::getAuditStatus, 0));
		infoDTO.setInactiveDevelopAgents(inactiveDevelopAgents.size());

		// 归属用户数量
		infoDTO.setUserCount(userService.count(Wrappers.<User>query().lambda().eq(User::getOfProxy, agent.getAgentId())));
		return new R<>(infoDTO);
	}

	@PutMapping("/transactionpwd")
	@ApiOperation("修改交易密码")
	public R setTransactionPwd(@RequestBody AgentTransactionPwdDTO pwdDTO) {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		if (!smsCodeService.verifyToken(agent.getPhone(), SMS_CODE_TYPE_AGENT_PAY_RESETPWD, pwdDTO.getSmsToken())) {
			return new R(Boolean.FALSE, "短信验证已失效，请重新验证");
		}
		agent.setTransactionPassword(ENCODER.encode(pwdDTO.getTransactionPwd()));
		return new R(agentService.updateById(agent));
	}

	/**
	 * 新增记录
	 *
	 * @param agentDTO
	 * @return R
	 */
	@ApiOperation(value = "新增记录", notes = "新增记录")
	@SysLog("新增代理商")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_agent')")
	public R save(@RequestBody AgentSaveDTO agentDTO) {
		agentDTO.setAuditReqTime(LocalDateTime.now());
		return new R<>(agentService.save(agentDTO));
	}


	@ApiOperation(value = "代理商审核", notes = "代理商审核")
	@SysLog("代理商审核")
	@PostMapping("/audit")
	@PreAuthorize("@pms.hasPermission('agent_audit_list')")
	public R audit(@RequestBody AgentAuditDTO auditDTO) {
		Agent agent = agentService.getById(auditDTO.getAgentId());
		if (agent.getAuditStatus() != null && agent.getAuditStatus() != 0) {
			return new R(Boolean.FALSE, "不是待审核状态");
		}
		BeanUtils.copyProperties(auditDTO, agent);
		agent.setAuditorId(SecurityUtils.getUser().getId());
		agent.setAuditTime(LocalDateTime.now());
		agent.setAuditRemark(auditDTO.getRemark() == null ? "" : auditDTO.getRemark());
		return new R<>(agentService.audit(agent));
	}

	@ApiOperation(value = "代理商信息修改审核", notes = "代理商信息修改审核")
	@SysLog("代理商信息修改审核")
	@PostMapping("/updateaudit")
	@PreAuthorize("@pms.hasPermission('agent_audit_list')")
	public R updateAudit(@RequestBody AgentAuditDTO auditDTO) {
		Agent agent = agentService.getById(auditDTO.getAgentId());
		if (agent.getUpdateAuditStatus() != null && agent.getUpdateAuditStatus() != 0) {
			return new R(Boolean.FALSE, "不是待审核状态");
		}
		agent.setUpdateAuditRemark(auditDTO.getRemark());
		agent.setUpdateAuditStatus(auditDTO.getAuditStatus());
		agent.setUpdateAuditorId(SecurityUtils.getUser().getId());
		agent.setUpdateAuditRemark(auditDTO.getRemark() == null ? "" : auditDTO.getRemark());
		return new R<>(agentService.updateAudit(agent));
	}

	@ApiOperation(value = "总部修改代理商密码", notes = "总部修改代理商密码")
	@PutMapping("/modifyPwd")
	@PreAuthorize("@pms.hasPermission('sys_agent')")
	public R modifyPwd(@RequestBody AgentModifyPwdDTO pwdDTO) {
		Agent agent = agentService.getById(pwdDTO.getAgentId());
		if (agent == null)
			return new R(Boolean.FALSE, "代理商不存在");
		AgentResetPwdDTO dto = new AgentResetPwdDTO();
		dto.setPwd(ENCODER.encode(pwdDTO.getPwd()));
		return new R<>(agentService.resetPwd(agent, dto));
	}

	/**
	 * 修改记录
	 *
	 * @param agentDTO
	 * @return R
	 */
	@ApiOperation(value = "修改记录", notes = "修改记录")
	@SysLog("修改代理商")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_agent')")
	public R update(@RequestBody AgentUpdateDTO agentDTO) {
		return new R<>(agentService.updateById(agentDTO));
	}

	@ApiOperation(value = "更新个人信息", notes = "更新个人信息")
	@SysLog("更新个人信息")
	@PutMapping("/edit")
	public R edit(@RequestBody AgentEditDTO agentDTO) {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		BeanUtils.copyProperties(agentDTO, agent);
		return new R<>(agentService.updateById(agent));
	}

	@ApiOperation(value = "代理商请求修改信息")
	@PutMapping("/requestUpdate")
	public R requestUpdate(@RequestBody AgentRequestUpdateDTO agentDTO) {
		Agent agent = agentService.getCurrentAgent();
		// 第一次提交认证信息或者审核被拒绝，重新提交审核
		if (agent.getAuditStatus() == null || agent.getAuditStatus() == 2) {
			// 修改为待审核
			agent.setAuditStatus(0);
			agent.setAuditReqTime(LocalDateTime.now());
			BeanUtils.copyProperties(agentDTO, agent);
		} else if (agent.getAuditStatus() == 1) {
			// 代理商审核通过，再次提交标识请求信息修改审核
			if (agent.getUpdateAuditStatus() != null && agent.getUpdateAuditStatus() == 0) {
				return new R(Boolean.FALSE, "信息审核中，无法再次提交。");
			}
			agent.setNewWechatId(agentDTO.getWechatId());
			agent.setNewBankAccountName(agentDTO.getBankAccountName());
			agent.setNewBankCardFront(agentDTO.getBankCardFront());
			agent.setNewBankCardVerso(agentDTO.getBankCardVerso());
			agent.setNewIdentityCardFront(agentDTO.getIdentityCardFront());
			agent.setNewIdentityCardVerso(agentDTO.getIdentityCardVerso());
			// 信息修改待审核
			agent.setUpdateAuditStatus(0);
			agent.setUpdateAuditReqTime(LocalDateTime.now());
			agent.setUpdateAuditorId(null);
			agent.setUpdateAuditTime(null);
			agent.setUpdateAuditRemark(null);
		} else {
			BeanUtils.copyProperties(agentDTO, agent);
			agent.setIdCard(null);
			agent.setName(null);
		}
		return new R<>(agentService.updateById(agent));
	}


	/**
	 * 通过id删除一条记录
	 *
	 * @param agentId
	 * @return R
	 */
	@ApiOperation(value = "通过id删除一条记录", notes = "通过id删除一条记录")
	@SysLog("删除代理商")
	@DeleteMapping("/{agentId}")
	@PreAuthorize("@pms.hasPermission('sys_agent')")
	public R removeById(@PathVariable Integer agentId) {
		return new R<>(agentService.removeById(agentId));
	}

	@ApiOperation(value = "获取可提现金额", notes = "代理商提现首页获取可提现金额")
	@GetMapping("/withdrawal_price")
	public R<BigDecimal> getAgentWithdrawalPrice() {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		return new R(agent.getWithdrawalPrice());
	}

	@ApiOperation(value = "设置下级抽佣比例", notes = "如果设置比例大于100%，返回错误")
	@PutMapping("/commissionratio")
	public R<Boolean> setCommissionRatio(@RequestBody @ApiParam("下级佣金比例") BigDecimal ratio) {
		if (ratio == null) {
			return new R(Boolean.FALSE, "参数为空");
		}
		if (ratio.compareTo(new BigDecimal(1)) > 0) {
			return new R(Boolean.FALSE, "抽佣比例不能大于100%");
		}
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		// 查询全部下级
		AgentReverseChain agentReverseChain = agentService.getAgentReverseChain(agent);
		if (agentReverseChain.getChildren() != null && !agentReverseChain.getChildren().isEmpty()) {
			if (agentReverseChain.getChildren().stream().anyMatch((child) -> agentService.getChildrenSumRatio(new BigDecimal(0), true, child).compareTo(ratio) > 0)) {
				return new R(Boolean.FALSE, "比例低于下级代理商已分配出的总比例");
			}
		}
		agentService.setCommissionRatio(agent, ratio);
		return new R(Boolean.TRUE);
	}

}
