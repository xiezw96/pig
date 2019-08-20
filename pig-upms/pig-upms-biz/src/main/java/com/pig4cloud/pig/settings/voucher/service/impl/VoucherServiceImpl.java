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
package com.pig4cloud.pig.settings.voucher.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.settings.voucher.entity.Voucher;
import com.pig4cloud.pig.settings.voucher.mapper.VoucherMapper;
import com.pig4cloud.pig.settings.voucher.service.VoucherService;
import com.pig4cloud.pig.settings.voucherGrantRecord.entity.VoucherGrantRecord;
import com.pig4cloud.pig.settings.voucherGrantRecord.service.VoucherGrantRecordService;

import lombok.AllArgsConstructor;

/**
 * 代金券
 *
 * @author zhuzubin
 * @date 2019-04-05 11:18:34
 */
@AllArgsConstructor
@Service("voucherService")
public class VoucherServiceImpl extends ServiceImpl<VoucherMapper, Voucher> implements VoucherService {

	private final VoucherGrantRecordService voucherGrantRecordService;
	/**
	 * 代金券简单分页查询
	 * @param voucher 代金券
	 * @return
	 */
	@Override
	public IPage<Voucher> getVoucherPage(Page<Voucher> page, Voucher voucher) {
		return baseMapper.getVoucherPage(page, voucher);
	}

	@Override
	public boolean removeVoucherById(Integer id) {
		List<VoucherGrantRecord> recordList = voucherGrantRecordService.list(Wrappers.<VoucherGrantRecord>query().lambda().eq(VoucherGrantRecord :: getVoucherId, id));
		if (recordList != null && recordList.size() > 0) {
			return Boolean.FALSE;
		}
		return super.removeById(id);
	}

}
