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
package com.pig4cloud.pig.accountmanage.agent.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentReferralDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentRegistDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentResetPwdDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentSaveDTO;
import com.pig4cloud.pig.accountmanage.agent.dto.AgentUpdateDTO;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.entity.AgentChain;
import com.pig4cloud.pig.accountmanage.agent.entity.AgentReverseChain;

import java.math.BigDecimal;

/**
 * 代理商管理
 *
 * @author zhuzubin
 * @date 2019-04-05 22:48:31
 */
public interface AgentService extends IService<Agent> {

	/**
	 * 获取当前登录的代理商
	 * <p>Title: getCurrentAgent</p>
	 * <p>Description: </p>
	 *
	 * @return com.pig4cloud.pig.accountmanage.agent.entity.Agent
	 * @date 2019年04月28日
	 * @author 余新引
	 */
	Agent getCurrentAgent();

	/**
	 * 代理商审核
	 * <p>Title: audit</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年05月08日
	 * @author 余新引
	 */
	boolean audit(Agent agent);

	/**
	 * 代理商信息修改审核
	 * <p>Title: updateAudit</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年05月08日
	 * @author 余新引
	 */
	boolean updateAudit(Agent agent);

	/**
	 * 代理商管理简单分页查询
	 *
	 * @param agent 代理商管理
	 * @return
	 */
	IPage<Agent> getAgentPage(Page<Agent> page, Agent agent);

	/**
	 * 根据注册手机号查询代理商数量
	 * <p>Title: getAgentCountByPhone</p>
	 * <p>Description: </p>
	 *
	 * @return int
	 * @date 2019年04月21日
	 * @author 余新引
	 */
	int getAgentCountByPhone(String phone);

	/**
	 * 查询代理链
	 * <p>Title: getAgentChain</p>
	 * <p>Description: </p>
	 *
	 * @return com.pig4cloud.pig.accountmanage.agent.entity.AgentChain
	 * @date 2019年04月18日
	 * @author 余新引
	 */
	AgentChain getAgentChain(Integer agentId);

	/**
	 * 查询向下代理链
	 * <p>Title: getAgentChain</p>
	 * <p>Description: </p>
	 *
	 * @return com.pig4cloud.pig.accountmanage.agent.entity.AgentChain
	 * @date 2019年04月18日
	 * @author 余新引
	 */
	AgentReverseChain getAgentReverseChain(Agent agent);

	/**
	 * 保存代理商信息
	 * <p>Title: updateById</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年04月19日
	 * @author 余新引
	 */
	boolean save(AgentSaveDTO agentDTO);

	/**
	 * 更新代理商信息
	 * <p>Title: updateById</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年04月19日
	 * @author 余新引
	 */
	boolean updateById(AgentUpdateDTO agentDTO);

	/**
	 * 注册代理商
	 * <p>Title: registAgent</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年04月21日
	 * @author 余新引
	 */
	boolean registAgent(AgentRegistDTO agentRegistDTO);

	/**
	 * 推荐代理商
	 * <p>Title: referralAgent</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年04月21日
	 * @author 余新引
	 */
	boolean referralAgent(Agent agent, AgentReferralDTO agentReferralDTO);

	/**
	 * 设置下级抽佣比例
	 * <p>Title: setCommissionRatio</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年05月07日
	 * @author 余新引
	 */
	int setCommissionRatio(Agent agent, BigDecimal ratio);

	/**
	 * 计算代理商已分配的分润比例
	 * <p>Title: getChildrenSumRatio</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年05月07日
	 * @author 余新引
	 */
	BigDecimal getChildrenSumRatio(BigDecimal ratio, boolean isFirst, AgentReverseChain child);

	/**
	 * 重置密码
	 * <p>Title: resetPwd</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年05月14日
	 * @author 余新引
	 */
	boolean resetPwd(Agent agent, AgentResetPwdDTO dto);

}
