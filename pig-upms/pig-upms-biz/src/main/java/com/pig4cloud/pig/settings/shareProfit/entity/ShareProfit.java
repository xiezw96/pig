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
package com.pig4cloud.pig.settings.shareProfit.entity;

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
 * 商城分润
 *
 * @author yuxinyin
 * @date 2019-04-09 21:50:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_share_profit")
public class ShareProfit extends Model<ShareProfit> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(hidden=true)
	@TableId(value="id",type=IdType.AUTO)
	private Integer id;
	/**
	 * 商品售价
	 */
	private BigDecimal goodsPrice;

	/**
	 * 代理上级分润比例
	 */
	private BigDecimal upAgentShareProfit;

	/**
	 * 代理分润比例
	 */
	private BigDecimal agentShareProfit;

	/**
	 * 省代分润
	 */
	private BigDecimal provinceShareProfit;

	/**
	 * 市代分润比例
	 */
	private BigDecimal cityShareProfit;

	/**
	 * 区/县代分润比例
	 */
	private BigDecimal areaShareProfit;
	/**
	 * 操作人
	 */
	@ApiModelProperty(hidden=true)
	private Integer creatorId;
	/**
	 * 操作时间
	 */
	@ApiModelProperty(hidden=true)
	private LocalDateTime createDate;

	/**
	 * 修改人
	 */
	@ApiModelProperty(hidden=true)
	private Integer modifierId;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(hidden=true)
	private LocalDateTime updateTime;

}