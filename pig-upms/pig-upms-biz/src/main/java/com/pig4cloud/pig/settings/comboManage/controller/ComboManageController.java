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
package com.pig4cloud.pig.settings.comboManage.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.settings.comboManage.entity.ComboManage;
import com.pig4cloud.pig.settings.comboManage.service.ComboManageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
 * 套餐管理
 *
 * @author zhuzubin
 * @date 2019-04-05 11:26:21
 */
@Api(value = "combomanage", tags = "套餐管理设置")
@RestController
@AllArgsConstructor
@RequestMapping("/combomanage")
public class ComboManageController {

	private final ComboManageService comboManageService;

	/**
	 * 简单分页查询
	 *
	 * @param page        分页对象
	 * @param comboManage 套餐管理
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<ComboManage>> getComboManagePage(Page<ComboManage> page, ComboManage comboManage) {
		return new R<>(comboManageService.getComboManagePage(page, comboManage));
	}

	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
	@GetMapping("/{id}")
	public R<ComboManage> getById(@PathVariable("id") Integer id) {
		return new R<>(comboManageService.getComboById(id));
	}

	/**
	 * 新增记录
	 *
	 * @param combo
	 * @return R
	 */
	@ApiOperation(value = "新增信息", notes = "新增信息")
	@SysLog("新增套餐管理")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('combo_list')")
	public R save(@RequestBody ComboManage combo) {
		combo.setCreatorId(SecurityUtils.getUser().getId());
		combo.setCreateDate(LocalDateTime.now());
		return new R<>(comboManageService.saveCombo(combo));
	}

	/**
	 * 修改记录
	 *
	 * @param combo
	 * @return R
	 */
	@ApiOperation(value = "修改信息", notes = "修改信息")
	@SysLog("修改套餐管理")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('combo_list')")
	public R update(@RequestBody ComboManage combo) {
		combo.setCreatorId(SecurityUtils.getUser().getId());
		combo.setCreateDate(LocalDateTime.now());
		return new R<>(comboManageService.updateCombo(combo));
	}

	/**
	 * 通过id删除一条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "删除套餐信息", notes = "删除套餐信息")
	@SysLog("删除套餐管理")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('combo_list')")
	public R removeById(@PathVariable Integer id) {
		return new R<>(comboManageService.removeComboById(id));
	}

}
