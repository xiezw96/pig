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
package com.pig4cloud.pig.settings.comboMachine.entity;

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

/**
 * 套餐设备
 *
 * @author yuxinyin
 * @date 2019-04-09 21:48:01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_combo_machine")
@ApiModel("激活套餐设备明细实体")
public class ComboMachine extends Model<ComboMachine> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty("id")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@ApiModelProperty("套餐id")
	private Integer comboId;
	@ApiModelProperty("数量")
	private Integer num;
	@ApiModelProperty("型号id")
	private Integer machineSpecId;
	@ApiModelProperty("型号名称")
	@TableField(exist = false)
	private String machineSpecName;
	@ApiModelProperty("设备采购价")
	@TableField(exist = false)
	private BigDecimal machineTradePrice;

}
