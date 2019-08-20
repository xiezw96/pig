/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. Neither the name of the pig4cloud.com developer nor
 * the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission. Author:
 * lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.settings.comboMachine.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.settings.comboMachine.entity.ComboMachine;
import com.pig4cloud.pig.settings.comboMachine.mapper.ComboMachineMapper;
import com.pig4cloud.pig.settings.comboMachine.service.ComboMachineService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * 套餐明细
 *
 * @author zhuzubin
 * @date 2019-04-05 11:27:38
 */
@Service("comboMachineService")
public class ComboMachineServiceImpl extends ServiceImpl<ComboMachineMapper, ComboMachine> implements ComboMachineService {

	@Override
	public boolean removeByComboId(Integer comboId) {
		if (StringUtils.isEmpty(comboId))
			return Boolean.FALSE;
		return baseMapper.removeByComboId(comboId);
	}

}
