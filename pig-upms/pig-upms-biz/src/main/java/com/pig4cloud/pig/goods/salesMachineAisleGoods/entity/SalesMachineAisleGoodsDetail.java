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
package com.pig4cloud.pig.goods.salesMachineAisleGoods.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 销售机器货道商品表
 *
 * @author yuxinyin
 * @date 2019-04-09 21:40:44
 */
@Data
public class SalesMachineAisleGoodsDetail extends SalesMachineAisleGoods {


	@ApiModelProperty("商品名称，同时作为商品名称/编码查询条件")
	@TableField(exist = false)
	private String goodsName;
	@ApiModelProperty("商品编码")
	@TableField(exist = false)
	private String goodsCode;
	@ApiModelProperty("货道编号")
	@TableField(exist = false)
	private String aisleCode;
	@ApiModelProperty("商品主图")
	@TableField(exist = false)
	private String goodsAttId;
	@ApiModelProperty("商品规格1")
	@TableField(exist = false)
	private String goodsSpe1;
	@ApiModelProperty("商品规格2")
	@TableField(exist = false)
	private String goodsSpe2;
	@ApiModelProperty("零售价")
	@TableField(exist = false)
	private BigDecimal salePrice;
	@ApiModelProperty("统批价")
	@TableField(exist = false)
	private BigDecimal tradePrice;

	@ApiModelProperty("设备名称，同时作为设备名称/编码查询条件")
	@TableField(exist = false)
	private String machineName;
	@ApiModelProperty("设备编号")
	@TableField(exist = false)
	private String machineCode;
	@ApiModelProperty("设备当前地址")
	@TableField(exist = false)
	private String machineCurrAddreaa;
	@ApiModelProperty(value = "归属代理商", hidden = true)
	@TableField(exist = false)
	private Integer belongsUser;

}
