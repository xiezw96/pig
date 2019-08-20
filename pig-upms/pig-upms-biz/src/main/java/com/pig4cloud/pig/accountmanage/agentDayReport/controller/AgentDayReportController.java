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
package com.pig4cloud.pig.accountmanage.agentDayReport.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.agentDayReport.entity.AgentDayReport;
import com.pig4cloud.pig.accountmanage.agentDayReport.service.AgentDayReportService;
import com.pig4cloud.pig.common.core.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 代理商管理
 *
 * @author zhuzubin
 * @date 2019-04-05 22:48:31
 */
@Api(value = "agentdayreport", tags = "代理商日报表")
@RestController
@AllArgsConstructor
@RequestMapping("/agentdayreport")
public class AgentDayReportController {

	private final AgentDayReportService agentDayReportService;

	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<AgentDayReport>> getAgentDayReportPage(Page<AgentDayReport> page, AgentDayReport agentDayReport) {
		return new R<>(agentDayReportService.getAgentDayReportPage(page, agentDayReport));
	}

	@ApiOperation(value = "总和", notes = "总和")
	@GetMapping("/sum")
	public R<AgentDayReport> getSumDayRport(AgentDayReport agentDayReport) {
		return new R<>(agentDayReportService.getSumDayRport(agentDayReport));
	}

}
