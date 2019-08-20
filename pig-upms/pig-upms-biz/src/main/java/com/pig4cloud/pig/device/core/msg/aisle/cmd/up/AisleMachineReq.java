package com.pig4cloud.pig.device.core.msg.aisle.cmd.up;

import com.pig4cloud.pig.device.core.msg.MachineMsg;

/**
 * @title: AisleMachineReq
 * @projectName eden
 * @description: 货道式机器请求
 */
public class AisleMachineReq extends MachineMsg {

	//应答出货指令
	public static final String TYPE_CMD_R_LAYGOODS = "cmd_r_laygood";
	//应答查询货道状态指令
	public static final String TYPE_CMD_R_QASTATE = "cmd_r_qastate";
	//应答补货复位指令
	public static final String TYPE_CMD_R_MERESET = "cmd_r_mereset";
	//应答订单状态查询指令
	public static final String TYPE_CMD_R_ODSTATE = "cmd_r_odstate";
	//应答网络状况查询指令
	public static final String TYPE_CMD_R_NETSTATE = "cmd_r_netstate";
	//应答打开货道门指令
	public static final String TYPE_CMD_R_OPENAISLE = "cmd_r_openaisle";


	public AisleMachineReq(String cmdType){
		super.operCode = cmdType;
	}

	public AisleMachineReq(String cmdType,String iotProto){
		super.operCode = cmdType;
		initContext(iotProto);
	}
}
