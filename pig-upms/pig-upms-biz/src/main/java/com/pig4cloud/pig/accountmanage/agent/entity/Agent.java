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
package com.pig4cloud.pig.accountmanage.agent.entity;

import com.baomidou.mybatisplus.annotation.FieldStrategy;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 代理商用户表
 *
 * @author yuxinyin
 * @date 2019-04-09 21:29:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_agent")
@ApiModel("代理商实体")
public class Agent extends Model<Agent> {
	private static final long serialVersionUID = 1L;

	public Agent() {
	}

	@TableId(type = IdType.INPUT)
	@ApiModelProperty("代理商ID")
	private Integer agentId;
	@ApiModelProperty("姓名")
	private String name;
	@ApiModelProperty("等级")
	private String level;
	@ApiModelProperty("头像")
	private String photo;
	@ApiModelProperty(" 性别")
	private Integer sex;
	@ApiModelProperty("生日")
	private LocalDateTime birthday;
	@ApiModelProperty("分润比例")
	private BigDecimal profit;
	@ApiModelProperty("下级分润比例")
	private BigDecimal childProfit;
	@ApiModelProperty("手机号")
	private String phone;
	@ApiModelProperty("新手机号")
	private String newPhone;
	@ApiModelProperty("省")
	private String privince;
	@ApiModelProperty("市")
	private String city;
	@ApiModelProperty("区")
	private String area;
	@ApiModelProperty("公司")
	private String company;
	@ApiModelProperty("地址")
	private String address;
	@ApiModelProperty("微信号")
	private String wechatId;
	@ApiModelProperty("新微信号")
	private String newWechatId;
	@ApiModelProperty("身份证号")
	private String idCard;
	@ApiModelProperty("推荐人")
	private Integer referrerId;
	@ApiModelProperty("推荐人关系")
	private String referrerIds;
	@ApiModelProperty("推荐人姓名")
	@TableField(exist = false)
	private String referrerName;
	@ApiModelProperty("身份证正面")
	private String identityCardFront;
	@ApiModelProperty("身份证反面")
	private String identityCardVerso;
	@ApiModelProperty("新身份证正面")
	private String newIdentityCardFront;
	@ApiModelProperty("新身份证反面")
	private String newIdentityCardVerso;
	@ApiModelProperty("银行开户名")
	private String bankAccountName;
	@ApiModelProperty("银行卡正面")
	private String bankCardFront;
	@ApiModelProperty("银行卡反面")
	private String bankCardVerso;
	@ApiModelProperty("新银行开户名")
	private String newBankAccountName;
	@ApiModelProperty("新银行卡正面")
	private String newBankCardFront;
	@ApiModelProperty("新银行卡反面")
	private String newBankCardVerso;
	@ApiModelProperty(value = "交易密码", hidden = true)
	@JsonIgnore
	private String transactionPassword;
	@ApiModelProperty("提现金额")
	private BigDecimal withdrawalPrice;
	@ApiModelProperty("推荐码")
	private String referralCode;
	@ApiModelProperty("审核备注")
	private String auditRemark;
	@ApiModelProperty(value = "0:未结算 1:已结算", hidden = true)
	@JsonIgnore
	private Integer isSettlement;
	@ApiModelProperty("激活状态 0 未激活 1 已激活 2 冻结")
	private Integer activeStatus;
	@ApiModelProperty("激活时间")
	private LocalDateTime activeTime;
	@ApiModelProperty("0:待审核 1:通过 1:拒绝")
	private Integer auditStatus;
	@ApiModelProperty("审核申请日期")
	private LocalDateTime auditReqTime;
	@ApiModelProperty("审核人员")
	private Integer auditorId;
	@ApiModelProperty("审核人员姓名")
	@TableField(exist = false)
	private String auditorName;
	@ApiModelProperty("审核日期")
	private LocalDateTime auditTime;
	@ApiModelProperty("修改审核状态 0:待审核 1:通过 1:拒绝")
	private Integer updateAuditStatus;
	@ApiModelProperty("修改审核申请日期")
	private LocalDateTime updateAuditReqTime;
	@ApiModelProperty("修改审核申请开始日期")
	@TableField(exist = false)
	private String updateAuditReqStartTime;
	@ApiModelProperty("修改审核申请结束日期")
	@TableField(exist = false)
	private String updateAuditReqEndTime;
	@ApiModelProperty("修改审核人员")
	@TableField(strategy = FieldStrategy.IGNORED)
	private Integer updateAuditorId;
	@ApiModelProperty("修改审核人姓名")
	@TableField(exist = false)
	private String updateAuditorName;
	@ApiModelProperty("修改审核日期")
	@TableField(strategy = FieldStrategy.IGNORED)
	private LocalDateTime updateAuditTime;
	@ApiModelProperty("修改审核备注")
	@TableField(strategy = FieldStrategy.IGNORED)
	private String updateAuditRemark;

}
