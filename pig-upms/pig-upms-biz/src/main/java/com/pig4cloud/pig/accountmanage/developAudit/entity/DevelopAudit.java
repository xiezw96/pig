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
package com.pig4cloud.pig.accountmanage.developAudit.entity;

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
 * 发展奖励审核
 *
 * @author yuxinyin
 * @date 2019-04-09 21:31:52
 */
@ApiModel("发展奖励申请单实体")
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName("fx_develop_audit")
@AllArgsConstructor
@NoArgsConstructor
public class DevelopAudit extends Model<DevelopAudit> {
	private static final long serialVersionUID = 1L;
	
	
	/**
	 * id
	 */
	@TableId
	@ApiModelProperty("主键")
	private Integer id;
	@ApiModelProperty("代理商id")
	private Integer agentId;
	@ApiModelProperty("代理商姓名")
	private String agentName;
	@ApiModelProperty("发展数量")
	private Integer developNum;
	@ApiModelProperty("奖励到账金额")
	private BigDecimal totalMoney;
	@ApiModelProperty("奖励总金额")
	private BigDecimal originalTotalMoney;
	@ApiModelProperty("通道费")
	private BigDecimal commission;
	@ApiModelProperty("0:待审核 1:通过 2:拒绝")
	private Integer auditStatus;
	@ApiModelProperty("操作人")
	private Integer creatorId;
	@ApiModelProperty("操作人名称")
	@TableField(exist = false)
	private String creatorName;
	@ApiModelProperty("操作时间")
	private LocalDateTime createDate;
	@ApiModelProperty("备注")
	private String remark;
	@ApiModelProperty("申请时间")
	private LocalDateTime reqTime;
	@ApiModelProperty("申请开始时间")
	@TableField(exist = false)
	private String reqStartTime;
	@ApiModelProperty("申请结束时间")
	@TableField(exist = false)
	private String reqEndTime;

}
