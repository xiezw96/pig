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
package com.pig4cloud.pig.goods.salesMachine.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.goods.salesMachine.dto.AgentSalesMachineDTO;
import com.pig4cloud.pig.goods.salesMachine.entity.SalesMachine;
import com.pig4cloud.pig.goods.salesMachine.service.SalesMachineService;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisle;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisleDetail;
import com.pig4cloud.pig.goods.salesMachineAisle.service.SalesMachineAisleService;
import com.pig4cloud.pig.goods.salesMachineSpec.entity.SalesMachineSpec;
import com.pig4cloud.pig.goods.salesMachineSpec.service.SalesMachineSpecService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 设备管理
 *
 * @author zhuzubin
 * @date 2019-04-05 14:08:11
 */
@Api(value = "salesmachine", tags = "设备管理")
@RestController
@AllArgsConstructor
@RequestMapping("/salesmachine")
public class SalesMachineController {

	private final SalesMachineService salesMachineService;
	private final SalesMachineAisleService aisleService;
	private final SalesMachineSpecService specService;

	/**
	 * 简单分页查询
	 *
	 * @param page         分页对象
	 * @param salesMachine 设备管理
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<SalesMachine>> getSalesMachinePage(Page<SalesMachine> page, SalesMachine salesMachine) {
		return new R<>(salesMachineService.getSalesMachinePage(page, salesMachine));
	}


	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
	@GetMapping("/{id}")
	public R<AgentSalesMachineDTO> getById(@PathVariable("id") Integer id) {
		SalesMachine salesMachine = salesMachineService.getById(id);
		AgentSalesMachineDTO dto = new AgentSalesMachineDTO();
		BeanUtils.copyProperties(salesMachine, dto);
		List<SalesMachineAisle> aisles = aisleService.list(Wrappers.<SalesMachineAisle>query().lambda().eq(SalesMachineAisle::getMachineId, salesMachine.getId()));
		dto.setAisleDetails(aisles.stream().map(aisle -> {
			SalesMachineAisleDetail detail = new SalesMachineAisleDetail();
			BeanUtils.copyProperties(aisle, detail);
			return detail;
		}).collect(Collectors.toList()));
		return new R<>(dto);
	}

	/**
	 * 新增记录
	 *
	 * @param salesMachine
	 * @return R
	 */
	@ApiOperation(value = "新增记录", notes = "新增记录")
	@SysLog("新增设备管理")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('machine_list')")
	public R save(@RequestBody SalesMachine salesMachine) {
		if (salesMachine.getSpecId() == null) {
			return new R(Boolean.FALSE, "设备类型为空");
		}
		SalesMachineSpec spec = specService.getById(salesMachine.getSpecId());
		if (spec == null) {
			return new R(Boolean.FALSE, "设备类型不存在，请刷新后重试");
		}
		if (spec.getAisleCount() == null || spec.getAisleCount() <= 0) {
			return new R(Boolean.FALSE, "设备类型货道数量不合法");
		}
		int count = salesMachine.selectCount(Wrappers.<SalesMachine>query().lambda().eq(SalesMachine::getCode, salesMachine.getCode()));
		if (count > 0)
			return new R(Boolean.FALSE, "设备编号已存在");
		return new R<>(salesMachineService.save(spec, salesMachine));
	}

	/**
	 * 修改记录
	 *
	 * @param salesMachine
	 * @return R
	 */
	@ApiOperation(value = "修改记录", notes = "修改记录")
	@SysLog("修改设备管理")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('machine_list')")
	public R update(@RequestBody SalesMachine salesMachine) {
		return new R<>(salesMachineService.updateById(salesMachine));
	}

	/**
	 * 通过id删除一条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除一条记录", notes = "通过id删除一条记录")
	@SysLog("删除设备管理")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('machine_list')")
	public R removeById(@PathVariable Integer id) {
		SalesMachine machine = salesMachineService.getById(id);
		if (machine.getStatus() != 0 || machine.getStatus() != 6) {
			return new R(Boolean.FALSE, "设备已激活，不能删除");
		}
		return new R<>(salesMachineService.removeById(id));
	}

	@ApiOperation(value = "查询代理商柜子", notes = "查询代理商柜子，包含了下级代理商的柜子")
	@GetMapping("/agent/page")
	public R<IPage<SalesMachine>> getAgentSalesMachineListForPage(Page<SalesMachine> page, SalesMachine salesMachine) {
		salesMachine.setBelongsUser(SecurityUtils.getUser().getId());
		salesMachine.setReferrerIds("," + salesMachine.getBelongsUser() + ",");
		return new R(salesMachineService.getSalesMachinePage(page, salesMachine));
	}

	@ApiOperation(value = "查询代理商柜子下拉数据", notes = "查询代理商柜子，包含明细，不包含下级")
	@GetMapping("/agent/select")
	public R getAgentSalesMachineList() {
		List<SalesMachine> salesMachineList = salesMachineService.list(Wrappers.<SalesMachine>query().lambda().eq(SalesMachine::getBelongsUser, SecurityUtils.getUser().getId()));
		if (salesMachineList == null || salesMachineList.size() == 0) {
			return new R(Boolean.FALSE, "您没有柜子");
		}
		List<SalesMachineAisleDetail> aisleDeails = aisleService.getSalesMachineAisleDetailByMachineId(salesMachineList.stream().map(SalesMachine::getId).collect(Collectors.toList()));
		return new R(salesMachineList.stream().map(salesMachine -> {
			AgentSalesMachineDTO dto = new AgentSalesMachineDTO();
			BeanUtils.copyProperties(salesMachine, dto);
			dto.setAisleDetails(aisleDeails.stream().filter(aisle -> salesMachine.getId().equals(aisle.getMachineId())).collect(Collectors.toList()));
			return dto;
		}).collect(Collectors.toList()));
	}

	@ApiOperation(value = "查询代理商柜子明细", notes = "查询代理商柜子明细")
	@GetMapping("/agent/info")
	public R<AgentSalesMachineDTO> getAgentSalesMachineInfo(Long id) {
		SalesMachine salesMachine = salesMachineService.getById(id);
		if (salesMachine == null) {
			return new R(Boolean.FALSE, "您没有柜子");
		}
		if (salesMachine.getBelongsUser() == null || (!salesMachine.getBelongsUser().equals(SecurityUtils.getUser().getId()))) {
			return new R(Boolean.FALSE, "不允许查看其他代理商的柜子明细");
		}
		AgentSalesMachineDTO dto = new AgentSalesMachineDTO();
		BeanUtils.copyProperties(salesMachine, dto);
		dto.setAisleDetails(aisleService.getSalesMachineAisleDetailByMachineId(salesMachine.getId()));
		return new R(dto);
	}

}
