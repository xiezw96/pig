package com.pig4cloud.pig.device.core.master;

import com.pig4cloud.pig.device.core.msg.MachineMsg;
import com.pig4cloud.pig.device.core.msg.MsgContext;

/**
 * @title: BrokerAgent
 * @projectName eden
 * @description: TODO
 */
public interface BrokerAgent extends Comparable<Short>{

	public void init(MachineInteraction machineInteraction);

	public void release();

	/**
	 * 机器请求接收
	 * @param msg
	 */
	public void sendMsg(MachineMsg msg);

	/**
	 * 匹配请求消息
	 * @param ctx
	 */
	public boolean match(MsgContext ctx);

	/**
	 * 获取匹配优先级
	 * @return
	 */
	public short getPriority();

	default int compareTo(Short o) {
		if(o == null) return 1 ;
		return getPriority() - o.shortValue();
	}

}
