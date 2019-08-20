package com.pig4cloud.pig.reports.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * <p>Title: AgentAppReportDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月16日
 * @since 1.8
 */
@Data
@Builder
@ApiModel("代理商App首页报表")
public class AgentAppReportDTO {
	@ApiModelProperty("今日流水")
	private double todaySaleStatement;
	@ApiModelProperty("昨日流水")
	private double yesterdaySaleStatement;

	@ApiModelProperty("今日订单数")
	private int todayOrderNum;
	@ApiModelProperty("昨日订单数")
	private int yesterdayOrderNum;

	@ApiModelProperty("资金总额")
	private double sumMoney;
	@ApiModelProperty("可结算金额")
	private double settlementMoney;
}
