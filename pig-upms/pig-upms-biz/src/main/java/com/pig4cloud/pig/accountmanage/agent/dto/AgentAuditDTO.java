package com.pig4cloud.pig.accountmanage.agent.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>Title: AgentAuditDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月09日
 * @since 1.8
 */
@Data
@ApiModel("代理商审核实体")
public class AgentAuditDTO {
	@ApiModelProperty("主键ID")
	private Integer agentId;
	@ApiModelProperty("等级")
	private String level;
	@ApiModelProperty("省")
	private String privince;
	@ApiModelProperty("市")
	private String city;
	@ApiModelProperty("区")
	private String area;
	@ApiModelProperty("审核状态， 0:待审核 1:通过 2:拒绝")
	private Integer auditStatus;
	@ApiModelProperty("备注")
	private String remark;
}
