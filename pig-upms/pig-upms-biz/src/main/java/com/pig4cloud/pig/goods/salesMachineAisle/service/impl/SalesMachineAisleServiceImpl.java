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
package com.pig4cloud.pig.goods.salesMachineAisle.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisle;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisleDetail;
import com.pig4cloud.pig.goods.salesMachineAisle.mapper.SalesMachineAisleMapper;
import com.pig4cloud.pig.goods.salesMachineAisle.service.SalesMachineAisleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 销售机器货道
 *
 * @author zhuzubin
 * @date 2019-04-05 21:25:59
 */
@Service("salesMachineAisleService")
@AllArgsConstructor
public class SalesMachineAisleServiceImpl extends ServiceImpl<SalesMachineAisleMapper, SalesMachineAisle> implements SalesMachineAisleService {

	/**
	 * 销售机器货道简单分页查询
	 *
	 * @param salesMachineAisle 销售机器货道
	 * @return
	 */
	@Override
	public IPage<SalesMachineAisle> getSalesMachineAislePage(Page<SalesMachineAisle> page, SalesMachineAisle salesMachineAisle) {
		return baseMapper.getSalesMachineAislePage(page, salesMachineAisle);
	}

	@Override
	public List<SalesMachineAisleDetail> getSalesMachineAisleDetailByMachineId(Integer machineId) {
		return getSalesMachineAisleDetailByMachineId(Arrays.asList(machineId));
	}

	@Override
	public List<SalesMachineAisleDetail> getSalesMachineAisleDetailByMachineId(List<Integer> machineIds) {
		return baseMapper.getSalesMachineAisleDetailByMachineId(machineIds);
	}
}
