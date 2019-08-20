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
package com.pig4cloud.pig.settings.agentCombo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * 套餐明细
 *
 * @author yuxinyin
 * @date 2019-04-09 21:48:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_agent_combo")
@ApiModel("代理商激活套餐")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AgentCombo extends Model<AgentCombo> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty("id")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@ApiModelProperty("订单id")
	private Integer orderId;
	@ApiModelProperty("订单编号")
	@TableField(exist = false)
	private String orderCode;
	@ApiModelProperty("代理商id")
	private Integer agentId;
	@ApiModelProperty("代理商姓名")
	@TableField(exist = false)
	private String agentName;
	@ApiModelProperty("套餐id")
	private Integer comboId;
	@ApiModelProperty("套餐名称")
	@TableField(exist = false)
	private String comboName;
	@ApiModelProperty("套餐类型")
	private Integer comboType;
	@ApiModelProperty("金额")
	private BigDecimal totalMoney;
	@ApiModelProperty("操作时间")
	private LocalDateTime createDate;
	@ApiModelProperty("操作开始时间")
	@TableField(exist = false)
	private String startCreateDate;
	@ApiModelProperty("操作结束时间")
	@TableField(exist = false)
	private String endCreateDate;
	@ApiModelProperty("交易码")
	private String tradeNo;
	@ApiModelProperty("支付状态 0 未支付 1 已支付")
	private Integer payStatus;
	@ApiModelProperty("支付时间")
	private LocalDateTime payTime;
	@ApiModelProperty("收货地址")
	private Integer addressId;
	@ApiModelProperty("买家留言")
	private String message;

}
