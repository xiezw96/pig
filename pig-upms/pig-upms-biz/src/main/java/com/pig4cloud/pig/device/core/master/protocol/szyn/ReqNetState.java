package com.pig4cloud.pig.device.core.master.protocol.szyn;

import com.pig4cloud.pig.device.core.msg.MqttMsgContext;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.up.AisleMachineReq;

/**
 * @title: ReqNetState
 * @projectName eden
 * @description: 应答查询网络状态
 */
public class ReqNetState extends AisleMachineReq {

	public ReqNetState(){
		super(TYPE_CMD_R_NETSTATE);
		this.setCtx(new MqttMsgContext());
	}
}
