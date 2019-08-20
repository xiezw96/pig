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
package com.pig4cloud.pig.settings.logisticsTemplate.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.pig4cloud.pig.settings.logisticsTemplate.entity.LogisticsTemplate;

import java.math.BigDecimal;

/**
 * 物流模板
 *
 * @author zhuzubin
 * @date 2019-04-05 11:29:49
 */
public interface LogisticsTemplateService extends IService<LogisticsTemplate> {

	/**
	 * 根据总重量计算物流费用
	 * <p>Title: getLogisticsPrice</p>
	 * <p>Description: </p>
	 *
	 * @return java.math.BigDecimal
	 * @date 2019年07月04日
	 * @author 余新引
	 */
	BigDecimal getLogisticsPrice(String province, BigDecimal weight);
}
