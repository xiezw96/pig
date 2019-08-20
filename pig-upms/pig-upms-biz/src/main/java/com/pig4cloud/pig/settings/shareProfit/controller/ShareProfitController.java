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
package com.pig4cloud.pig.settings.shareProfit.controller;

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
import com.pig4cloud.pig.settings.shareProfit.entity.ShareProfit;
import com.pig4cloud.pig.settings.shareProfit.service.ShareProfitService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 商城分润
 *
 * @author zhuzubin
 * @date 2019-04-05 11:29:49
 */
@Api(value = "shareprofit", tags = "商城分润设置")
@RestController
@AllArgsConstructor
@RequestMapping("/shareprofit")
public class ShareProfitController {

	private final ShareProfitService shareProfitService;

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param shareProfit 商城分润
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<ShareProfit>> getShareProfitPage(Page<ShareProfit> page, ShareProfit shareProfit) {
		return new R<>(shareProfitService.getShareProfitPage(page, shareProfit));
	}

	/**
	 * 通过id查询单条记录
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
	@GetMapping("/{id}")
	public R<ShareProfit> getById(@PathVariable("id") Integer id) {
		return new R<>(shareProfitService.getById(id));
	}

	/**
	 * 新增记录
	 * @param shareProfit
	 * @return R
	 */
	@ApiOperation(value = "新增信息", notes = "新增信息")
	@SysLog("新增商城分润")
	@PostMapping
	public R save(@RequestBody ShareProfit shareProfit) {
		shareProfit.setCreatorId(SecurityUtils.getUser().getId());
		shareProfit.setCreateDate(LocalDateTime.now());
		return new R<>(shareProfitService.save(shareProfit));
	}

	/**
	 * 修改记录
	 * @param shareProfit
	 * @return R
	 */
	@ApiOperation(value = "修改信息", notes = "修改信息")
	@SysLog("修改商城分润")
	@PutMapping
	public R update(@RequestBody ShareProfit shareProfit) {
		shareProfit.setCreatorId(SecurityUtils.getUser().getId());
		shareProfit.setCreateDate(LocalDateTime.now());
		return new R<>(shareProfitService.updateById(shareProfit));
	}

	/**
	 * 通过id删除一条记录
	 * @param id
	 * @return R
	 */
	@SysLog("删除商城分润")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Integer id) {
		return new R<>(shareProfitService.removeById(id));
	}

}
