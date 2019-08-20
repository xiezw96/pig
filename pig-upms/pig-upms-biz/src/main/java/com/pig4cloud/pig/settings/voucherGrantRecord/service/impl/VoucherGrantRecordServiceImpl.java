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
package com.pig4cloud.pig.settings.voucherGrantRecord.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.settings.voucherGrantRecord.entity.VoucherGrantRecord;
import com.pig4cloud.pig.settings.voucherGrantRecord.mapper.VoucherGrantRecordMapper;
import com.pig4cloud.pig.settings.voucherGrantRecord.service.VoucherGrantRecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * 代金券发放记录
 *
 * @author zhuzubin
 * @date 2019-04-05 11:22:32
 */
@AllArgsConstructor
@Service("voucherGrantRecordService")
public class VoucherGrantRecordServiceImpl extends ServiceImpl<VoucherGrantRecordMapper, VoucherGrantRecord> implements VoucherGrantRecordService {

	/**
	 * 代金券发放记录简单分页查询
	 * @param voucherGrantRecord 代金券发放记录
	 * @return
	 */
	@Override
	public IPage<VoucherGrantRecord> getVoucherGrantRecordPage(Page<VoucherGrantRecord> page, VoucherGrantRecord voucherGrantRecord) {
		return baseMapper.getVoucherGrantRecordPage(page, voucherGrantRecord);
	}

	@Override
	public VoucherGrantRecord getVoucherGrantRecordById(Integer id) {
		return baseMapper.getVoucherGrantRecordById(id);
	}

}
