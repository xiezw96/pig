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
package com.pig4cloud.pig.accountmanage.agentDayReport.service.impl;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.pig4cloud.pig.accountmanage.agentDayReport.entity.AgentDayReport;
import com.pig4cloud.pig.accountmanage.agentDayReport.mapper.AgentDayReportMapper;
import com.pig4cloud.pig.accountmanage.agentDayReport.service.AgentDayReportService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 代理商管理
 *
 * @author zhuzubin
 * @date 2019-04-05 22:48:31
 */
@Service("agentDayReportService")
@AllArgsConstructor
public class AgentDayReportServiceImpl extends ServiceImpl<AgentDayReportMapper, AgentDayReport> implements AgentDayReportService {

	@Override
	public IPage<AgentDayReport> getAgentDayReportPage(Page<AgentDayReport> page, AgentDayReport agentDayReport) {
		return baseMapper.getAgentDayReportPage(page, agentDayReport);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean executeAgentDayReport() {
		LocalDateTime now = LocalDateTime.now();
		List<AgentDayReport> data = baseMapper.getAgentTodayReport(DateUtil.format(new Date(), "yyyy-MM-dd"));
		return saveBatch(data.stream().map(d -> {
			d.setCreateDate(now);
			return d;
		}).collect(Collectors.toList()));
	}

	@Override
	public AgentDayReport getSumDayRport(AgentDayReport agentDayReport) {
		return baseMapper.getSumDayRport(agentDayReport);
	}
}
