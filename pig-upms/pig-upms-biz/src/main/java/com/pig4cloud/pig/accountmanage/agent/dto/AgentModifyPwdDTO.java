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
@ApiModel("管理员修改代理商密码实体")
public class AgentModifyPwdDTO {
	@ApiModelProperty("代理商id")
	private String agentId;
	@ApiModelProperty("密码")
	private String pwd;
}
