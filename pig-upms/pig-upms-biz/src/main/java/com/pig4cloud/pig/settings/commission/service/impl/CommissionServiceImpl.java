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
package com.pig4cloud.pig.settings.commission.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.settings.commission.entity.Commission;
import com.pig4cloud.pig.settings.commission.mapper.CommissionMapper;
import com.pig4cloud.pig.settings.commission.service.CommissionService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

/**
 * 通道费设置
 *
 * @author zhuzubin
 * @date 2019-04-05 11:29:49
 */
@Service("commissionService")
public class CommissionServiceImpl extends ServiceImpl<CommissionMapper, Commission> implements CommissionService {

	@Override
	public BigDecimal getCommissionValue(BigDecimal commission, BigDecimal totalMoney) {
		if (commission == null || totalMoney == null || totalMoney.compareTo(new BigDecimal(0)) <= 0)
			return new BigDecimal(0);
		return totalMoney.multiply(commission);
	}

	@Override
	public BigDecimal getCommissionValue(BigDecimal totalMoney) {
		return getCommissionValue(getCommission(), totalMoney);
	}

	@Override
	public BigDecimal getCommission() {
		return list().stream()
			.filter(c -> Optional.ofNullable(c.getCommission()).isPresent())
			.map(Commission::getCommission)
			.findFirst()
			.orElse(null);
	}

	@Override
	public BigDecimal subtractCommission(BigDecimal totalMoney) {
		return subtractCommission(getCommission(), totalMoney);
	}

	@Override
	public BigDecimal subtractCommission(BigDecimal commission, BigDecimal totalMoney) {
		if (totalMoney == null)
			return null;

		return totalMoney.subtract(getCommissionValue(commission, totalMoney));
	}
}
