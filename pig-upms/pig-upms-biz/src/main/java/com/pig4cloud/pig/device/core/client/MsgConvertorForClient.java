package com.pig4cloud.pig.device.core.client;

import com.pig4cloud.pig.device.api.MachineInfo;
import com.pig4cloud.pig.device.core.msg.MachineMsg;

/**
 * @title: MsgConvertorForClient
 * @projectName pig
 * @description: TODO
 */
public class MsgConvertorForClient {

	/**
	 * 消息回填MachineMsg
	 * @param info
	 * @param msg
	 */
	public static void toCmdMsg(MachineInfo info, MachineMsg msg) {
		if(info != null) {
			msg.initContext(info.getIotProto());
			msg.setMachineCode(info.getCode());
			msg.getCtx().setMachineSerial(String.valueOf(info.getSpecId()));
			msg.getCtx().setManufactoryId(info.getSpecProvider());
		}
	}
}
