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
package com.pig4cloud.pig.order.agentSettlement.entity;

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
 * 结算申请表
 *
 * @author yuxinyin
 * @date 2019-04-09 21:43:24
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName("fx_agent_settlement")
@ApiModel("结算申请单")
@AllArgsConstructor
@NoArgsConstructor
public class AgentSettlement extends Model<AgentSettlement> {
	private static final long serialVersionUID = 1L;


	@TableId
	private Integer id;
	@ApiModelProperty("申请单编号，系统自动生成")
	private String code;
	@ApiModelProperty("结算到账金额")
	private BigDecimal total;
	@ApiModelProperty("结算金额")
	private BigDecimal originalTotal;
	@ApiModelProperty("通道费")
	private BigDecimal commission;

	@ApiModelProperty("可提现金额（结算前）")
	@TableField(exist = false)
	private BigDecimal oldWithdrawalPrice;
	@ApiModelProperty("可提现金额（结算后）")
	@TableField(exist = false)
	private BigDecimal newWithdrawalPrice;
	@ApiModelProperty("申请代理商")
	private Integer userId;

	@ApiModelProperty("代理商姓名")
	@TableField(exist = false)
	private String userName;
	@ApiModelProperty("申请结算时间")
	private LocalDateTime reqTime;
	@ApiModelProperty("申请结算开始时间")
	@TableField(exist = false)
	private String reqStartTime;
	@ApiModelProperty("申请结算结束时间")
	@TableField(exist = false)
	private String reqEndTime;
	@ApiModelProperty("审核人")
	private Integer auditorId;
	@ApiModelProperty("审核时间")
	private LocalDateTime auditTime;
	@ApiModelProperty("审核状态，0 待审核 1 通过 2 拒绝")
	private Integer status;
	@ApiModelProperty("备注")
	private String remark;

}
