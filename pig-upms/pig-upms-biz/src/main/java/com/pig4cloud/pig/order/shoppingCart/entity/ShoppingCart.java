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
package com.pig4cloud.pig.order.shoppingCart.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 购物车
 *
 * @author yuxinyin
 * @date 2019-04-17 18:54:04
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_shopping_cart")
@ApiModel("商品添加到购物车保存实体")
public class ShoppingCart extends Model<ShoppingCart> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	@ApiModelProperty("id")
	private Integer id;
	@ApiModelProperty(value = "消费者id或者代理商id", notes = "根据业务类型传不同的值")
	private Integer userId;
	@ApiModelProperty("商品id")
	private Integer goodsId;
	@ApiModelProperty("图片id")
	private String goodsAttId;
	@ApiModelProperty("商品名称")
	private String goodsName;
	@ApiModelProperty("购买数量")
	private Integer num;
	@ApiModelProperty("商品规格id")
	private Integer goodsSpeId;
	@ApiModelProperty("商品规格1")
	private String goodsSpe1;
	@ApiModelProperty("商品规格2")
	private String goodsSpe2;
	@ApiModelProperty("价目Key")
	private String spePriceKey;
	@ApiModelProperty("零售价")
	private BigDecimal salePrice;
	@ApiModelProperty("统批价")
	private BigDecimal tradePrice;
	@ApiModelProperty("添加时间")
	private LocalDateTime creatorTime;
	@ApiModelProperty("生成订单并付款后改变状态，或直接删除")
	private Integer isGenOrder;
	@ApiModelProperty("购物车类型 0 代理商 1 消费者")
	private Integer userType;

}
