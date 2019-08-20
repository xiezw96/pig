package com.pig4cloud.pig.accountmanage.agent.entity;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>Title: AgentChain</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月18日
 * @since 1.8
 */
@Data
@Builder
public class AgentReverseChain {
	private List<AgentReverseChain> children;
	private Agent agent;

	public List<Integer> toIdList() {
		return toIdList(new ArrayList<>(), this);
	}

	private List<Integer> toIdList(List<Integer> idList, AgentReverseChain agent) {
		if (agent != null && agent.getAgent() != null) {
			idList.add(agent.getAgent().getAgentId());
			if (agent.getChildren() != null) {
				agent.getChildren().stream().forEach(c -> toIdList(idList, c));
			}
		}
		return idList;
	}
}
