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
package com.pig4cloud.pig.settings.machineTemplate.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.goods.salesMachine.entity.SalesMachine;
import com.pig4cloud.pig.goods.salesMachine.service.SalesMachineService;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisle;
import com.pig4cloud.pig.goods.salesMachineAisle.service.SalesMachineAisleService;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.entity.SalesMachineAisleGoods;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.service.SalesMachineAisleGoodsService;
import com.pig4cloud.pig.goods.salesMachineSpec.entity.SalesMachineSpec;
import com.pig4cloud.pig.goods.salesMachineSpec.service.SalesMachineSpecService;
import com.pig4cloud.pig.settings.machineTemplate.entity.MachineTemplate;
import com.pig4cloud.pig.settings.machineTemplate.mapper.MachineTemplateMapper;
import com.pig4cloud.pig.settings.machineTemplate.service.MachineTemplateService;
import com.pig4cloud.pig.settings.machineTemplateDetail.entity.MachineTemplateDetail;
import com.pig4cloud.pig.settings.machineTemplateDetail.service.MachineTemplateDetailService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 补货模板
 *
 * @author zhuzubin
 * @date 2019-07-08 15:38:50
 */
@Service("machineTemplateService")
@AllArgsConstructor
public class MachineTemplateServiceImpl extends ServiceImpl<MachineTemplateMapper, MachineTemplate> implements MachineTemplateService {

	private final SalesMachineSpecService salesMachineSpecService;

	private final SalesMachineService salesMachineService;

	private final SalesMachineAisleService salesMachineAisleService;

	private final SalesMachineAisleGoodsService salesMachineAisleGoodsService;

	private final MachineTemplateDetailService machineTemplateDetailService;
	/**
	 * 补货模板简单分页查询
	 *
	 * @param machineTemplate 补货模板
	 * @return
	 */
	@Override
	public IPage<MachineTemplate> getMachineTemplatePage(Page<MachineTemplate> page, MachineTemplate machineTemplate) {
		return baseMapper.getMachineTemplatePage(page, machineTemplate);
	}

	@Override
	public boolean save(MachineTemplate machineTemplate) {
		// 获取设备
		SalesMachine salesMachine = salesMachineService.getById(machineTemplate.getMachineId());
		if (salesMachine == null)
			throw new RuntimeException("未找到设备信息,保存模板失败");
		machineTemplate.setMachineType(salesMachine.getSpecId());
		boolean result = super.save(machineTemplate);
		if (result) {
			SalesMachineSpec salesMachineSpec = salesMachineSpecService.getById(salesMachine.getSpecId());
			// 获取设备货道信息
			List<SalesMachineAisleGoods> aisleGoodsList = salesMachineAisleGoodsService.getSalesMachineAisleGoodsByMachineId(machineTemplate.getMachineId());
			if (aisleGoodsList == null || aisleGoodsList.isEmpty())
				throw new RuntimeException("该设备无商品信息,保存模板失败");
			if (aisleGoodsList.size() < salesMachineSpec.getAisleCount())
				throw new RuntimeException("该设备还有货道未上货,保存模板失败");
			List<MachineTemplateDetail> details = aisleGoodsList.stream().map(item -> {
				MachineTemplateDetail detail = new MachineTemplateDetail();
				detail.setAisleCode(item.getAisleCode());
				detail.setGoodsId(item.getGoodsId());
				detail.setNum(item.getNum());
				detail.setSpePriceId(item.getSpePriceId());
				detail.setSpecPriceKey(item.getSpePriceKey());
				detail.setTemplateId(machineTemplate.getId());
				return detail;
			}).collect(Collectors.toList());
			result = machineTemplateDetailService.saveBatch(details);
		}
		return result;
	}


	@Override
	public boolean setMachineTemplate(MachineTemplate machineTemplate){
		MachineTemplate oldTemplate =  super.getById(machineTemplate.getId());
		if (oldTemplate == null)
			throw new RuntimeException("未找到补货模板信息");
		SalesMachine salesMachine = salesMachineService.getById(machineTemplate.getMachineId());
		if (salesMachine == null)
			throw new RuntimeException("未找到设备信息");
		if (!oldTemplate.getMachineType().equals(salesMachine.getSpecId()))
			throw new RuntimeException("设备类型不匹配,不能做导入操作");
		List<SalesMachineAisleGoods> oldGoodsList = salesMachineAisleGoodsService.getSalesMachineAisleGoodsByMachineId(machineTemplate.getMachineId());
		if (oldGoodsList != null && oldGoodsList.size() > 0)
			throw new RuntimeException("设备货道已存在商品信息,不能做导入操作");
//		salesMachineAisleGoodsService.removeByMap();
		List<MachineTemplateDetail> details = machineTemplateDetailService.getMachineTemplateDetailByTemplate(machineTemplate.getId());
		List<SalesMachineAisleGoods> goodsList = details.stream().map(detail -> {
			SalesMachineAisleGoods newGoods = new SalesMachineAisleGoods();
			BeanUtils.copyProperties(detail, newGoods);
			newGoods.setSpePriceKey(detail.getSpecPriceKey());
			newGoods.setMachineId(machineTemplate.getMachineId());
			SalesMachineAisle salesMachineAisle = salesMachineAisleService.getOne(Wrappers.<SalesMachineAisle>query().lambda()
				.eq(SalesMachineAisle::getMachineId,machineTemplate.getMachineId())
				.eq(SalesMachineAisle::getCode,detail.getAisleCode())
			);
			newGoods.setAisleId(salesMachineAisle.getId());
			return newGoods;
		}).collect(Collectors.toList());
		return salesMachineAisleGoodsService.saveBatch(goodsList);
	}
}
