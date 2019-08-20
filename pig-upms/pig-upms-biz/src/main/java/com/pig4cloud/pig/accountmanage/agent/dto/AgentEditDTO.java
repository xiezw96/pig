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
public class AgentEditDTO {
	@ApiModelProperty("头像")
	private String photo;
	@ApiModelProperty("性别")
	private Integer sex;
	@ApiModelProperty("生日")
	private String birthday;
//	@ApiModelProperty("地址")
//	private String address;

}
