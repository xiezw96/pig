package com.pig4cloud.pig.order.agentSettlement.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>Title: AgentSettlementSaveDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月22日
 * @since 1.8
 */
@Data
@ApiModel("结算申请保存实体")
public class AgentSettlementSaveDTO {
	@ApiModelProperty("交易密码")
	private String transactionPwd;
}
