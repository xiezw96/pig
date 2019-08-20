package com.pig4cloud.pig.accountmanage.agent.entity;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
public class AgentChain {
	private AgentChain parent;
	private Agent agent;

	public List<Integer> toIdList() {
		List<Agent> list = toList();
		if (list != null && !list.isEmpty()) {
			return list.stream().map(agent -> agent.getAgentId()).collect(Collectors.toList());
		}
		return null;
	}

	public List<Agent> toList() {
		if (agent != null) {
			List<Agent> agentList = new ArrayList<>();
			agentList.add(agent);
			return toList(agentList, parent);
		}
		return null;
	}

	private List<Agent> toList(List<Agent> agent, AgentChain curr) {
		if (curr != null && curr.getAgent() != null) {
			agent.add(curr.getAgent());
			return toList(agent, curr.getParent());
		}
		return agent;
	}
}
