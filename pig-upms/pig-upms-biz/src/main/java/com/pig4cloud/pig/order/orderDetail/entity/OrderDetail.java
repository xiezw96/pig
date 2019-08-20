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
package com.pig4cloud.pig.order.orderDetail.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * 订单明细表
 *
 * @author yuxinyin
 * @date 2019-04-17 18:45:04
 */
@ApiModel("订单明细实体")
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("fx_order_detail")
public class OrderDetail extends Model<OrderDetail> {
	private static final long serialVersionUID = 1L;

	@TableId
	@ApiModelProperty("明细ID")
	private Integer id;
	@ApiModelProperty("订单id")
	private Integer orderId;
	@ApiModelProperty("商品id")
	private Integer goodsId;
	@ApiModelProperty("价目id")
	private Integer spePriceId;
	@ApiModelProperty("价目Key")
	private String spePriceKey;
	@ApiModelProperty("商品名称")
	private String goodsName;
	@ApiModelProperty("商品图片")
	private String goodsAttId;
	@ApiModelProperty("商品规格1")
	private String goodsSpe1;
	@ApiModelProperty("商品规格2")
	private String goodsSpe2;
	@ApiModelProperty("零售价")
	private BigDecimal salePrice;
	@ApiModelProperty("统批价")
	private BigDecimal tradePrice;
	@ApiModelProperty("物流单价")
	private BigDecimal logisticsPrice;
	@ApiModelProperty("优惠券")
	private Integer voucherId;
	@ApiModelProperty("购买数量")
	private Integer payNum;
	@ApiModelProperty("实付金额")
	private BigDecimal payPrice;

}
