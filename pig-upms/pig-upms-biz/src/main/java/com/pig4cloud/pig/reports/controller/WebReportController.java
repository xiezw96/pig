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
package com.pig4cloud.pig.reports.controller;

import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.order.order.service.OrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;


/**
 * 总部报表
 *
 * @author zhuzubin
 * @date 2019-04-05 23:06:00
 */
@Api(value = "webreport", tags = "总部Web报表查询服务")
@RestController
@AllArgsConstructor
@RequestMapping("/web")
public class WebReportController {

	private final OrderService orderService;

	@PostMapping("/data_report")
	@ApiOperation(value = "数据概况报表", notes = "数据概况报表")
	public R getDataReport() {
		return null;
	}


	@PostMapping("/goods_report")
	@ApiOperation(value = "商品概况报表", notes = "商品概况报表")
	public R getGoodsReport(@RequestParam(value = "nameOrCode", required = false)
							@ApiParam("商品名称或编码") String nameOrCode,
							@RequestParam(value = "startTime", required = false)
							@ApiParam("开始时间") Date startTime,
							@RequestParam(value = "endTime", required = false)
							@ApiParam("结束时间") Date endTime) {
		return null;
	}

	@PostMapping("/agent_detail_report")
	@ApiOperation(value = "代理商数据报表", notes = "代理商数据报表")
	public R getAgentDetailReport(@RequestParam(value = "agentId", required = false)
								  @ApiParam("代理商") Long agentId,
								  @RequestParam(value = "startTime", required = false)
								  @ApiParam("开始时间") Date startTime,
								  @RequestParam(value = "endTime", required = false)
								  @ApiParam("结束时间") Date endTime) {
		return null;
	}
}
