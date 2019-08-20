/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. Neither the name of the pig4cloud.com developer nor
 * the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission. Author:
 * lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.settings.commission.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 通道费设置
 *
 * @author yuxinyin
 * @date 2019-04-09 21:50:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_commission")
public class Commission extends Model<Commission> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@ApiModelProperty("通道费设置")
	private BigDecimal commission;
	@ApiModelProperty("操作人")
	private Integer creatorId;
	@ApiModelProperty("操作时间")
	private LocalDateTime createDate;
	@ApiModelProperty("修改人")
	private Integer modifierId;
	@ApiModelProperty("修改时间")
	private LocalDateTime updateTime;

}
