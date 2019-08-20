package com.pig4cloud.pig.device.core.msg.aisle.cmd.down;

import com.pig4cloud.pig.device.core.msg.MachineMsg;

/**
 * @title: AisleComandMsg
 * @projectName eden
 * @description: 货道式机器下放指令
 */
public class AisleComandMsg extends MachineMsg {
	//出货指令
	public static final String TYPE_CMD_LAYGOODS = "cmd_laygood";
	//查询货道状态指令
	public static final String TYPE_CMD_QASTATE = "cmd_qastate";
	//补货复位指令
	public static final String TYPE_CMD_MERESET = "cmd_mereset";
	//订单状态查询指令
	public static final String TYPE_CMD_ODSTATE = "cmd_odstate";
	//网络状况查询指令
	public static final String TYPE_CMD_NETSTATE = "cmd_netstate";
	//打开货道门
	public static final String TYPE_CMD_OPENAISLE= "cmd_openaisle";

	public AisleComandMsg(String cmdType){
		super.operCode = cmdType;
	}

	public AisleComandMsg(String cmdType,String iotProto){
		super.operCode = cmdType;
		initContext(iotProto);
	}

}
