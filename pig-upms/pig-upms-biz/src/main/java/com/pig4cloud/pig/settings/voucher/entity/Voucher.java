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
package com.pig4cloud.pig.settings.voucher.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
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
 * 代金券
 *
 * @author yuxinyin
 * @date 2019-04-09 21:51:09
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_voucher")
@ApiModel("代金券实体")
public class Voucher extends Model<Voucher> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@ApiModelProperty("名称")
	private String name;
	@ApiModelProperty("面额")
	private BigDecimal price;
	@ApiModelProperty("最低消费")
	private BigDecimal minPrice;
	@ApiModelProperty("有效期开始时间")
	private LocalDateTime startTime;
	@ApiModelProperty("有效期结束时间")
	private LocalDateTime endTime;
	@ApiModelProperty("使用状态 0  未使用 1 已使用")
	private Integer useStatus;
	@ApiModelProperty("是否过期 0  未过期 1 已过期")
	@TableField(exist = false)
	private Integer isExpires;
	/**
	 * 操作人
	 */
	@ApiModelProperty(hidden = true)
	private Integer creatorId;
	/**
	 * 操作时间
	 */
	@ApiModelProperty(hidden = true)
	private LocalDateTime createDate;

}
