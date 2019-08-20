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
package com.pig4cloud.pig.accountmanage.developAuditDetail.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 发展明细
 *
 * @author zhuzubin
 * @date 2019-04-05 23:39:10
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName("fx_develop_audit_detail")
@ApiModel("发展明细实体")
public class DevelopAuditDetail extends Model<DevelopAuditDetail> {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Integer id;
	@ApiModelProperty("发展代理商ID")
	private Integer agentId;
	@ApiModelProperty("发展代理商姓名")
	@TableField(exist = false)
	private String agentName;
	@ApiModelProperty("代理商激活时间")
	@TableField(exist = false)
	private LocalDateTime activeTime;
	@ApiModelProperty("发展申请单ID")
	private Integer developId;
	@ApiModelProperty("奖励到账金额")
	private BigDecimal awardMoney;

}
