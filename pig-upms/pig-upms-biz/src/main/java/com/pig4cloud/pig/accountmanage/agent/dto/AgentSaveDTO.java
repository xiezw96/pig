package com.pig4cloud.pig.accountmanage.agent.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 代理商保存类
 * <p>Title: AgentSaveDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月13日
 * @since 1.8
 */
@Data
@ApiModel("代理商保存实体")
public class AgentSaveDTO extends AgentUpdateDTO {

	@ApiModelProperty("姓名")
	private String name;
	@ApiModelProperty("手机号")
	private String phone;
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
	@ApiModelProperty("推荐人，手机号或者推荐码")
	private String referrerCode;

	@ApiModelProperty(value = "审核申请日期", hidden = true)
	@JsonIgnore
	private LocalDateTime auditReqTime;

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private Integer auditStatus;
}
