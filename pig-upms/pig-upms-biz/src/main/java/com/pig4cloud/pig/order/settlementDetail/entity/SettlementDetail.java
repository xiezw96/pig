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
package com.pig4cloud.pig.order.settlementDetail.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
import java.time.LocalDateTime;

/**
 * 结算订单明细
 *
 * @author yuxinyin
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@TableName("fx_settlement_detail")
@ApiModel("结算明细")
public class SettlementDetail extends Model<SettlementDetail> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	@ApiModelProperty("代理商id")
	private Integer agentId;
	@ApiModelProperty("归属代理商")
	private Integer sourceAgentId;
	@ApiModelProperty("消费者id")
	private Integer userId;
	@ApiModelProperty("柜子id")
	private Integer machineId;
	@ApiModelProperty("柜子id")
	@TableField(exist = false)
	private String machineName;
	@ApiModelProperty("柜子编码")
	@TableField(exist = false)
	private String machineCode;
	@ApiModelProperty("归属  0 自有（自己的设备上消费）、1 商城（线上商城消费）、2 下级（发展的下级代理设备省消费）")
	private Integer source;
	@ApiModelProperty("结算id")
	private Integer settlementId;
	@ApiModelProperty("订单id")
	private Integer orderId;
	@ApiModelProperty("订单编号")
	private String orderCode;
	@ApiModelProperty("订单明细id")
	private Integer orderDetailId;
	@ApiModelProperty("提成比例")
	private BigDecimal shareProfit;
	@ApiModelProperty("支付时间")
	private LocalDateTime payTime;
	@ApiModelProperty("订单下单时间")
	private LocalDateTime orderCreateTime;
	@ApiModelProperty("结算金额")
	private BigDecimal settlementPrice;

	/**
	 * 订单总金额
	 */
	@TableField(exist = false)
	private String orderTotal;
	/**
	 * 商品名称
	 */
	@TableField(exist = false)
	private String goodsName;
	/**
	 * 商品图片
	 */
	@TableField(exist = false)
	private String goodsAttId;
	/**
	 * 商品规格1
	 */
	@TableField(exist = false)
	private String goodsSpe1;
	/**
	 * 商品规格2
	 */
	@TableField(exist = false)
	private String goodsSpe2;
	/**
	 * 零售价
	 */
	@TableField(exist = false)
	private BigDecimal salePrice;
	/**
	 * 统批价
	 */
	@TableField(exist = false)
	private BigDecimal tradePrice;
	/**
	 * 购买数量
	 */
	@TableField(exist = false)
	private Integer payNum;

}
