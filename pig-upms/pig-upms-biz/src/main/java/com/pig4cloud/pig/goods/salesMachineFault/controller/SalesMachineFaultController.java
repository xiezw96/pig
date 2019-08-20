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
package com.pig4cloud.pig.goods.salesMachineFault.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import com.pig4cloud.pig.accountmanage.agent.service.AgentService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.goods.salesMachineFault.dto.SalesMachineFaultAgentAddDTO;
import com.pig4cloud.pig.goods.salesMachineFault.dto.SalesMachineFaultDealDTO;
import com.pig4cloud.pig.goods.salesMachineFault.entity.SalesMachineFault;
import com.pig4cloud.pig.goods.salesMachineFault.service.SalesMachineFaultService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


/**
 * 设备故障
 *
 * @author zhuzubin
 * @date 2019-04-05 14:08:11
 */
@Api(value = "salesmachinefault", tags = "设备故障管理")
@RestController
@AllArgsConstructor
@RequestMapping("/salesmachinefault")
public class SalesMachineFaultController {

	private final AgentService agentService;
	private final SalesMachineFaultService salesMachineFaultService;

	/**
	 * 简单分页查询
	 *
	 * @param page              分页对象
	 * @param salesMachineFault 设备故障
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<SalesMachineFault>> getSalesMachinePage(Page<SalesMachineFault> page, SalesMachineFault salesMachineFault) {
		return new R<>(salesMachineFaultService.getSalesMachineFaultPage(page, salesMachineFault));
	}

	@ApiOperation("处理故障")
	@PutMapping("/deal")
	public R<Boolean> dealSalesMachineFault(SalesMachineFaultDealDTO dealDTO) {
		SalesMachineFault updateDO = new SalesMachineFault();
		BeanUtils.copyProperties(dealDTO, updateDO);
		return new R(salesMachineFaultService.updateById(updateDO));
	}

	@ApiOperation("代理商新增故障")
	@PostMapping("/agentadd")
	public R<Boolean> saveByAgent(SalesMachineFaultAgentAddDTO addDTO) {
		Agent agent = agentService.getCurrentAgent();
		if (agent == null) {
			return new R(Boolean.FALSE, "您不是代理商");
		}
		SalesMachineFault addDO = new SalesMachineFault();
		BeanUtils.copyProperties(addDTO, addDO);
		addDO.setCreatorId(SecurityUtils.getUser().getId());
		addDO.setAgentId(agent.getAgentId());
		addDO.setCreateTime(LocalDateTime.now());
		return new R(salesMachineFaultService.save(addDO));
	}

	/**
	 * 新增记录
	 *
	 * @param salesMachineFault
	 * @return R
	 */
	@ApiOperation(value = "新增记录", notes = "新增记录")
	@SysLog("新增设备管理")
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('salesMachine_salesmachine_add')")
	public R save(@RequestBody SalesMachineFault salesMachineFault) {
		salesMachineFault.setCreatorId(SecurityUtils.getUser().getId());
		salesMachineFault.setCreateTime(LocalDateTime.now());
		return new R<>(salesMachineFaultService.save(salesMachineFault));
	}

}
