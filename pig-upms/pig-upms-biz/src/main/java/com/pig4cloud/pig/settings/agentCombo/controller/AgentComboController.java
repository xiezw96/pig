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
package com.pig4cloud.pig.settings.agentCombo.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.service.AgentService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.pay.weixin.notify.handler.WXPayNotifyHandler;
import com.pig4cloud.pig.settings.agentCombo.dto.SelectComboDTO;
import com.pig4cloud.pig.settings.agentCombo.dto.UnifiedOrderDTO;
import com.pig4cloud.pig.settings.agentCombo.entity.AgentCombo;
import com.pig4cloud.pig.settings.agentCombo.service.AgentComboService;
import com.pig4cloud.pig.settings.comboManage.entity.ComboManage;
import com.pig4cloud.pig.settings.comboManage.service.ComboManageService;
import com.pig4cloud.pig.utils.IpUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

/**
 * 代理商激活套餐
 *
 * @author zhuzubin
 * @date 2019-04-05 11:26:21
 */
@Api(value = "agentcombo", tags = "代理商激活套餐")
@RestController
@AllArgsConstructor
@RequestMapping("/agentcombo")
@Slf4j
public class AgentComboController {

	private final AgentService agentService;
	private final ComboManageService comboManageService;
	private final AgentComboService agentComboService;

	private final WXPayNotifyHandler notifyHandler;

	/**
	 * 简单分页查询
	 *
	 * @param page        分页对象
	 * @param comboManage 代理商激活套餐
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<AgentCombo>> getAgentComboPage(Page<AgentCombo> page, AgentCombo comboManage) {
		return new R<>(agentComboService.getAgentComboPage(page, comboManage));
	}

	@ApiOperation("选择激活套餐")
	@PostMapping("/selectcombo/totalmoney")
	public R<BigDecimal> getComboTotalMoney(@RequestBody SelectComboDTO dto) {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		if (agent.getActiveStatus() != null && agent.getActiveStatus() == 1) {
			return new R(Boolean.FALSE, "您的账户已激活");
		}
		ComboManage comboManage = comboManageService.getComboById(dto.getComboId());
		if (comboManage == null) {
			return new R(Boolean.FALSE, "套餐不存在，请刷新后重试");
		}
		return new R(agentComboService.getComboTotalMoney(comboManage, dto));
	}

	@ApiOperation("选择激活套餐")
	@PostMapping("/selectcombo")
	public R<String> selectCombo(@RequestBody SelectComboDTO dto) {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		if (agent.getActiveStatus() != null && agent.getActiveStatus() == 1) {
			return new R(Boolean.FALSE, "您的账户已激活");
		}
		ComboManage comboManage = comboManageService.getComboById(dto.getComboId());
		if (comboManage == null) {
			return new R(Boolean.FALSE, "套餐不存在，请刷新后重试");
		}
		return new R(agentComboService.saveAgentCombo(agent, comboManage, dto));
	}

	@ApiOperation("支付前统一下单")
	@PostMapping("/unifiedorder")
	public R<Map<String, String>> unifiedOrder(@RequestBody UnifiedOrderDTO dto, HttpServletRequest request) {
		if (dto == null) {
			return new R(Boolean.FALSE, "请求参数为空");
		}
		if (StringUtils.isEmpty(dto.getOpenid())) {
			return new R(Boolean.FALSE, "openid为空，请通过微信公从号入口登录激活");
		}
		if (StringUtils.isEmpty(dto.getTradeNo())) {
			return new R(Boolean.FALSE, "交易流水号为空");
		}
		AgentCombo agentCombo = agentComboService.getOne(Wrappers.<AgentCombo>query().lambda().eq(AgentCombo::getTradeNo, dto.getTradeNo()));
		if (agentCombo == null) {
			return new R(Boolean.FALSE, "交易不存在");
		}
		if (agentCombo.getPayStatus() != null && agentCombo.getPayStatus() == 1) {
			return new R(Boolean.FALSE, "交易已完成支付");
		}
		String ip = IpUtil.getIpAddr(request);
		return new R<>(agentComboService.unifiedOrder(agentCombo, dto.getOpenid(), ip));
	}

	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
	@GetMapping("/{id}")
	public R<AgentCombo> getById(@PathVariable("id") Integer id) {
		return new R(agentComboService.getById(id));
	}


	@ApiOperation(value = "支付通知回调", notes = "支付通知回调")
	@PostMapping("/wxpay/notify")
	public ResponseEntity<String> notify(@RequestBody String xml) {
		return notifyHandler.notify(xml, notifyEntity -> {
			AgentCombo agentCombo = agentComboService.getOne((Wrappers.<AgentCombo>query().lambda().eq(AgentCombo::getTradeNo, notifyEntity.getOut_trade_no())));
			// 订单状态未变更，防止重复通知
			if (agentCombo.getPayStatus() == null || 0 == agentCombo.getPayStatus()) {
				// 验证订单价格，防止假通知
				BigDecimal total = agentCombo.getTotalMoney().multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP);
				if (total.compareTo(notifyEntity.getTotal_fee()) == 0) {
					agentComboService.updateByWXPayNotify(notifyEntity, comboManageService.getComboById(agentCombo.getComboId()), agentService.getById(agentCombo.getAgentId()), agentCombo);
				} else {
					return false;
				}
			}
			return true;
		});
	}
}
