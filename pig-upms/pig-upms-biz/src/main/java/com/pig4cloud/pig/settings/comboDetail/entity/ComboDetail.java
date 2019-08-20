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
package com.pig4cloud.pig.settings.comboDetail.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 套餐明细
 *
 * @author yuxinyin
 * @date 2019-04-09 21:48:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_combo_detail")
@ApiModel("激活套餐明细实体")
public class ComboDetail extends Model<ComboDetail> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty("id")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@ApiModelProperty("套餐id")
	private Integer comboId;
	@ApiModelProperty("商品id")
	private Integer goodsId;
	@ApiModelProperty("价目id")
	private Integer spePriceId;
	@ApiModelProperty("价目key")
	private String spePriceKey;
	@ApiModelProperty("数量")
	private Integer goodNum;
	@ApiModelProperty("商品名称")
	@TableField(exist = false)
	private String goodsName;
	@ApiModelProperty("商品主图")
	@TableField(exist = false)
	private String goodsAttId;
	@ApiModelProperty("规格1")
	@TableField(exist = false)
	private String sepcValue1;
	@ApiModelProperty("规格2")
	@TableField(exist = false)
	private String sepcValue2;
	@ApiModelProperty("零售价")
	@TableField(exist = false)
	private BigDecimal salePrice;
	@ApiModelProperty("统批价")
	@TableField(exist = false)
	private BigDecimal tradePrice;
	@ApiModelProperty("总价")
	@TableField(exist = false)
	private BigDecimal totalPrice;

}
