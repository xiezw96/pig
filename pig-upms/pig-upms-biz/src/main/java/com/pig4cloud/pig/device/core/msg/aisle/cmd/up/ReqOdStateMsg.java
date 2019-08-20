package com.pig4cloud.pig.device.core.msg.aisle.cmd.up;

import com.pig4cloud.pig.device.core.msg.MqttMsgContext;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @title: ReqOdStateMsg
 * @projectName eden
 * @description: TODO
 */
@Setter
@Getter
@ToString
public class ReqOdStateMsg extends AisleMachineReq{

	//订单是否已存在
	public boolean isOdExisted;

	public ReqOdStateMsg(){
		super(TYPE_CMD_R_ODSTATE);
		this.setCtx(new MqttMsgContext());
	}

	public ReqOdStateMsg(String iotProto){
		super(TYPE_CMD_R_ODSTATE,iotProto);
	}


}
