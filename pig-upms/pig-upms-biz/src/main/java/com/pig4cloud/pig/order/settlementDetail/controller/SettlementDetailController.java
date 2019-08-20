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
package com.pig4cloud.pig.order.settlementDetail.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.order.settlementDetail.entity.SettlementDetail;
import com.pig4cloud.pig.order.settlementDetail.service.SettlementDetailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 结算单明细
 *
 * @author zhuzubin
 * @date 2019-04-05 23:21:42
 */
@Api(value = "settlementdetail", tags = "结算明细管理")
@RestController
@AllArgsConstructor
@RequestMapping("/settlementdetail")
public class SettlementDetailController {

	private final SettlementDetailService settlementDetailService;

	/**
	 * 简单分页查询
	 *
	 * @param page             分页对象
	 * @param settlementDetail 结算单明细
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<SettlementDetail>> getSettlementDetailPage(Page<SettlementDetail> page, SettlementDetail settlementDetail) {
		return new R<>(settlementDetailService.getSettlementDetailPage(page, settlementDetail));
	}

	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
	@GetMapping("/{id}")
	public R<SettlementDetail> getById(@PathVariable("id") Integer id) {
		return new R<>(settlementDetailService.getById(id));
	}

	@GetMapping("/sum")
	@ApiOperation("查询代理商可结算金额")
	public R getSettlementDetailSum() {
		return new R(settlementDetailService.getAgentSettlementMoney(SecurityUtils.getUser().getId()));
	}

}
