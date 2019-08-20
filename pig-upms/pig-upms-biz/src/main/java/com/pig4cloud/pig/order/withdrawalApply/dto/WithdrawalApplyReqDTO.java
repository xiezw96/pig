package com.pig4cloud.pig.order.withdrawalApply.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Title: WithdrawalApplyReqDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月28日
 * @since 1.8
 */
@ApiModel("代理商提现申请实体")
@Data
public class WithdrawalApplyReqDTO {
	@ApiModelProperty("交易密码")
	private String transactionPwd;
	@ApiModelProperty("本次提现金额")
	private BigDecimal reqMoney;
}
