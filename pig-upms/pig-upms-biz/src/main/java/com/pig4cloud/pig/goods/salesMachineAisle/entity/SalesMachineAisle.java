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
package com.pig4cloud.pig.goods.salesMachineAisle.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 销售机器货道表
 *
 * @author yuxinyin
 * @date 2019-04-09 21:40:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_sales_machine_aisle")
public class SalesMachineAisle extends Model<SalesMachineAisle> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty("id")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@ApiModelProperty("销售机器id")
	private Integer machineId;
	@ApiModelProperty("编码")
	private String code;
	@ApiModelProperty("状态 1 正常 2 故障 3 补货 4 开门")
	private Integer status;

}
