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
package com.pig4cloud.pig.goods.goods.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.goods.goods.entity.Goods;
import com.pig4cloud.pig.goods.goods.service.GoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 商品信息
 *
 * @author zhuzubin
 * @date 2019-04-05 22:36:49
 */
@Api(value = "goods", tags = "商品管理")
@RestController
@AllArgsConstructor
@RequestMapping("/goods")
public class GoodsController {

	private final GoodsService goodsService;

	/**
	 * 简单分页查询
	 *
	 * @param page  分页对象
	 * @param goods 商品信息
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<Goods>> getGoodsPage(Page<Goods> page, Goods goods) {
		return new R<>(goodsService.getGoodsPage(page, goods));
	}

	@ApiModelProperty("更新状态")
	@PutMapping("/updateState/{id}")
	public R updateState(@PathVariable Integer id, @RequestParam Integer state) {
		Goods goods = goodsService.getById(id);
		if (goods == null)
			return new R(Boolean.FALSE, "商品不存在");
		goods.setStatus(state);
		return new R(goodsService.updateById(goods));
	}

	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
	@GetMapping("/{id}")
	public Goods getById(@PathVariable("id") Integer id) {
		return goodsService.getGoodsById(id);
	}

	/**
	 * 新增记录
	 *
	 * @param goods
	 * @return R
	 */
	@ApiOperation(value = "新增记录", notes = "新增记录")
	@SysLog("新增商品信息")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('goods_list')")
	public R save(@RequestBody Goods goods) {
		if (goods == null) {
			return new R(Boolean.FALSE, "请求参数为空");
		}
		if (StringUtils.isEmpty(goods.getName())) {
			return new R(Boolean.FALSE, "商品名称不能为空");
		}
		if (StringUtils.isEmpty(goods.getCode())) {
			return new R(Boolean.FALSE, "商品编号不能为空");
		}
		goods.setCreatorId(SecurityUtils.getUser().getId());
		goods.setCreateDate(LocalDateTime.now());
		goods.setUpdateTime(LocalDateTime.now());
		Goods oldGoods = goodsService.getOne(Wrappers.<Goods>query().lambda().eq(Goods::getCode,goods.getCode()));
		if (oldGoods != null)
			return new R(Boolean.FALSE, "商品编号已经存在");
		return new R(goodsService.saveGoods(goods));
	}

	/**
	 * 修改记录
	 *
	 * @param goods
	 * @return R
	 */
	@ApiOperation(value = "修改记录", notes = "修改记录")
	@SysLog("修改商品信息")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('goods_list')")
	public R update(@RequestBody Goods goods) {
		goods.setModifierId(SecurityUtils.getUser().getId());
		goods.setUpdateTime(LocalDateTime.now());
		Goods oldGoods = goodsService.getById(goods.getId());
		if (!oldGoods.getCode().equals(goods.getCode())) {
			oldGoods = goodsService.getOne(Wrappers.<Goods>query().lambda().eq(Goods::getCode,goods.getCode()));
			if (oldGoods != null)
				return new R(Boolean.FALSE, "商品编号已经存在");
		}
		return new R(goodsService.updateGoodsById(goods));
	}

	/**
	 * 通过id删除一条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除一条记录", notes = "通过id删除一条记录")
	@SysLog("删除商品信息")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('goods_list')")
	public boolean removeById(@PathVariable Integer id) {
		return goodsService.removeGoodsById(id);
	}

}
