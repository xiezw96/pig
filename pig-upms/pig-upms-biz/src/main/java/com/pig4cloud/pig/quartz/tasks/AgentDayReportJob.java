package com.pig4cloud.pig.quartz.tasks;

import com.pig4cloud.pig.accountmanage.agentDayReport.service.AgentDayReportService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 代理商日报表定时任务
 * <p>Title: AgentDayReportJob</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年07月06日
 * @since 1.8
 */
@Slf4j
public class AgentDayReportJob implements Job {

	@Autowired
	private AgentDayReportService agentDayReportService;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		long start = System.currentTimeMillis();
		log.info("==========================开始统计代理商日报表==========================");
		agentDayReportService.executeAgentDayReport();
		long end = System.currentTimeMillis();
		log.info("统计代理商日报表：耗时：{} 秒", new BigDecimal((end - start) / 1000).setScale(2, RoundingMode.HALF_UP).doubleValue());
		log.info("==========================结束统计代理商日报表==========================");
	}
}
