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

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 销售机器货道商品表
 *
 * @author yuxinyin
 * @date 2019-04-09 21:40:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_sales_machine_aisle_goods")
public class SalesMachineAisleGoods extends Model<SalesMachineAisleGoods> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty("id")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@ApiModelProperty("商品id")
	private Integer goodsId;
	@ApiModelProperty("商品价格id")
	private Integer spePriceId;
	@ApiModelProperty("价目key")
	private String spePriceKey;
	@ApiModelProperty("销售机器id")
	private Integer machineId;
	@ApiModelProperty("货道id")
	private Integer aisleId;
	@ApiModelProperty("数量")
	private Integer num;
	@ApiModelProperty("货道编号")
	@TableField(exist = false)
	private String aisleCode;

}
