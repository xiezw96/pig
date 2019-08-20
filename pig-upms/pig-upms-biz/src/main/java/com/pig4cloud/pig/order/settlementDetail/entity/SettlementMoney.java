package com.pig4cloud.pig.order.settlementDetail.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Title: SettlementMoney</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年07月11日
 * @since 1.8
 */
@ApiModel("可结算金额实体")
@Data
public class SettlementMoney {
	@ApiModelProperty("结算到账金额")
	private BigDecimal settlementPrice;
	@ApiModelProperty("结算金额")
	private BigDecimal originalSettlementPrice;
	@ApiModelProperty("通道费")
	private BigDecimal commission;
}
