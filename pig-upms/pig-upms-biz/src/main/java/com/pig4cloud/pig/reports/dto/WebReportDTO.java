package com.pig4cloud.pig.reports.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * <p>Title: WebReportDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月15日
 * @since 1.8
 */
@Data
@Builder
@ApiModel("总部Web数据概况")
public class WebReportDTO {
	@ApiModelProperty("今日线下流水")
	private double todayOfflineSaleStatement;
	@ApiModelProperty("今日线上流水")
	private double todayOnlineSaleStatement;
	@ApiModelProperty("昨日线下流水")
	private double yesterdayOfflineSaleStatement;
	@ApiModelProperty("昨日线上流水")
	private double yesterdayOnlineSaleStatement;

	@ApiModelProperty("今日线下订单")
	private int todayOfflineOrder;
	@ApiModelProperty("今日线上订单")
	private int todayOnlineOrder;
	@ApiModelProperty("昨日线下订单")
	private int yesterdayOfflineOrder;
	@ApiModelProperty("昨日线上订单")
	private int yesterdayOnlineOrder;

	@ApiModelProperty("今日线下用户")
	private int todayOfflineUser;
	@ApiModelProperty("今日线上用户")
	private int todayOnlineUser;
	@ApiModelProperty("昨日线下用户")
	private int yesterdayOfflineUser;
	@ApiModelProperty("昨日线上用户")
	private int yesterdayOnlineUser;

	@ApiModelProperty("今日线下代理商")
	private int todayOfflineAgent;
	@ApiModelProperty("今日线上代理商")
	private int todayOnlineAgent;
	@ApiModelProperty("昨日线下代理商")
	private int yesterdayOfflineAgent;
	@ApiModelProperty("昨日线上代理商")
	private int yesterdayOnlineAgent;

	@ApiModelProperty("代理商排行榜")
	private List<AgentReport> agentReports;

	@ApiModelProperty("商品销售排行")
	private List<AgentReport> goodsSaleReports;

	@ApiModelProperty("商品浏览排行")
	private List<AgentReport> goodsBrowseReports;


}
