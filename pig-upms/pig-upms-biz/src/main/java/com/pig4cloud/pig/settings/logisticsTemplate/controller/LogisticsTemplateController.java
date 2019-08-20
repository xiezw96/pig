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
package com.pig4cloud.pig.settings.logisticsTemplate.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.settings.logisticsTemplate.entity.LogisticsTemplate;
import com.pig4cloud.pig.settings.logisticsTemplate.service.LogisticsTemplateService;
import com.pig4cloud.pig.settings.logisticsTemplate.utils.LogisticsPriceUtils;
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
 * 物流模板
 *
 * @author zhuzubin
 * @date 2019-04-05 11:29:49
 */
@Api(value = "logisticstemplate", tags = "物流模板")
@RestController
@AllArgsConstructor
@RequestMapping("/logisticstemplate")
public class LogisticsTemplateController {

	private final LogisticsTemplateService logisticsTemplateService;

	@ApiOperation(value = "查询物流模板", notes = "查询物流模板，未设置返回null")
	@GetMapping
	public R<LogisticsTemplate> get() {
		return new R<>(logisticsTemplateService.list().stream().map(LogisticsPriceUtils::split).findFirst().orElse(null));
	}

	@ApiOperation(value = "保存/修改物流模板", notes = "保存/修改物流模板")
	@PostMapping
	public R save(@RequestBody LogisticsTemplate tpl) {
		tpl = LogisticsPriceUtils.join(tpl);
		if (tpl.getStartingWeight() == null)
			return new R(Boolean.FALSE, "首重未设置");
		if (tpl.getStartingPrice() == null)
			return new R(Boolean.FALSE, "起步价未设置");
		if (tpl.getIncrement() == null)
			return new R(Boolean.FALSE, "续重未设置");
		LogisticsTemplate old = logisticsTemplateService.list().stream().findFirst().orElse(null);
		if (old == null) {
			tpl.setCreatorId(SecurityUtils.getUser().getId());
			tpl.setCreateDate(LocalDateTime.now());
			return new R<>(logisticsTemplateService.save(tpl));
		} else {
			old.setStartingPrice(tpl.getStartingPrice());
			old.setIncrement(tpl.getIncrement());
			old.setFarawayProvinces(tpl.getFarawayProvinces());
			old.setFarawayStartingPrice(tpl.getFarawayStartingPrice());
			old.setFarawayIncrement(tpl.getFarawayIncrement());
			old.setModifierId(SecurityUtils.getUser().getId());
			old.setUpdateTime(LocalDateTime.now());
			return new R<>(logisticsTemplateService.updateById(old));
		}
	}

}
