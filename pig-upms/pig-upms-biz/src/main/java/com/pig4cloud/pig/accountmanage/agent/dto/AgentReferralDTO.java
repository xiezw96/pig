package com.pig4cloud.pig.accountmanage.agent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>Title: AgentReferralDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年05月07日
 * @since 1.8
 */
@Data
@ApiModel("代理商推荐申请保存实体")
public class AgentReferralDTO {
	@ApiModelProperty("姓名")
	private String name;
	@ApiModelProperty("手机号")
	private String phone;
	@ApiModelProperty("微信号")
	private String wechatId;
	@ApiModelProperty("身份证号")
	private String idCard;
	@ApiModelProperty("身份证正面")
	private String identityCardFront;
	@ApiModelProperty("身份证反面")
	private String identityCardVerso;
	@ApiModelProperty("银行卡正面")
	private String bankCardFront;
	@ApiModelProperty("银行卡反面")
	private String bankCardVerso;
}
