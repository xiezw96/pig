package com.pig4cloud.pig.quartz.tasks;

import com.pig4cloud.pig.order.settlementDetail.service.SettlementDetailService;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 清算订单可结算明细
 * <p>Title: ScheduledTasksConfigurer</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月16日
 * @since 1.8
 */
@Slf4j
public class OrderSettlementDetailJob implements Job {
	@Autowired
	private SettlementDetailService detailService;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		long start = System.currentTimeMillis();
		log.info("==========================开始清算代理商可结算明细==========================");
		Integer num = detailService.executeOrderSettlement();
		long end = System.currentTimeMillis();
		log.info("结算明细总数：{} ，耗时：{} 秒", num, new BigDecimal((end - start) / 1000).setScale(2, RoundingMode.HALF_UP).doubleValue());
		log.info("==========================结束清算代理商可结算明细==========================");
	}
}
