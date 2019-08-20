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
package com.pig4cloud.pig.settings.comboManage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.pig4cloud.pig.settings.comboDetail.entity.ComboDetail;
import com.pig4cloud.pig.settings.comboMachine.entity.ComboMachine;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 套餐管理
 *
 * @author yuxinyin
 * @date 2019-04-09 21:48:35
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_combo_manage")
@ApiModel("激活套餐保存实体")
public class ComboManage extends Model<ComboManage> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@ApiModelProperty("名称")
	private String name;
	@ApiModelProperty("类型")
	private Integer type;
	@ApiModelProperty("商品金额")
	private BigDecimal goodPrice;
	@ApiModelProperty("代金券id")
	private Integer voucherId;
	@ApiModelProperty("代金券名称")
	@TableField(exist = false)
	private String voucherName;
	@ApiModelProperty("操作人")
	private Integer creatorId;
	@ApiModelProperty("创建人名称")
	@TableField(exist = false)
	private String creatorName;
	@ApiModelProperty("操作时间")
	private LocalDateTime createDate;

	@ApiModelProperty("套餐明细")
	@TableField(exist = false)
	private List<ComboDetail> detailList;

	@ApiModelProperty("套餐设备")
	@TableField(exist = false)
	private List<ComboMachine> machineList;

//	@ApiModelProperty("适用对象")
//	@TableField(exist = false)
//	private List<ComboSuitableObject> suitableObjectList;
}
