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
package com.pig4cloud.pig.goods.salesMachine.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 设备管理
 *
 * @author zhuzubin
 * @date 2019-04-05 14:08:11
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_sales_machine")
public class SalesMachine extends Model<SalesMachine> {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;

	@ApiModelProperty("机器名称")
	private String name;

	@ApiModelProperty("设备类型id")
	private Integer specId;

	@ApiModelProperty("设备类型厂家")
	@TableField(exist = false)
	private String specProvider;

	@ApiModelProperty("设备类型名称")
	@TableField(exist = false)
	private String specName;

	@ApiModelProperty("编码")
	private String code;
	@ApiModelProperty("状态 0 待激活 1 正常  2 故障 3补货 4 开门 5 离线 6 回收")
	private Integer status;

	@ApiModelProperty("当前地址")
	private String currAddreaa;

	@ApiModelProperty("激活地址")
	private String activateAddress;

	@ApiModelProperty("激活日期")
	private LocalDateTime activateTime;

	@ApiModelProperty("激活开始日期")
	@TableField(exist = false)
	private String activateStartTime;

	@ApiModelProperty("激活结束日期")
	@TableField(exist = false)
	private String activateEndTime;

	@ApiModelProperty("所属人")
	private Integer belongsUser;

	@ApiModelProperty(value = "推荐人关系", hidden = true)
	@JsonIgnore
	@TableField(exist = false)
	private String referrerIds;

	@ApiModelProperty("代理商名称或者手机号")
	@TableField(exist = false)
	private String belongs;

	@ApiModelProperty("所属类型 使用权/所有权")
	private Integer belongsType;

	@ApiModelProperty("操作人")
	private Integer creatorId;

	@ApiModelProperty("创建时间")
	private LocalDateTime createTime;

	@ApiModelProperty("推广码")
	private String referralCode;

}
