package com.pig4cloud.pig.device.core.client;

import com.pig4cloud.pig.device.core.msg.MachineMsg;
import com.pig4cloud.pig.device.core.msg.MachineMsgListener;

/**
 * @title: MachineOpsClient
 * @projectName eden
 * @description: 机器设备客户端接口
 */
public interface MachineOpsClient{
	/**
	 * 发送下行指令
	 * @param msg
	 * @return
	 */
	public MachineMsg postCmd(MachineMsg msg);


	/**
	 * 发送下行指令
	 * @param msg
	 * @param retryTimes  重试次数
	 * @return
	 */
	public MachineMsg postCmd(MachineMsg msg ,int retryTimes);

	/**
	 * 异步发送机器指令
	 * @param msg
	 */
	public void  asynPostCmd(MachineMsg msg);

	/**
	 * 添加设备反馈监听
	 * @param msgCode 消息类型
	 * @param machineListener 监听器
	 */
	public void addMachineListener(String msgCode,MachineMsgListener machineListener);

}
