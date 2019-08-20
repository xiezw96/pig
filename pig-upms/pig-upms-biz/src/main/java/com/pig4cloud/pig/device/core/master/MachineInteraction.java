package com.pig4cloud.pig.device.core.master;

import com.pig4cloud.pig.device.core.msg.MachineMsg;
import com.pig4cloud.pig.device.core.msg.MachineMsgListener;
import com.pig4cloud.pig.device.core.msg.MsgContext;

/**
 * @title: MachineInteraction
 * @projectName eden
 * @description: TODO
 */
public interface MachineInteraction {

	//状态-初始化启动中
	public static  final short  STATUS_INITING = 0 ;
	//状态-执行中，可正常处理
	public static  final short  STATUS_RUNNING = 1 ;
	//状态-停止中
	public static  final short  STATUS_STOPPING = 2 ;
	//状态-已停止
	public static  final short  STATUS_STOPPED  = 3 ;

	public void init();

	public void release();

	/**
	 * 发送指令
	 * @param req
	 */
	public void asynSend(MachineMsg req);

	/**
	 * 接收机器请求
	 * @param rMsg
	 */
	public void asynRecv(MachineMsg rMsg);

	/**
	 * 添加设备反馈监听
	 * @param machineListener
	 */
	public void addMachineListener(MachineMsgListener machineListener);

	/**
	 * 获取状态信息
	 * @return
	 */
	public short getStatus();

	/**
	 * 状态检查
	 * @param status
	 * @return
	 */
	public boolean checkStatus(short status);

}
