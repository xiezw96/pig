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
package com.pig4cloud.pig.accountmanage.agentDayReport.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.agentDayReport.entity.AgentDayReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 代理商日报表
 *
 * @author zhuzubin
 * @date 2019-04-05 22:48:31
 */
public interface AgentDayReportMapper extends BaseMapper<AgentDayReport> {

	IPage<AgentDayReport> getAgentDayReportPage(Page page, @Param("agentDayReport") AgentDayReport agentDayReport);

	List<AgentDayReport> getAgentTodayReport(@Param("date") String date);

	AgentDayReport getSumDayRport(@Param("agentDayReport") AgentDayReport agentDayReport);

}
