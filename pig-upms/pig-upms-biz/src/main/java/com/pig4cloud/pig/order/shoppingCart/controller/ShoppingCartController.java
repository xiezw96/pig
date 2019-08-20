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
package com.pig4cloud.pig.order.shoppingCart.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.goods.goods.entity.Goods;
import com.pig4cloud.pig.goods.goods.service.GoodsService;
import com.pig4cloud.pig.goods.goodsSpePrice.entity.GoodsSpePrice;
import com.pig4cloud.pig.goods.goodsSpePrice.service.GoodsSpePriceService;
import com.pig4cloud.pig.order.shoppingCart.entity.ShoppingCart;
import com.pig4cloud.pig.order.shoppingCart.service.ShoppingCartService;
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
import java.util.List;
import java.util.stream.Collectors;


/**
 * 购物车
 *
 * @author zhuzubin
 * @date 2019-04-05 23:18:23
 */
@Api(value = "settlementdetail", tags = "购物车管理")
@RestController
@AllArgsConstructor
@RequestMapping("/shoppingcart")
public class ShoppingCartController {

	private final ShoppingCartService shoppingCartService;

	private final GoodsService goodsService;

	private final GoodsSpePriceService goodsSpePriceService;

	/**
	 * 简单分页查询
	 *
	 * @param page         分页对象
	 * @param shoppingCart 购物车
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<ShoppingCart>> getShoppingCartPage(Page<ShoppingCart> page, ShoppingCart shoppingCart) {
		shoppingCart.setUserId(SecurityUtils.getUser().getId());
		return new R<>(shoppingCartService.getShoppingCartPage(page, shoppingCart));
	}


	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@GetMapping("/{id}")
	public R<ShoppingCart> getById(@PathVariable("id") Integer id) {
		return new R<>(shoppingCartService.getById(id));
	}

	/**
	 * 新增记录
	 *
	 * @param shoppingCart
	 * @return R
	 */
	@ApiOperation(value = "新增购物车", notes = "新增购物车")
	@SysLog("新增购物车")
	@PostMapping
	public R save(@RequestBody ShoppingCart shoppingCart) {
		shoppingCart.setUserId(SecurityUtils.getUser().getId());
		return new R<>(shoppingCartService.save(shoppingCart));
	}

	@ApiOperation(value = "批量新增购物车", notes = "批量新增购物车")
	@SysLog("新增购物车")
	@PostMapping("/batchSave")
	public R batchSave(@RequestBody List<ShoppingCart> shoppingCartList) {
		List<ShoppingCart> saveList = shoppingCartList.stream().map(item -> {
			item.setUserId(SecurityUtils.getUser().getId());
			GoodsSpePrice goodsSpePrice = goodsSpePriceService.getById(item.getGoodsSpeId());
			item.setGoodsSpe1(goodsSpePrice.getSpeId1());
			item.setGoodsSpe2(goodsSpePrice.getSpeId2());
			item.setSalePrice(goodsSpePrice.getSalePrice());
			item.setTradePrice(goodsSpePrice.getTradePrice());
			item.setIsGenOrder(0);
			Goods goods = goodsService.getById(item.getGoodsId());
			item.setGoodsName(goods.getName());
			item.setGoodsAttId(goods.getAttId());
			item.setCreatorTime(LocalDateTime.now());
			return item;
		}).collect(Collectors.toList());
		return new R<>(shoppingCartService.saveBatch(saveList));
	}

	/**
	 * 修改记录
	 *
	 * @param shoppingCart
	 * @return R
	 */
	@SysLog("修改购物车")
	@PutMapping
	@ApiOperation(value = "修改购物车", notes = "修改购物车")
	public R update(@RequestBody ShoppingCart shoppingCart) {
		return new R<>(shoppingCartService.updateById(shoppingCart));
	}

	/**
	 * 通过id删除一条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "删除购物车", notes = "删除购物车")
	@SysLog("删除购物车")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Integer id) {
		return new R<>(shoppingCartService.removeById(id));
	}

}
