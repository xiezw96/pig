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
package com.pig4cloud.pig.accountmanage.agent.service.impl;

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentReferralDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentRegistDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentResetPwdDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentSaveDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentUpdateDTO;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.entity.AgentChain;
import com.pig4cloud.pig.accountmanage.agent.entity.AgentReverseChain;
import com.pig4cloud.pig.accountmanage.agent.mapper.AgentMapper;
import com.pig4cloud.pig.accountmanage.agent.service.AgentService;
import com.pig4cloud.pig.accountmanage.shoppingAddress.entity.ShoppingAddress;
import com.pig4cloud.pig.accountmanage.shoppingAddress.service.ShoppingAddressService;
import com.pig4cloud.pig.admin.api.dto.UserDTO;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.admin.service.SysUserService;
import com.pig4cloud.pig.common.security.util.ClientDetailsThreadLocal;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.settings.message.entity.Message;
import com.pig4cloud.pig.settings.message.service.MessageService;
import com.pig4cloud.pig.utils.ShareCodeUtil;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 代理商管理
 *
 * @author zhuzubin
 * @date 2019-04-05 22:48:31
 */
@Service("agentService")
@AllArgsConstructor
public class AgentServiceImpl extends ServiceImpl<AgentMapper, Agent> implements AgentService {
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();
	private final SysUserService userService;
	private final MessageService messageService;
	private final ShoppingAddressService shoppingAddressService;

	@Override
	public Agent getCurrentAgent() {
		return this.getById(SecurityUtils.getUser().getId());
	}

	/**
	 * 代理商管理简单分页查询
	 *
	 * @param agent 代理商管理
	 * @return
	 */
	@Override
	public IPage<Agent> getAgentPage(Page<Agent> page, Agent agent) {
		return baseMapper.getAgentPage(page, agent);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean audit(Agent agent) {
		boolean result = false;
		// 发送系统消息
		Message message = new Message();
		message.setCreatorId(agent.getAgentId());
		message.setCreateDate(LocalDateTime.now());
		String reqTime = agent.getAuditReqTime().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_MINUTE_PATTERN));
		message.setTitle("您" + reqTime + "提交的代理商审核申请有新消息");
		String flag = agent.getAuditStatus() == 2 ? "拒绝" : "通过";
		String remark = StringUtils.isEmpty(agent.getAuditRemark()) ? "" : agent.getAuditRemark();
		message.setContent("您" + reqTime + "提交的代理商审核申请审核" + flag + "；" + remark);
		result = messageService.save(message);
		// 更新状态
		if (result) result = updateById(agent);

		if (!result) {
			throw new RuntimeException("审核失败");
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean updateAudit(Agent agent) {
		boolean result = false;
		// 发送系统消息
		Message message = new Message();
		message.setCreatorId(agent.getUpdateAuditorId());
		message.setUserId(agent.getAgentId());
		message.setCreateDate(LocalDateTime.now());
		String reqTime = agent.getUpdateAuditReqTime().format(DateTimeFormatter.ofPattern(DatePattern.NORM_DATETIME_MINUTE_PATTERN));
		message.setTitle("认证信息修改审核结果");
		String flag = agent.getUpdateAuditStatus() == 2 ? "拒绝" : "通过";
		String remark = StringUtils.isEmpty(agent.getUpdateAuditRemark()) ? "" : agent.getUpdateAuditRemark();
		message.setContent("您" + reqTime + "提交的信息修改申请,已审核" + flag + "；" + remark);
		result = messageService.save(message);
		Agent updateAgent = new Agent();
		updateAgent.setAgentId(agent.getAgentId());
		updateAgent.setUpdateAuditStatus(agent.getUpdateAuditStatus());
		updateAgent.setUpdateAuditTime(LocalDateTime.now());
		updateAgent.setUpdateAuditorId(agent.getUpdateAuditorId());
		updateAgent.setUpdateAuditRemark(agent.getUpdateAuditRemark());

		if (agent.getUpdateAuditStatus() == 1) {
			// 审核不通过，只更新审核字段
			updateAgent.setIdentityCardFront(agent.getNewIdentityCardFront());
			updateAgent.setIdentityCardVerso(agent.getNewIdentityCardVerso());
			updateAgent.setBankCardFront(agent.getNewBankCardFront());
			updateAgent.setBankCardVerso(agent.getNewBankCardVerso());
		}
		// 更新状态
		if (result) result = updateById(updateAgent);

		if (!result) {
			throw new RuntimeException("信息修改审核失败");
		}
		return result;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(AgentSaveDTO agentDTO) {
		if (getAgentCountByPhone(agentDTO.getPhone()) > 0) {
			throw new RuntimeException("手机号码已存在");
		}
		Agent agent = new Agent();
		BeanUtils.copyProperties(agentDTO, agent);
		// 设置推荐人
		Agent referralAgent = baseMapper.getAgentByPhoneOrReferralCode(agentDTO.getReferrerCode());
		if (referralAgent != null) {
			agent.setReferrerId(referralAgent.getAgentId());
			// 根据上级设置分润比例
			agent.setProfit(referralAgent.getChildProfit());
			agent.setReferrerIds(getReferrerIds(referralAgent));
		}
		// 创建系统账号
		UserDTO userDTO = new UserDTO();
		userDTO.setUsername(agentDTO.getPhone());
		userDTO.setPhone(agentDTO.getPhone());
		userDTO.setPassword(ENCODER.encode(agentDTO.getPwd()));
		userDTO.setUsertype(ClientDetailsThreadLocal.AGENT_USER_TYPE);
		userDTO.setShowName(agentDTO.getName());
		userService.save(userDTO);
		agent.setAgentId(userDTO.getUserId());
		// 生成推荐码
		agent.setReferralCode(ShareCodeUtil.toSerialCode(agent.getAgentId()));
		return save(agent);
	}

	private String getReferrerIds(Agent referralAgent) {
		String referrerIds = referralAgent.getAgentId() + ",";
		if (referralAgent.getReferrerIds() != null) {
			referrerIds = referralAgent.getReferrerIds() + referrerIds;
		} else {
			referrerIds = "," + referrerIds;
		}
		return referrerIds;
	}

	@Override
	public boolean updateById(AgentUpdateDTO agentDTO) {
		Agent agent = getById(agentDTO.getAgentId());
		BeanUtils.copyProperties(agentDTO, agent);
		return updateById(agent);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean removeById(Serializable id) {
		Agent agent = this.getById(id);
		if (agent == null) {
			return true;
		} else {
			SysUser sysUser = new SysUser();
			sysUser.setUserId(agent.getAgentId());
			sysUser.setUsername(agent.getPhone());
			boolean userFlag = userService.removeUserById(sysUser);
			if (!userFlag) {
				return false;
			}
			// 移除收货地址
			shoppingAddressService.remove(Wrappers.<ShoppingAddress>query().lambda().eq(ShoppingAddress::getCreatorId, id));
			return super.removeById(id);
		}
	}

	@Override
	public int getAgentCountByPhone(String phone) {
		return userService.count(Wrappers.<SysUser>query().lambda().eq(SysUser::getUsername, phone).eq(SysUser::getUsertype, ClientDetailsThreadLocal.AGENT_USER_TYPE));
	}

	@Override
	public AgentChain getAgentChain(Integer agentId) {
		if (agentId == null) {
			return null;
		}
		Agent agent = getById(agentId);
		if (agent != null) {
			AgentChain agentChain = AgentChain.builder().agent(agent).parent(null).build();
			if (agent.getReferrerId() == null)
				return agentChain;
			else {
				agentChain.setParent(getParent(agentChain, listByIds(Arrays.asList(agent.getReferrerIds().split(",")).stream().filter(s -> StringUtils.isNotEmpty(s)).collect(Collectors.toList()))));
			}
			return agentChain;
		}
		return null;
	}

	private AgentChain getParent(AgentChain agentChain, Collection<Agent> parents) {
		Agent parent = parents.stream()
			.filter(p -> p.getAgentId().equals(agentChain.getAgent().getReferrerId()))
			.findFirst()
			.orElse(null);
		if (parent == null)
			return null;
		AgentChain parentAgentChain = AgentChain
			.builder()
			.agent(parent)
			.build();
		parentAgentChain.setParent(getParent(parentAgentChain, parents));
		return parentAgentChain;
	}

	@Override
	public AgentReverseChain getAgentReverseChain(Agent agent) {
		if (agent == null) {
			return null;
		}
		if (agent != null) {
			String referrerIds = getReferrerIds(agent);
			List<Agent> allChild = list(Wrappers.<Agent>query().lambda().likeRight(Agent::getReferrerIds, referrerIds));
			return fillAgentReverseChain(allChild, agent);
		}
		return null;
	}

	public AgentReverseChain fillAgentReverseChain(List<Agent> allChild, Agent agent) {
		if (agent == null)
			return null;
		String referrerIds = getReferrerIds(agent);
		AgentReverseChain chain = AgentReverseChain.builder().agent(agent).build();
		chain.setChildren(allChild.stream()
			.filter(child -> child.getReferrerIds().equals(referrerIds))
			.map(child -> fillAgentReverseChain(allChild, child))
			.filter(child -> Optional.ofNullable(child).isPresent())
			.collect(Collectors.toList()));
		return chain;
	}

	@Override
	public boolean registAgent(AgentRegistDTO agentRegistDTO) {
		AgentSaveDTO agentSaveDTO = new AgentSaveDTO();
		BeanUtils.copyProperties(agentRegistDTO, agentSaveDTO);
		return save(agentSaveDTO);
	}

	@Override
	public boolean referralAgent(Agent agent, AgentReferralDTO agentReferralDTO) {
		AgentSaveDTO agentSaveDTO = new AgentSaveDTO();
		BeanUtils.copyProperties(agentReferralDTO, agentSaveDTO);
		// 密码为身份证后6位
		String idCard = agentReferralDTO.getIdCard();
		agentSaveDTO.setPwd(idCard.substring(idCard.length() - 6, idCard.length()));
		// 设置当前登录的代理商为被推荐代理商的推荐人
		agentSaveDTO.setReferrerCode(agent.getReferralCode());
		agentSaveDTO.setAuditReqTime(LocalDateTime.now());
		agentSaveDTO.setAuditStatus(0);
		return save(agentSaveDTO);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public int setCommissionRatio(Agent agent, BigDecimal ratio) {
		agent.setChildProfit(ratio);
		updateById(agent);
		return baseMapper.setCommissionRatio(agent.getAgentId(), ratio);
	}

	@Override
	public BigDecimal getChildrenSumRatio(BigDecimal ratio, boolean isFirst, AgentReverseChain child) {
		// 加上当前下级的分润比例
		ratio = isFirst ? ratio : ratio.add(child.getAgent().getProfit());
		if (child.getChildren() != null && !child.getChildren().isEmpty()) {
			return getChildrenSumRatio(ratio, false, child.getChildren().get(0));
		}
		return ratio;
	}

	@Override
	public boolean resetPwd(Agent agent, AgentResetPwdDTO dto) {
		SysUser user = new SysUser();
		user.setUserId(agent.getAgentId());
		user.setPassword(dto.getPwd());
		return userService.updateById(user);
	}
}
