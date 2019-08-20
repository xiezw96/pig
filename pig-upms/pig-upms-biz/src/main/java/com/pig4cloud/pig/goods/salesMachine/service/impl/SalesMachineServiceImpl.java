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
package com.pig4cloud.pig.goods.salesMachine.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.goods.salesMachine.entity.SalesMachine;
import com.pig4cloud.pig.goods.salesMachine.mapper.SalesMachineMapper;
import com.pig4cloud.pig.goods.salesMachine.service.SalesMachineService;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisle;
import com.pig4cloud.pig.goods.salesMachineAisle.service.SalesMachineAisleService;
import com.pig4cloud.pig.goods.salesMachineSpec.entity.SalesMachineSpec;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 设备管理
 *
 * @author zhuzubin
 * @date 2019-04-05 14:08:11
 */
@Service("salesMachineService")
@AllArgsConstructor
@Slf4j
public class SalesMachineServiceImpl extends ServiceImpl<SalesMachineMapper, SalesMachine> implements SalesMachineService {
	private final SalesMachineAisleService salesMachineAisleService;

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean save(SalesMachineSpec spec, SalesMachine entity) {
		// 保存设备
		entity.setStatus(0);
		boolean result = super.save(entity);
		if (result) {
			// 生成货道
			List<SalesMachineAisle> aisles = new ArrayList<>();
			for (int i = 0; i < spec.getAisleCount(); i++) {
				SalesMachineAisle salesMachineAisle = new SalesMachineAisle();
				salesMachineAisle.setCode((i + 1) + "");
				salesMachineAisle.setMachineId(entity.getId());
				salesMachineAisle.setStatus(1);
				aisles.add(salesMachineAisle);
			}
			result = salesMachineAisleService.saveBatch(aisles);
			if (!result) {
				throw new RuntimeException("货道保存失败");
			}
		}
		return result;
	}

	/**
	 * 设备管理简单分页查询
	 *
	 * @param salesMachine 设备管理
	 * @return
	 */
	@Override
	public IPage<SalesMachine> getSalesMachinePage(Page<SalesMachine> page, SalesMachine salesMachine) {
		return baseMapper.getSalesMachinePage(page, salesMachine);
	}

	@Override
	public SalesMachine getByCode(String code) {
		return getOne(Wrappers.<SalesMachine>query().lambda().eq(SalesMachine::getCode, code));
	}
}
