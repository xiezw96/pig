package com.pig4cloud.pig.device.core.msg;

/**
 * @title: MachineMsgListener
 * @projectName eden
 * @description: TODO
 */
public interface MachineMsgListener {
	//监听所有消息监听器
	public static final String LISTEN_ALL_MSG_CODE = "all_code";

	public void  receiveData(MachineMsg msg);
}
