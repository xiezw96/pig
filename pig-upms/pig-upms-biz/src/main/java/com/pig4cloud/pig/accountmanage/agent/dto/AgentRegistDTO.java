package com.pig4cloud.pig.accountmanage.agent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>Title: AgentRegistDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月19日
 * @since 1.8
 */
@Data
@ApiModel("代理商注册实体")
public class AgentRegistDTO {
	@ApiModelProperty("注册手机号")
	private String phone;
	@ApiModelProperty("密码")
	private String pwd;
	@ApiModelProperty("推荐人，手机号或者推荐码")
	private String referrerCode;
	@ApiModelProperty("短信验证码令牌")
	private String smsToken;
}
