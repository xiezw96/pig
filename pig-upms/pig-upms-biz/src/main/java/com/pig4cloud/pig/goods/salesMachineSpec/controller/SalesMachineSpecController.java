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
package com.pig4cloud.pig.goods.salesMachineSpec.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.goods.salesMachineSpec.entity.SalesMachineSpec;
import com.pig4cloud.pig.goods.salesMachineSpec.service.SalesMachineSpecService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


/**
 * 设备型号管理
 *
 * @author zhuzubin
 * @date 2019-04-05 14:08:11
 */
@Api(value = "salesmachinespec", tags = "设备型号管理")
@RestController
@AllArgsConstructor
@RequestMapping("/salesmachinespec")
public class SalesMachineSpecController {

	private final SalesMachineSpecService salesMachineSpecService;

	/**
	 * 简单分页查询
	 *
	 * @param page             分页对象
	 * @param salesMachineSpec 设备型号
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<SalesMachineSpec>> getSalesMachineSpecSpecPage(Page<SalesMachineSpec> page, SalesMachineSpec salesMachineSpec) {
		return new R<>(salesMachineSpecService.getSalesMachineSpecPage(page, salesMachineSpec));
	}


	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
	@GetMapping("/{id}")
	public R<SalesMachineSpec> getById(@PathVariable("id") Integer id) {
		return new R<>(salesMachineSpecService.getById(id));
	}

	/**
	 * 新增记录
	 *
	 * @param salesMachine
	 * @return R
	 */
	@ApiOperation(value = "新增记录", notes = "新增记录")
	@SysLog("新增设备型号")
	@PostMapping
	public R save(@RequestBody SalesMachineSpec salesMachine) {
		salesMachine.setCreateDate(LocalDateTime.now());
		salesMachine.setCreatorId(SecurityUtils.getUser().getId());
		return new R<>(salesMachineSpecService.save(salesMachine));
	}

	/**
	 * 修改记录
	 *
	 * @param salesMachine
	 * @return R
	 */
	@ApiOperation(value = "修改记录", notes = "修改记录")
	@SysLog("修改设备型号")
	@PutMapping
	public R update(@RequestBody SalesMachineSpec salesMachine) {
		salesMachine.setUpdateTime(LocalDateTime.now());
		salesMachine.setModifierId(SecurityUtils.getUser().getId());
		return new R<>(salesMachineSpecService.updateById(salesMachine));
	}

	/**
	 * 通过id删除一条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除一条记录", notes = "通过id删除一条记录")
	@SysLog("删除设备型号")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Integer id) {
		return new R<>(salesMachineSpecService.removeById(id));
	}

}
