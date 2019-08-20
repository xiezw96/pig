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
package com.pig4cloud.pig.order.agentSettlement.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.order.agentSettlement.dto.AgentSettlementSaveDTO;
import com.pig4cloud.pig.order.agentSettlement.entity.AgentSettlement;

/**
 * 结算单
 *
 * @author zhuzubin
 * @date 2019-04-05 23:20:28
 */
public interface AgentSettlementService extends IService<AgentSettlement> {

	/**
	 * 结算单简单分页查询
	 *
	 * @param agentSettlement 结算单
	 * @return
	 */
	IPage<AgentSettlement> getAgentSettlementPage(Page<AgentSettlement> page, AgentSettlement agentSettlement);

	/**
	 * 结算申请单
	 * <p>Title: save</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年04月22日
	 * @author 余新引
	 */
	boolean save(Agent agent, AgentSettlementSaveDTO saveDTO);

	/**
	 * 结算申请单审核
	 * <p>Title: audit</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年05月12日
	 * @author 余新引
	 */
	boolean audit(AgentSettlement sett);
}
