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
package com.pig4cloud.pig.goods.salesMachineFault.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 销售机器故障表
 *
 * @author yuxinyin
 * @date 2019-04-09 21:40:44
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_sales_machine_fault")
public class SalesMachineFault extends Model<SalesMachineFault> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty("id")
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	@ApiModelProperty("销售机器id")
	private Integer machineId;
	@ApiModelProperty("销售机器码")
	@TableField(exist = false)
	private Integer machineCode;
	@ApiModelProperty("当前地址")
	@TableField(exist = false)
	private String currAddreaa;
	@ApiModelProperty("故障类型 0 整机故障 1 货道故障 2 其他故障")
	private Integer faultType;
	@ApiModelProperty("货道id")
	private Integer aisleId;
	@ApiModelProperty("故障描述")
	private String remark;
	@ApiModelProperty("代理商id")
	private Integer agentId;
	@ApiModelProperty("代理商名称")
	@TableField(exist = false)
	private String agentName;
	@ApiModelProperty("创建人")
	private Integer creatorId;
	@ApiModelProperty("故障提交时间")
	private LocalDateTime createTime;
	@ApiModelProperty("故障提交开始时间")
	@TableField(exist = false)
	private String startCreateTime;
	@ApiModelProperty("故障提交结束时间")
	@TableField(exist = false)
	private String endCreateTime;
	@ApiModelProperty("处理时间")
	private LocalDateTime operateTime;
	@ApiModelProperty("处理人")
	private Integer operatorId;
	@ApiModelProperty("处理人姓名")
	@TableField(exist = false)
	private Integer operatorName;
	@ApiModelProperty("状态 0 待处理 1 成功 2 失败")
	private Integer status;
	@ApiModelProperty("处理描述")
	private String dealRemark;
	@ApiModelProperty("图片信息，多个逗号隔开")
	private String pics;

}
