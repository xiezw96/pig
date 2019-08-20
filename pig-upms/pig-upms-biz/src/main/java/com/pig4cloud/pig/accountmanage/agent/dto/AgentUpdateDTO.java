package com.pig4cloud.pig.accountmanage.agent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>Title: AgentUpdateDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月19日
 * @since 1.8
 */
@Data
@ApiModel("代理商编辑实体")
public class AgentUpdateDTO {
	@ApiModelProperty("主键ID")
	private Integer agentId;
	@ApiModelProperty("等级")
	private String level;
	@ApiModelProperty("性别")
	private Integer sex;
	@ApiModelProperty("生日")
	private LocalDateTime birthday;
	@ApiModelProperty("手机号")
	private String phone;
	@ApiModelProperty("省")
	private String privince;
	@ApiModelProperty("市")
	private String city;
	@ApiModelProperty("区")
	private String area;
	@ApiModelProperty("公司")
	private String company;
	@ApiModelProperty("地址")
	private String address;
	@ApiModelProperty("微信号")
	private String wechatId;
	@ApiModelProperty("密码")
	private String pwd;
	@ApiModelProperty("激活状态 0 未激活 1 已激活 2 冻结")
	private Integer activeStatus;

}
