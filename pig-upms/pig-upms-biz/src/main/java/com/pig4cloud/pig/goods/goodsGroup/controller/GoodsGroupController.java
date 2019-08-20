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
package com.pig4cloud.pig.goods.goodsGroup.controller;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
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
import com.pig4cloud.pig.goods.goodsGroup.entity.GoodsGroup;
import com.pig4cloud.pig.goods.goodsGroup.service.GoodsGroupService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 商品分组
 *
 * @author zhuzubin
 * @date 2019-04-05 14:10:14
 */
@Api(value = "goodsgroup", tags = "商品分组管理")
@RestController
@AllArgsConstructor
@RequestMapping("/goodsgroup")
public class GoodsGroupController {

	private final GoodsGroupService goodsGroupService;

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param goodsGroup 商品分组
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<GoodsGroup>> getGoodsGroupPage(Page<GoodsGroup> page, GoodsGroup goodsGroup) {
		return new R<>(goodsGroupService.getGoodsGroupPage(page, goodsGroup));
	}
	
	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param goodsGroup 商品分组
	 * @return
	 */
	@ApiOperation(value = "查询全部分组", notes = "查询全部分组")
	@GetMapping("/list")
	public List<GoodsGroup> getGoodsGroupList(Page<GoodsGroup> page, GoodsGroup goodsGroup) {
		return goodsGroupService.getGoodsGroupList(goodsGroup);
	}
	
	/**
	 * 通过id查询单条记录
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
	@GetMapping("/{id}")
	public R<GoodsGroup> getById(@PathVariable("id") Integer id) {
		return new R<>(goodsGroupService.getById(id));
	}

	/**
	 * 新增记录
	 * @param goodsGroup
	 * @return R
	 */
	@ApiOperation(value = "新增记录", notes = "新增记录")
	@SysLog("新增商品分组")
	@PostMapping
	// @PreAuthorize("@pms.hasPermission('goodsGroup_goodsgroup_add')")
	public R save(@RequestBody GoodsGroup goodsGroup) {
		goodsGroup.setCreateDate(LocalDateTime.now());
		goodsGroup.setCreatorId(SecurityUtils.getUser().getId());
		return new R<>(goodsGroupService.save(goodsGroup));
	}

	/**
	 * 修改记录
	 * @param goodsGroup
	 * @return R
	 */
	@ApiOperation(value = "修改记录", notes = "修改记录")
	@SysLog("修改商品分组")
	@PutMapping
	// @PreAuthorize("@pms.hasPermission('goodsGroup_goodsgroup_edit')")
	public R update(@RequestBody GoodsGroup goodsGroup) {
		return new R<>(goodsGroupService.updateById(goodsGroup));
	}

	/**
	 * 通过id删除一条记录
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除一条记录", notes = "通过id删除一条记录")
	@SysLog("删除商品分组")
	@DeleteMapping("/{id}")
	// @PreAuthorize("@pms.hasPermission('goodsGroup_goodsgroup_del')")
	public boolean removeById(@PathVariable Integer id) {
		return goodsGroupService.removeGoodsGroupById(id);
	}

}
