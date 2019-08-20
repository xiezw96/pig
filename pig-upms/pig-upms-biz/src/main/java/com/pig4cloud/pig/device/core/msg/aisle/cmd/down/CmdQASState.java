package com.pig4cloud.pig.device.core.msg.aisle.cmd.down;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @title: CmdQASState
 * @projectName eden
 * @description: 查询货道电机状态
 */
@Setter
@Getter
@ToString
public class CmdQASState extends AisleComandMsg {

	//所有货道标识（查全部）
	public static final String  ASID_OF_ALL = "0";
	//货道编号
	String asId ;

	public CmdQASState(){
		super(TYPE_CMD_QASTATE);
	}

	public CmdQASState(String iotProto){
		super(TYPE_CMD_QASTATE,iotProto);
	}

}
