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
package com.pig4cloud.pig.goods.goodsSpePrice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 商品规格价格表
 *
 * @author yuxinyin
 * @date 2019-04-09 21:39:55
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_goods_spe_price")
public class GoodsSpePrice extends Model<GoodsSpePrice> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(hidden = true)
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@ApiModelProperty("商品id")
	private Integer goodsId;
	@ApiModelProperty("价目key")
	private String spePriceKey;
	/**
	 * 规格名称1
	 */
	private String speName1;
	/**
	 * 规格名称2
	 */
	private String speName2;
	/**
	 * 规格名称3
	 */
	@ApiModelProperty(hidden = true)
	private String speName3;
	/**
	 * 规格名称4
	 */
	@ApiModelProperty(hidden = true)
	private String speName4;
	/**
	 * 规格1
	 */
	private String speId1;
	/**
	 * 规格2
	 */
	private String speId2;
	/**
	 * 规格3
	 */
	@ApiModelProperty(hidden = true)
	private String speId3;
	/**
	 * 规格4
	 */
	@ApiModelProperty(hidden = true)
	private String speId4;
	/**
	 * 零售价
	 */
	private BigDecimal salePrice;
	/**
	 * 统批价
	 */
	private BigDecimal tradePrice;
	/**
	 * 库存
	 */
	private Integer inventoryNum;
	/**
	 * 销量
	 */
	private Integer salesVolume;
	@ApiModelProperty("重量(kg)")
	private BigDecimal weight;

}
