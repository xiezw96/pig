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
package com.pig4cloud.pig.settings.commission.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.settings.commission.entity.Commission;
import com.pig4cloud.pig.settings.commission.service.CommissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 通道费设置
 *
 * @author zhuzubin
 * @date 2019-04-05 11:29:49
 */
@Api(value = "commission", tags = "通道费设置")
@RestController
@AllArgsConstructor
@RequestMapping("/commission")
public class CommissionController {

	private final CommissionService commissionService;

	@ApiOperation(value = "查询通道费设置", notes = "查询通道费设置，未设置返回null")
	@GetMapping
	public R<Commission> get() {
		return new R<>(commissionService.list().stream().findFirst().orElse(null));
	}

	@ApiOperation(value = "保存/修改通道费设置", notes = "保存/修改通道费设置")
	@PostMapping
	public R save(@RequestBody Commission entity) {
		if (entity.getCommission() == null)
			return new R(Boolean.FALSE, "通道费未设置");
		Commission old = commissionService.list().stream().findFirst().orElse(null);
		if (old == null) {
			entity.setCreatorId(SecurityUtils.getUser().getId());
			entity.setCreateDate(LocalDateTime.now());
			return new R<>(commissionService.save(entity));
		} else {
			old.setCommission(entity.getCommission());
			old.setModifierId(SecurityUtils.getUser().getId());
			old.setUpdateTime(LocalDateTime.now());
			return new R<>(commissionService.updateById(old));
		}
	}

}
