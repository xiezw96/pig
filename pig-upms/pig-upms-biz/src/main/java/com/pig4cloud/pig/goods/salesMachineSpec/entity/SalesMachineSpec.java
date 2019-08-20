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
package com.pig4cloud.pig.goods.salesMachineSpec.entity;

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
 * 设备管理
 *
 * @author zhuzubin
 * @date 2019-04-05 14:08:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_sales_machine_spec")
@ApiModel("销售机器型号实体")
public class SalesMachineSpec extends Model<SalesMachineSpec> {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@ApiModelProperty("厂家")
	private String provider;
	@ApiModelProperty("型号")
	private String spec;
	@ApiModelProperty("统批价")
	private BigDecimal tradePrice;
	@ApiModelProperty("货道数量")
	private Integer aisleCount;
	@ApiModelProperty("协议")
	private String protocol;
	@ApiModelProperty("创建人")
	private Integer creatorId;
	@ApiModelProperty("创建人名称")
	@TableField(exist = false)
	private String creatorName;
	@ApiModelProperty("创建时间")
	private LocalDateTime createDate;
	@ApiModelProperty("修改人")
	private Integer modifierId;
	@ApiModelProperty("修改人名称")
	@TableField(exist = false)
	private String modifierName;
	@ApiModelProperty("修改时间")
	private LocalDateTime updateTime;


}
