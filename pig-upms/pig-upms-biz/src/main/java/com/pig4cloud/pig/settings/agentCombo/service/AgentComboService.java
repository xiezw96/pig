/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. Neither the name of the pig4cloud.com developer nor
 * the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission. Author:
 * lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.settings.agentCombo.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.pay.weixin.notify.entity.WXPayNotify;
import com.pig4cloud.pig.settings.agentCombo.dto.SelectComboDTO;
import com.pig4cloud.pig.settings.agentCombo.entity.AgentCombo;
import com.pig4cloud.pig.settings.comboManage.entity.ComboManage;

import java.math.BigDecimal;
import java.util.Map;

/**
 * 套餐明细
 *
 * @author zhuzubin
 * @date 2019-04-05 11:27:38
 */
public interface AgentComboService extends IService<AgentCombo> {

	/**
	 * 代理商激活套餐简单分页查询
	 *
	 * @param agentCombo 代理商激活套餐
	 * @return
	 */
	IPage<AgentCombo> getAgentComboPage(Page<AgentCombo> page, AgentCombo agentCombo);

	/**
	 * 代理商选择套餐
	 * <p>Title: saveAgentCombo</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年05月16日
	 * @author 余新引
	 */
	String saveAgentCombo(Agent agent, ComboManage combo, SelectComboDTO dto);

	BigDecimal getComboTotalMoney(ComboManage combo, SelectComboDTO dto);

	/**
	 * 统一下单
	 * <p>Title: unifiedOrder</p>
	 * <p>Description: </p>
	 *
	 * @return com.pig4cloud.pig.pay.weixin.api.entity.UnifiedOrderResult
	 * @date 2019年06月21日
	 * @author 余新引
	 */
	Map<String, String> unifiedOrder(AgentCombo agentCombo, String wxOpenid, String ip);

	/**
	 * 根据微信支付通知修改代理商激活
	 * <p>Title: updateByWXPayNotify</p>
	 * <p>Description: </p>
	 *
	 * @return void
	 * @date 2019年06月13日
	 * @author 余新引
	 */
	void updateByWXPayNotify(WXPayNotify notify, ComboManage combo, Agent agent, AgentCombo agentCombo);
}
