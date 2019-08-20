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
package com.pig4cloud.pig.goods.salesMachineAisleGoods.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.entity.SalesMachineAisleGoods;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.entity.SalesMachineAisleGoodsDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 销售机器货道商品
 *
 * @author zhuzubin
 * @date 2019-04-05 21:25:59
 */
public interface SalesMachineAisleGoodsMapper extends BaseMapper<SalesMachineAisleGoods> {
	/**
	 * 销售机器货道商品简单分页查询
	 *
	 * @param salesMachineAisleGoods 销售机器货道商品
	 * @return
	 */
	IPage<SalesMachineAisleGoods> getSalesMachineAisleGoodsPage(Page page, @Param("salesMachineAisleGoods") SalesMachineAisleGoods salesMachineAisleGoods);


	List<SalesMachineAisleGoods> getSalesMachineAisleGoodsByMachineId(@Param("machineId") Integer machineId);

	/**
	 * 分页查询代理商的设备商品
	 * <p>Title: getSalesMachineAisleGoodsDetailPage</p>
	 * <p>Description: </p>
	 *
	 * @return com.baomidou.mybatisplus.core.metadata.IPage<com.pig4cloud.pig.goods.salesMachineAisleGoods.entity.SalesMachineAisleGoodsDetail>
	 * @date 2019年06月18日
	 * @author 余新引
	 */
	IPage<SalesMachineAisleGoodsDetail> getSalesMachineAisleGoodsDetailPage(Page page, @Param("salesMachineAisleGoodsDetail") SalesMachineAisleGoodsDetail salesMachineAisleGoodsDetail);

	/**
	 * 查询代理商货道商品明细
	 * <p>Title: getSalesMachineAisleGoodsDetailByAisleId</p>
	 * <p>Description: </p>
	 *
	 * @return java.util.List<com.pig4cloud.pig.goods.salesMachineAisleGoods.entity.SalesMachineAisleGoodsDetail>
	 * @date 2019年06月18日
	 * @author 余新引
	 */
	List<SalesMachineAisleGoodsDetail> getSalesMachineAisleGoodsDetailByAisleId(@Param("aisleId") Integer aisleId);
}
