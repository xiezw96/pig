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
package com.pig4cloud.pig.goods.salesMachineAisleGoods.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisle;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.entity.SalesMachineAisleGoods;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.entity.SalesMachineAisleGoodsDetail;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.mapper.SalesMachineAisleGoodsMapper;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.service.SalesMachineAisleGoodsService;
import com.pig4cloud.pig.order.order.dto.GoodsDetailDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * 销售机器货道商品
 *
 * @author zhuzubin
 * @date 2019-04-05 21:25:59
 */
@Service("salesMachineAisleGoodsService")
@AllArgsConstructor
public class SalesMachineAisleGoodsServiceImpl extends ServiceImpl<SalesMachineAisleGoodsMapper, SalesMachineAisleGoods> implements SalesMachineAisleGoodsService {

	/**
	 * 销售机器货道商品简单分页查询
	 *
	 * @param salesMachineAisleGoods 销售机器货道商品
	 * @return
	 */
	@Override
	public IPage<SalesMachineAisleGoods> getSalesMachineAisleGoodsPage(Page<SalesMachineAisleGoods> page, SalesMachineAisleGoods salesMachineAisleGoods) {
		return baseMapper.getSalesMachineAisleGoodsPage(page, salesMachineAisleGoods);
	}

	@Override
	public List<SalesMachineAisleGoods> getSalesMachineAisleGoodsByMachineId(Integer machineId) {
		if (machineId == null) return null;

		return baseMapper.getSalesMachineAisleGoodsByMachineId(machineId);
	}

	@Override
	public IPage<SalesMachineAisleGoodsDetail> getSalesMachineAisleGoodsDetailPage(Page page, SalesMachineAisleGoodsDetail salesMachineAisleGoodsDetail) {
		return baseMapper.getSalesMachineAisleGoodsDetailPage(page, salesMachineAisleGoodsDetail);
	}

	@Override
	public boolean save(List<GoodsDetailDTO> goodsList, SalesMachineAisle aisle) {
		if (aisle == null) return false;
		if (goodsList == null || goodsList.isEmpty()) return false;
		List<SalesMachineAisleGoods> oldGoodsList = list(Wrappers.<SalesMachineAisleGoods>query().lambda().eq(SalesMachineAisleGoods::getAisleId, aisle.getId()));
		List<SalesMachineAisleGoods> newGoodsList = goodsList.stream().map(goods -> {
				return Optional.ofNullable(oldGoodsList.stream()
					.filter(old -> {
						boolean result = goods.getGoodsId().equals(old.getGoodsId()) && goods.getSpePriceKey().equals(old.getSpePriceKey());
						if (result) {
							old.setNum(old.getNum() + goods.getNum());
						}
						return result;
					})
					.findFirst()
					.orElse(null))
					.orElseGet(() -> {
						SalesMachineAisleGoods newGoods = new SalesMachineAisleGoods();
						newGoods.setGoodsId(goods.getGoodsId());
						newGoods.setAisleId(aisle.getId());
						newGoods.setMachineId(aisle.getMachineId());
						newGoods.setSpePriceId(goods.getGoodsSpeId());
						newGoods.setSpePriceKey(goods.getSpePriceKey());
						newGoods.setNum(goods.getNum());
						return newGoods;
					});
			}
		).collect(Collectors.toList());
		boolean result = true;
		// 更新
		List<SalesMachineAisleGoods> updateList = newGoodsList.stream().filter(g -> Optional.ofNullable(g.getId()).isPresent()).collect(Collectors.toList());
		if (!updateList.isEmpty())
			result = updateBatchById(updateList);
		if (result) {
			// 新增
			List<SalesMachineAisleGoods> insertList = newGoodsList.stream().filter(g -> !Optional.ofNullable(g.getId()).isPresent()).collect(Collectors.toList());
			if (!insertList.isEmpty()) {
				result = saveBatch(insertList);
				if (!result)
					throw new RuntimeException("保存货道商品失败");
			}
		}
		return result;
	}

	@Override
	public boolean update(SalesMachineAisle aisle, Integer num) {
		if (aisle == null) return false;
		List<SalesMachineAisleGoods> oldGoodsList = list(Wrappers.<SalesMachineAisleGoods>query().lambda().eq(SalesMachineAisleGoods::getAisleId, aisle.getId()));
		if (oldGoodsList == null || oldGoodsList.size() == 0)
			throw new RuntimeException("货道无商品信息");

		List<SalesMachineAisleGoods> newGoodsList = oldGoodsList.stream().map(goods -> {
				SalesMachineAisleGoods newGoods = new SalesMachineAisleGoods();
				newGoods.setId(goods.getId());
				newGoods.setGoodsId(goods.getGoodsId());
				newGoods.setAisleId(aisle.getId());
				newGoods.setMachineId(aisle.getMachineId());
				newGoods.setSpePriceId(goods.getSpePriceId());
				newGoods.setSpePriceKey(goods.getSpePriceKey());
				newGoods.setNum(num);
				return newGoods;
			}
		).collect(Collectors.toList());
		// 更新
		return updateBatchById(newGoodsList);
	}
}
