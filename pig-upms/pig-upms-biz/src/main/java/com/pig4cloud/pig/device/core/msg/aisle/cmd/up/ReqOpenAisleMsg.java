package com.pig4cloud.pig.device.core.msg.aisle.cmd.up;

import com.pig4cloud.pig.device.core.msg.MqttMsgContext;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @title: ReqOpenAisleMsg
 * @projectName pig
 * @description: TODO
 */
@Getter
@Setter
@ToString
public class ReqOpenAisleMsg extends AisleMachineReq{
	//成功开门
	public static final short RESULT_OPEN_DOOR_SUCESS = 1;
	//货道门打开情况
	Integer result;

	public ReqOpenAisleMsg(){
		super(TYPE_CMD_R_OPENAISLE);
		this.setCtx(new MqttMsgContext());
	}

	public ReqOpenAisleMsg(String iotProto){
		super(TYPE_CMD_R_OPENAISLE,iotProto);
	}

}
