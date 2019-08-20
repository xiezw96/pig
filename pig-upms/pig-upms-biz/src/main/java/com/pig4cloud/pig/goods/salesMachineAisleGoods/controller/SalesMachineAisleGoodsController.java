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
package com.pig4cloud.pig.goods.salesMachineAisleGoods.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.goods.salesMachine.entity.SalesMachine;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisle;
import com.pig4cloud.pig.goods.salesMachineAisle.service.SalesMachineAisleService;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.dto.SalesMachineAisleGoodsAddDTO;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.entity.SalesMachineAisleGoodsDetail;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.service.SalesMachineAisleGoodsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;


/**
 * 设备商品管理
 *
 * @author zhuzubin
 * @date 2019-04-05 14:08:11
 */
@Api(value = "salesmachineaislegoods", tags = "设备商品管理")
@RestController
@AllArgsConstructor
@RequestMapping("/salesmachineaislegoods")
public class SalesMachineAisleGoodsController {
	private final SalesMachineAisleService aisleService;
	private final SalesMachineAisleGoodsService aisleGoodsService;

	/**
	 * 查询代理商的设备商品列表
	 *
	 * @param page                         分页对象
	 * @param salesMachineAisleGoodsDetail 设备商品管理
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<SalesMachineAisleGoodsDetail>> getSalesMachinePage(Page<SalesMachine> page, SalesMachineAisleGoodsDetail salesMachineAisleGoodsDetail) {
		salesMachineAisleGoodsDetail.setBelongsUser(SecurityUtils.getUser().getId());
		return new R<>(aisleGoodsService.getSalesMachineAisleGoodsDetailPage(page, salesMachineAisleGoodsDetail));
	}

	@ApiOperation(value = "更换补货提交服务", notes = "更换提交服务")
	@PostMapping
	public R save(@RequestBody SalesMachineAisleGoodsAddDTO dto) {
		SalesMachineAisle aisle = aisleService.getById(dto.getId());
		if (aisle == null) {
			return new R(Boolean.FALSE, "货道不存在，请刷新重试");
		}
		if (dto.getGoods() == null || dto.getGoods().isEmpty())
			return new R(Boolean.FALSE, "请添加商品");
		return new R(aisleGoodsService.save(dto.getGoods(), aisle));
	}

	@ApiOperation(value = "补货提交服务", notes = "补货提交服务")
	@PutMapping
	public R update(@RequestBody SalesMachineAisleGoodsAddDTO dto) {
		SalesMachineAisle aisle = aisleService.getById(dto.getId());
		if (aisle == null) {
			return new R(Boolean.FALSE, "货道不存在，请刷新重试");
		}
		if (dto.getNum() <= 0)
			return new R(Boolean.FALSE, "请填写补货数量");
		return new R(aisleGoodsService.update(aisle, dto.getNum()));
	}

	@ApiOperation(value = "通过id删除一条记录", notes = "通过id删除一条记录")
	@DeleteMapping("/{id}")
	public boolean removeById(@PathVariable Integer id) {
		return aisleGoodsService.removeById(id);
	}


}
