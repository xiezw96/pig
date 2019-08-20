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
package com.pig4cloud.pig.settings.logisticsTemplate.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.settings.logisticsTemplate.entity.LogisticsTemplate;
import com.pig4cloud.pig.settings.logisticsTemplate.mapper.LogisticsTemplateMapper;
import com.pig4cloud.pig.settings.logisticsTemplate.service.LogisticsTemplateService;
import com.pig4cloud.pig.settings.logisticsTemplate.utils.LogisticsPriceUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 物流模板
 *
 * @author zhuzubin
 * @date 2019-04-05 11:29:49
 */
@Service("logisticsTemplateService")
public class LogisticsTemplateServiceImpl extends ServiceImpl<LogisticsTemplateMapper, LogisticsTemplate> implements LogisticsTemplateService {

	@Override
	public BigDecimal getLogisticsPrice(String province, BigDecimal weight) {
		if (weight == null || weight.compareTo(new BigDecimal(0)) <= 0)
			return new BigDecimal(0);
		LogisticsTemplate logisticsTemplate = list().stream().map(LogisticsPriceUtils::split).findFirst().orElse(null);
		if (logisticsTemplate == null) {
			throw new RuntimeException("物流模板未设置");
		}
		if (logisticsTemplate.getStartingWeight() == null) {
			throw new RuntimeException("物流模板首重未设置");
		}
		BigDecimal startingPrice = logisticsTemplate.getStartingPrice(), increment = logisticsTemplate.getIncrement();
		if (logisticsTemplate.getFarawayProvinceList() != null && logisticsTemplate.getFarawayProvinceList().contains(province)) {
			startingPrice = Optional.ofNullable(logisticsTemplate.getFarawayStartingPrice()).orElse(logisticsTemplate.getStartingPrice());
			increment = Optional.ofNullable(logisticsTemplate.getFarawayIncrement()).orElse(logisticsTemplate.getIncrement());
		}
		startingPrice = Optional.ofNullable(startingPrice).orElse(new BigDecimal(0));
		if (weight.compareTo(logisticsTemplate.getStartingWeight()) <= 0)
			return startingPrice;
		else {
			// 起步价 + (重量 - 起步价重量(1kg)) * 续重
			increment = Optional.ofNullable(increment).orElse(new BigDecimal(0));
			return weight.subtract(logisticsTemplate.getStartingWeight()).multiply(increment).add(startingPrice);
		}
	}
}
