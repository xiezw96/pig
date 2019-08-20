package com.pig4cloud.pig.quartz.config;

import com.pig4cloud.pig.quartz.tasks.AgentDayReportJob;
import com.pig4cloud.pig.quartz.tasks.OrderSettlementDetailJob;
import com.pig4cloud.pig.quartz.tasks.SalesMachineStatusMonitorJob;
import com.pig4cloud.pig.quartz.tasks.TaskGroup;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobDetail;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.JobDetailImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: SchedledConfigurer</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月16日
 * @since 1.8
 */
@Configuration
public class QuartzAutoConfigurer {

	@Bean
	public SchedulerFactoryBeanCustomizer pigSchedulerFactoryBeanCustomizer() {
		return (factoryBean) -> {
			// 延时启动，应用启动1秒后
			factoryBean.setStartupDelay(1);
			// 用于quartz集群,QuartzScheduler 启动时更新己存在的Job
			factoryBean.setOverwriteExistingJobs(true);
		};
	}

	@Configuration
	static class OrderTasks {
		private String GROUP_KEY = TaskGroup.ORDER_KEY;
		private String ORDER_SETTLEMENT_DETAIL_JOB_NAME = OrderSettlementDetailJob.class.getName();

		@Bean
		public JobDetail orderSettlementDetailJob() {
			JobDetailImpl jobDetail = new JobDetailImpl();
			jobDetail.setJobClass(OrderSettlementDetailJob.class);
			jobDetail.setDurability(true);
			// 设置任务的名字
			jobDetail.setName(ORDER_SETTLEMENT_DETAIL_JOB_NAME);
			// 设置任务的分组，在多任务的时候使用
			jobDetail.setGroup(GROUP_KEY);
			return jobDetail;
		}

		@Bean
		public Trigger orderSettlementDetailTrigger(@Value("${task.cron.orderSettlementDetail:0 0 1 * * ?}") String cron) {
			return TriggerBuilder.newTrigger().withIdentity(ORDER_SETTLEMENT_DETAIL_JOB_NAME, GROUP_KEY).withSchedule(CronScheduleBuilder.cronSchedule(cron)).forJob(ORDER_SETTLEMENT_DETAIL_JOB_NAME, GROUP_KEY).build();
		}
	}

	@Configuration
	static class SalesMachineStatusMonitorTasks {
		private String GROUP_KEY = TaskGroup.SALES_MACHINE_KEY;
		private String SALES_MACHINE_STATUS_MONITOR_JOB_NAME = SalesMachineStatusMonitorJob.class.getName();

		@Bean
		public JobDetail salesMachineStatusMonitorJob() {
			JobDetailImpl jobDetail = new JobDetailImpl();
			jobDetail.setJobClass(SalesMachineStatusMonitorJob.class);
			jobDetail.setDurability(true);
			// 设置任务的名字
			jobDetail.setName(SALES_MACHINE_STATUS_MONITOR_JOB_NAME);
			// 设置任务的分组，在多任务的时候使用
			jobDetail.setGroup(GROUP_KEY);
			return jobDetail;
		}

		@Bean
		public Trigger salesMachineStatusMonitorTrigger(@Value("${task.cron.salesmachinestatusmonitor:0 0 1 * * ?}") String cron) {
			return TriggerBuilder.newTrigger().withIdentity(SALES_MACHINE_STATUS_MONITOR_JOB_NAME, GROUP_KEY).withSchedule(CronScheduleBuilder.cronSchedule(cron)).forJob(SALES_MACHINE_STATUS_MONITOR_JOB_NAME, GROUP_KEY).build();
		}
	}

	@Configuration
	static class AgentDayReportJobTasks {
		private String GROUP_KEY = TaskGroup.AGENT_DAY_REPORT_KEY;
		private String AGENT_DAY_REPORT_JOB_NAME = AgentDayReportJob.class.getName();

		@Bean
		public JobDetail agentDayReportJob() {
			JobDetailImpl jobDetail = new JobDetailImpl();
			jobDetail.setJobClass(AgentDayReportJob.class);
			jobDetail.setDurability(true);
			// 设置任务的名字
			jobDetail.setName(AGENT_DAY_REPORT_JOB_NAME);
			// 设置任务的分组，在多任务的时候使用
			jobDetail.setGroup(GROUP_KEY);
			return jobDetail;
		}

		@Bean
		public Trigger agentDayReportTrigger(@Value("${task.cron.agentdayreport:0 0 1 * * ?}") String cron) {
			return TriggerBuilder.newTrigger().withIdentity(AGENT_DAY_REPORT_JOB_NAME, GROUP_KEY).withSchedule(CronScheduleBuilder.cronSchedule(cron)).forJob(AGENT_DAY_REPORT_JOB_NAME, GROUP_KEY).build();
		}
	}
}
