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
package com.pig4cloud.pig.settings.voucherGrantRecord.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 代金券发放记录
 *
 * @author yuxinyin
 * @date 2019-04-09 21:51:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_voucher_grant_record")
public class VoucherGrantRecord extends Model<VoucherGrantRecord> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type=IdType.AUTO,value="id")
	private Integer id;
	/**
	 * 代理商id
	 */
	private Integer userId;
	/**
	 * 代金券id
	 */
	private Integer voucherId;
	/**
	 * 发放类型
	 */
	private Integer grantType;
	/**
	 * 发放时间
	 */
	private LocalDateTime createDate;
	/**
	 * 是否使用
	 */
	private Integer isConsumer;
	
	/**
	 * 代金券名称
	 */
	@ApiModelProperty(hidden=true)
	@TableField(exist=false)
	private String voucherName;
	/**
	 * 面额
	 */
	@ApiModelProperty(hidden=true)
	@TableField(exist=false)
	private BigDecimal price;
	/**
	 * 最低消费
	 */
	@ApiModelProperty(hidden=true)
	@TableField(exist=false)
	private BigDecimal minPrice;
	/**
	 * 有效期开始时间
	 */
	@ApiModelProperty(hidden=true)
	@TableField(exist=false)
	private LocalDateTime startTime;
	/**
	 * 有效期结束时间
	 */
	@ApiModelProperty(hidden=true)
	@TableField(exist=false)
	private LocalDateTime endTime;
	
	@TableField(exist=false)
	@ApiModelProperty(hidden=true)
	private String agentName;

}
