package com.pig4cloud.pig.accountmanage.agent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>Title: AgentTransactionPwdDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月22日
 * @since 1.8
 */
@Data
@ApiModel("交易密码修改保存实体")
public class AgentTransactionPwdDTO {
	@ApiModelProperty("交易密码")
	private String transactionPwd;
	@ApiModelProperty("短信验证码令牌")
	private String smsToken;
}
