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
package com.pig4cloud.pig.settings.developAward.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.settings.developAward.entity.DevelopAward;
import com.pig4cloud.pig.settings.developAward.service.DevelopAwardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 发展奖励
 *
 * @author zhuzubin
 * @date 2019-04-13 17:41:33
 */
@Api(value="developaward", tags="发展奖励金额设置")
@RestController
@AllArgsConstructor
@RequestMapping("/developaward")
public class DevelopAwardController {

	private final DevelopAwardService developAwardService;

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param developAward 发展奖励
	 * @return
	 */
	@ApiOperation(value="分页查询", notes="分页查询")
	@GetMapping("/page")
	public R<IPage<DevelopAward>> getDevelopAwardPage(Page<DevelopAward> page, DevelopAward developAward) {
		return new R<>(developAwardService.getDevelopAwardPage(page, developAward));
	}

	/**
	 * 通过id查询单条记录
	 * @param id
	 * @return R
	 */
	@ApiOperation(value="通过id查询单条记录", notes="通过id查询单条记录")
	@GetMapping("/{id}")
	public R<DevelopAward> getById(@PathVariable("id") Integer id) {
		return new R<>(developAwardService.getById(id));
	}

	/**
	 * 新增记录
	 * @param developAward
	 * @return R
	 */
	@ApiOperation(value="新增信息", notes="新增信息")
	@SysLog("新增发展奖励")
	@PostMapping
	public R save(@RequestBody DevelopAward developAward) {
		developAward.setCreatorId(SecurityUtils.getUser().getId());
		developAward.setCreateDate(LocalDateTime.now());
		return new R<>(developAwardService.save(developAward));
	}

	/**
	 * 修改记录
	 * @param developAward
	 * @return R
	 */
	@ApiOperation(value="修改信息", notes="修改信息")
	@SysLog("修改发展奖励")
	@PutMapping
	public R update(@RequestBody DevelopAward developAward) {
		developAward.setCreatorId(SecurityUtils.getUser().getId());
		developAward.setCreateDate(LocalDateTime.now());
		return new R<>(developAwardService.updateById(developAward));
	}

	/**
	 * 通过id删除一条记录
	 * @param id
	 * @return R
	 */
	@SysLog("删除发展奖励")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Integer id) {
		return new R<>(developAwardService.removeById(id));
	}

}
