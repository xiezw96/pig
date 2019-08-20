package com.pig4cloud.pig.device.core.msg.aisle.cmd.up;

import com.pig4cloud.pig.device.core.msg.MqttMsgContext;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.part.AsPart;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @title: ReqQASStateMsg
 * @projectName eden
 * @description: 应答查询货道状态
 */
@Getter
@Setter
@ToString
public class ReqQASStateMsg extends AisleMachineReq{

	//货道信息列表
	public List<AsPart> parts = new ArrayList<>();

	public ReqQASStateMsg(){
		super(TYPE_CMD_R_QASTATE);
		this.setCtx(new MqttMsgContext());
	}

	public ReqQASStateMsg(String iotProto){
		super(TYPE_CMD_R_QASTATE,iotProto);
	}

	public void addPart(AsPart p){
		this.parts.add(p);
	}

}
