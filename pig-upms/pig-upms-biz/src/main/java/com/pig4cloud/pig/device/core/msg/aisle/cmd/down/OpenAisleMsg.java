package com.pig4cloud.pig.device.core.msg.aisle.cmd.down;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @title: OpenAisleMsg
 * @projectName pig
 * @description: TODO
 */
@Getter
@Setter
@ToString
public class OpenAisleMsg extends  AisleComandMsg{

	//货道编号
	String aisleId;


	public OpenAisleMsg(){
		super(TYPE_CMD_OPENAISLE);
	}

	public OpenAisleMsg(String iotProto){
		super(TYPE_CMD_OPENAISLE,iotProto);
	}

}
