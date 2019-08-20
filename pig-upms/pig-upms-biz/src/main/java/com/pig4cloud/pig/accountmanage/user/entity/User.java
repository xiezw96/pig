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
package com.pig4cloud.pig.accountmanage.user.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 消费者表
 *
 * @author yuxinyin
 * @date 2019-04-09 21:36:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_user")
public class User extends Model<User> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 性别
	 */
	private Integer sex;
	/**
	 * 生日
	 */
	private LocalDateTime birthday;
	/**
	 * 头像
	 */
	private String image;
	/**
	 * 地区
	 */
	private String region;
	/**
	 * 手机号
	 */
	private String phone;
	/**
	 * 注册ip
	 */
	private String registerId;
	/**
	 * 注册设备
	 */
	private String registerEq;
	/**
	 * 注册时间
	 */
	private LocalDateTime registerDate;

	@TableField(exist = false)
	private String registerStartDate;
	@TableField(exist = false)
	private String registerEndDate;

	/**
	 * 最近登录时间
	 */
	private LocalDateTime loginTime;
	/**
	 * 最近登录ip
	 */
	private String loginIp;
	/**
	 * 归属代理
	 */
	private Integer ofProxy;
	/**
	 * 归属代理姓名
	 */
	@TableField(exist = false)
	private String ofProxyName;
	/**
	 * 累计消费
	 */
	private BigDecimal totleConsumption;
	/**
	 * 近期消费
	 */
	private BigDecimal recentConsumption;
	/**
	 * wx_openid
	 */
	private String wxOpenid;

	@ApiModelProperty("状态 0 正常 1 冻结")
	private Integer state;

}
