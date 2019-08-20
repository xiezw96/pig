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
package com.pig4cloud.pig.goods.salesMachine.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.goods.salesMachine.entity.SalesMachine;
import com.pig4cloud.pig.goods.salesMachineSpec.entity.SalesMachineSpec;

/**
 * 设备管理
 *
 * @author zhuzubin
 * @date 2019-04-05 14:08:11
 */
public interface SalesMachineService extends IService<SalesMachine> {

	/**
	 * 保存设备
	 * <p>Title: save</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年06月18日
	 * @author 余新引
	 */
	boolean save(SalesMachineSpec spec, SalesMachine entity);

	/**
	 * 设备管理简单分页查询
	 *
	 * @param salesMachine 设备管理
	 * @return
	 */
	IPage<SalesMachine> getSalesMachinePage(Page<SalesMachine> page, SalesMachine salesMachine);

	/**
	 * 根据设备编码查询设备
	 * <p>Title: getByCode</p>
	 * <p>Description: </p>
	 *
	 * @return com.pig4cloud.pig.goods.salesMachine.entity.SalesMachine
	 * @date 2019年06月26日
	 * @author 余新引
	 */
	SalesMachine getByCode(String code);

}
