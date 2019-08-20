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
@ApiModel("修改密码保存实体")
public class AgentResetPwdDTO {
	@ApiModelProperty("密码")
	private String phone;
	@ApiModelProperty("密码")
	private String pwd;
	@ApiModelProperty("短信验证码令牌")
	private String smsToken;
}
