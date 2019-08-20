package com.pig4cloud.pig.device.core.msg.aisle.cmd.up;

import com.pig4cloud.pig.device.core.msg.MqttMsgContext;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.part.SimPart;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @title: ReqQNetStateMsg
 * @projectName eden
 * @description: 应答查询网络状态
 */
@Setter
@Getter
@ToString
public class ReqQNetStateMsg extends AisleMachineReq{

	SimPart sim;

	public ReqQNetStateMsg(){
		super(TYPE_CMD_R_NETSTATE);
		this.setCtx(new MqttMsgContext());
	}

	public ReqQNetStateMsg(String iotProto){
		super(TYPE_CMD_R_NETSTATE,iotProto);
	}

}
