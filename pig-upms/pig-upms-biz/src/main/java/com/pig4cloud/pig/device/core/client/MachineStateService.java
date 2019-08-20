package com.pig4cloud.pig.device.core.client;

import com.pig4cloud.pig.device.api.MachineInfo;
import com.pig4cloud.pig.device.api.SaleMachineHealth;
import com.pig4cloud.pig.device.core.msg.MachineMsgListener;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.up.ReqQASStateMsg;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.up.ReqQNetStateMsg;

/**
 * @title: MachineStateService
 * @projectName pig
 * @description: 设备状态相关服务
 */
public interface MachineStateService extends MachineMsgListener {

	/**
	 * 根据机器的编号获取机器基础信息
	 * @param machineCode
	 * @return
	 */
	public MachineInfo get(String machineCode);

	/**
	 * 获取机器最新的健康状况信息
	 * @param machineCode
	 * @param doRealTimeTest 是否执行指令检测
	 * @return
	 */
	public SaleMachineHealth getHealthState(String machineCode, boolean doRealTimeTest);

	/**
	 * 实时检查机器网络状况
	 * @param machineCode
	 * @return
	 */
	public ReqQNetStateMsg   checkMachineNetStateByRealTime(String machineCode);

	/**
	 * 实时检查机器的货道状况
	 * @param machineCode
	 * @param aisle ,参数为空，则查询机器所有货道信息
	 * @return
	 */
	public ReqQASStateMsg    checkMachineAisleStateByRealTime(String machineCode,String aisle);
}
