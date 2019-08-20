package com.pig4cloud.pig.accountmanage.agent.dto;

import com.pig4cloud.pig.accountmanage.agent.entity.Agent;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>Title: AgentInfoDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月19日
 * @since 1.8
 */
@Data
@ApiModel("代理商明细实体")
public class AgentInfoDTO extends Agent {

	@ApiModelProperty("激活设备数")
	private int activeSalesMachines;

	@ApiModelProperty("待激活设备数")
	private int inactiveSalesMachines;

	@ApiModelProperty("激活发展代理商")
	private int activeDevelopAgents;

	@ApiModelProperty("待激活发展代理商")
	private int inactiveDevelopAgents;

	@ApiModelProperty("待激活发展代理商")
	private int userCount;
}
