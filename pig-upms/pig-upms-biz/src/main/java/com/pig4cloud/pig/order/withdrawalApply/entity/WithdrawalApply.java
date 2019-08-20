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
package com.pig4cloud.pig.order.withdrawalApply.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 提现申请表
 *
 * @author yuxinyin
 * @date 2019-04-09 21:47:33
 */
@ApiModel("提现申请单实体")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName("fx_withdrawal_apply")
@AllArgsConstructor
public class WithdrawalApply extends Model<WithdrawalApply> {
	private static final long serialVersionUID = 1L;

	public WithdrawalApply() {
		super();
	}
	/**
	 * id
	 */
	@TableId
	@ApiModelProperty("id")
	private Integer id;
	@ApiModelProperty("提现单号")
	private String code;
	@ApiModelProperty("提现金额")
	private BigDecimal total;
	@ApiModelProperty("当前余额")
	@TableField(exist = false)
	private BigDecimal withdrawalPrice;
	@ApiModelProperty("银行开户名")
	@TableField(exist = false)
	private String bankAccountName;
	@ApiModelProperty("身份证正面")
	@TableField(exist = false)
	private String identityCardFront;
	@ApiModelProperty("身份证反面")
	@TableField(exist = false)
	private String identityCardVerso;
	@ApiModelProperty("银行卡正面")
	@TableField(exist = false)
	private String bankCardFront;
	@ApiModelProperty("银行卡反面")
	@TableField(exist = false)
	private String bankCardVerso;
	@ApiModelProperty("申请代理商")
	private Integer userId;
	@ApiModelProperty("申请代理商姓名")
	@TableField(exist = false)
	private String userName;
	@ApiModelProperty("申请代理商信息")
	@TableField(exist = false)
	private Agent agent;
	@ApiModelProperty("申请时间")
	private LocalDateTime reqTime;
	@ApiModelProperty("申请开始时间")
	@TableField(exist = false)
	private String reqStartTime;
	@ApiModelProperty("申请结束时间")
	@TableField(exist = false)
	private String reqEndTime;
	@ApiModelProperty("审核人")
	private Integer auditorId;
	@ApiModelProperty("审核时间")
	private LocalDateTime auditTime;
	@ApiModelProperty("审核状态 0 待审核 1 通过 2 拒绝")
	private Integer auditStatus;
	@ApiModelProperty("打款状态 0 未打款 1 已打款")
	private Integer remitStatus;
	@ApiModelProperty("打款确认人员")
	private Integer remitUserId;
	@ApiModelProperty("打款大约时间")
	private LocalDateTime remitTime;
	@ApiModelProperty("备注")
	private String remark;

}
