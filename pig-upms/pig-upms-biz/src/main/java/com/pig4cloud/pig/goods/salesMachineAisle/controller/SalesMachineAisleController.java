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
package com.pig4cloud.pig.goods.salesMachineAisle.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.goods.salesMachine.entity.SalesMachine;
import com.pig4cloud.pig.goods.salesMachine.service.SalesMachineService;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisle;
import com.pig4cloud.pig.goods.salesMachineAisle.service.SaleMachineAisleIotService;
import com.pig4cloud.pig.goods.salesMachineAisle.service.SalesMachineAisleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 设备货道管理
 *
 * @author zhuzubin
 * @date 2019-04-05 14:08:11
 */
@Api(value = "salesmachineaisle", tags = "设备货道管理")
@RestController
@AllArgsConstructor
@RequestMapping("/salesmachineaisle")
public class SalesMachineAisleController {

	private final SalesMachineService salesMachineService;
	private final SalesMachineAisleService aisleService;
	private final SaleMachineAisleIotService iotService;

	@ApiOperation(value = "货道开启服务", notes = "货道开启服务")
	@GetMapping("/agent/openone/{aisleId}")
	public R<Boolean> open(@PathVariable("aisleId") Long aisleId) {
		SalesMachineAisle aisle = aisleService.getById(aisleId);
		if (aisle == null) {
			return new R(Boolean.FALSE, "货道不存在");
		}
		SalesMachine salesMachine = salesMachineService.getById(aisle.getMachineId());
		if (salesMachine == null) {
			return new R(Boolean.FALSE, "货柜不存在");
		}
		if (salesMachine.getBelongsUser() == null || !salesMachine.getBelongsUser().equals(SecurityUtils.getUser().getId())) {
			return new R(Boolean.FALSE, "不能开启其他代理商的柜子");
		}
		return new R(iotService.open(salesMachine, aisle));
	}

	@ApiOperation(value = "货道全部开启服务", notes = "货道全部开启服务")
	@GetMapping("/agent/openall/{machineId}")
	public R<Boolean> openAll(@PathVariable("machineId") Integer machineId) {
		SalesMachine salesMachine = salesMachineService.getById(machineId);
		if (salesMachine == null)
			return new R(Boolean.FALSE, "设备不存在，请刷新重试");
		if (!SecurityUtils.getUser().getId().equals(salesMachine.getBelongsUser()))
			return new R(Boolean.FALSE, "您不能打开别人的柜子，请刷新重试");
		return new R(iotService.openAll(salesMachine));
	}

}
