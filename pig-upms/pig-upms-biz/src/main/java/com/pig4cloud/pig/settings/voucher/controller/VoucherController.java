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
package com.pig4cloud.pig.settings.voucher.controller;

import java.time.LocalDateTime;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.settings.voucher.entity.Voucher;
import com.pig4cloud.pig.settings.voucher.service.VoucherService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;

/**
 * 代金券
 *
 * @author zhuzubin
 * @date 2019-04-05 11:18:34
 */
@Api(value = "voucher", tags = "代金券管理")
@RestController
@AllArgsConstructor
@RequestMapping("/voucher")
public class VoucherController {

	private final VoucherService voucherService;

	/**
	 * 简单分页查询
	 * @param page 分页对象
	 * @param voucher 代金券
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<Voucher>> getVoucherPage(Page<Voucher> page, Voucher voucher) {
		return new R<>(voucherService.getVoucherPage(page, voucher));
	}

	/**
	 * 通过id查询单条记录
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
	@GetMapping("/{id}")
	public R<Voucher> getById(@PathVariable("id") Integer id) {
		return new R<>(voucherService.getById(id));
	}

	/**
	 * 新增记录
	 * @param voucher
	 * @return R
	 */
	@ApiOperation(value = "新增记录", notes = "新增记录")
	@SysLog("新增代金券")
	@PostMapping
	// @PreAuthorize("@pms.hasPermission('voucher_voucher_add')")
	public R save(@RequestBody Voucher voucher) {
//		voucher.setCreatorId(SecurityUtils.getUser().getId());
		voucher.setCreateDate(LocalDateTime.now());
		return new R<>(voucherService.save(voucher));
	}

	/**
	 * 修改记录
	 * @param voucher
	 * @return R
	 */
	@ApiOperation(value = "修改记录", notes = "修改记录")
	@SysLog("修改代金券")
	@PutMapping
	// @PreAuthorize("@pms.hasPermission('voucher_voucher_edit')")
	public R update(@RequestBody Voucher voucher) {
		return new R<>(voucherService.updateById(voucher));
	}

	/**
	 * 通过id删除一条记录
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除一条记录", notes = "通过id删除一条记录")
	@SysLog("删除代金券")
	@DeleteMapping("/{id}")
	// @PreAuthorize("@pms.hasPermission('voucher_voucher_del')")
	public boolean removeById(@PathVariable Integer id) {
		return voucherService.removeVoucherById(id);
	}

}
