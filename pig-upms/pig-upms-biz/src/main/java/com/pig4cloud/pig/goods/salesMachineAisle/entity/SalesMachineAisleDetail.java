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
package com.pig4cloud.pig.goods.salesMachineAisle.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.entity.SalesMachineAisleGoodsDetail;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * 销售机器货道表
 *
 * @author yuxinyin
 * @date 2019-04-09 21:40:44
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class SalesMachineAisleDetail extends SalesMachineAisle {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -4050957890489841100L;
	@ApiModelProperty("货道商品明细")
	@TableField(exist = false)
	private List<SalesMachineAisleGoodsDetail> aisleGoods;

}
