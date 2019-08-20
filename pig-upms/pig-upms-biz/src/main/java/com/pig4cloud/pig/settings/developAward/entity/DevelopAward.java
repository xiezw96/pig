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
package com.pig4cloud.pig.settings.developAward.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 发展奖励
 *
 * @author zhuzubin
 * @date 2019-04-13 17:41:33
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_develop_award")
public class DevelopAward extends Model<DevelopAward> {
	private static final long serialVersionUID = 1L;

	/**
	* id
	*/
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	* 奖励金额/个
	*/
	private BigDecimal awardMoney;
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
