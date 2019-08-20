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
package com.pig4cloud.pig.goods.salesMachineAisle.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisle;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisleDetail;

import java.util.List;

/**
 * 销售机器货道
 *
 * @author zhuzubin
 * @date 2019-04-05 21:25:59
 */
public interface SalesMachineAisleService extends IService<SalesMachineAisle> {

	/**
	 * 销售机器货道简单分页查询
	 *
	 * @param salesMachineAisle 销售机器货道
	 * @return
	 */
	IPage<SalesMachineAisle> getSalesMachineAislePage(Page<SalesMachineAisle> page, SalesMachineAisle salesMachineAisle);

	/**
	 * 查询代理商所有设备的货道明细
	 * <p>Title: getSalesMachineAisleDetailByMachineId</p>
	 * <p>Description: </p>
	 *
	 * @return java.util.List<com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisleDetail>
	 * @date 2019年06月18日
	 * @author 余新引
	 */
	List<SalesMachineAisleDetail> getSalesMachineAisleDetailByMachineId(Integer machineId);

	/**
	 * 查询代理商所有设备的货道明细
	 * <p>Title: getSalesMachineAisleDetailByMachineId</p>
	 * <p>Description: </p>
	 *
	 * @return java.util.List<com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisleDetail>
	 * @date 2019年06月18日
	 * @author 余新引
	 */
	List<SalesMachineAisleDetail> getSalesMachineAisleDetailByMachineId(List<Integer> machineIds);

}
