package com.pig4cloud.pig.reports.dto;

import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * <p>Title: AgentReport</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月15日
 * @since 1.8
 */
@Data
@Builder
@ApiModel("代理商排行榜数据")
public class AgentReport {
	@ApiModelProperty("代理商")
	private Agent agent;
	@ApiModelProperty("今日线下流水")
	private int todayOfflineSaleStatement;
	@ApiModelProperty("今日线上流水")
	private int todayOnlineSaleStatement;
	@ApiModelProperty("总用户数")
	private int sumUser;
	@ApiModelProperty("总发展数")
	private int sumDevelop;
	@ApiModelProperty("已提现金额")
	private int extractMoney;
	@ApiModelProperty("可提现金额")
	private int depositMoney;

}
