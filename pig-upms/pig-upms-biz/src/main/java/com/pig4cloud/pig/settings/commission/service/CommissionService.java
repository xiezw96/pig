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
package com.pig4cloud.pig.settings.commission.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.settings.commission.entity.Commission;

import java.math.BigDecimal;

/**
 * 通道费设置
 *
 * @author zhuzubin
 * @date 2019-04-05 11:29:49
 */
public interface CommissionService extends IService<Commission> {

	/**
	 * 获取通道费比例
	 * <p>Title: getCommission</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年07月11日
	 * @author 余新引
	 */
	BigDecimal getCommission();

	/**
	 * 获取通道费值
	 * <p>Title: getCommissionValue</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年07月11日
	 * @author 余新引
	 */
	BigDecimal getCommissionValue(BigDecimal commission, BigDecimal totalMoney);


	/**
	 * 获取通道费值
	 * <p>Title: getCommissionValue</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年07月11日
	 * @author 余新引
	 */
	BigDecimal getCommissionValue(BigDecimal totalMoney);

	/**
	 * 计算扣除通道费
	 * <p>Title: subtractCommission</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年07月11日
	 * @author 余新引
	 */
	BigDecimal subtractCommission(BigDecimal commission, BigDecimal totalMoney);

	/**
	 * 计算扣除通道费
	 * <p>Title: subtractCommission</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年07月11日
	 * @author 余新引
	 */
	BigDecimal subtractCommission(BigDecimal totalMoney);
}
