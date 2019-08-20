package com.pig4cloud.pig.device.core.client.shadow;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pig.device.api.AisleMachineOperException;
import com.pig4cloud.pig.device.api.MachineInfo;
import com.pig4cloud.pig.device.api.SaleMachineHealth;
import com.pig4cloud.pig.device.core.client.MachineOpsClient;
import com.pig4cloud.pig.device.core.client.MsgConvertorForClient;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.down.AisleComandMsg;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.down.CmdQASState;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.part.AsPart;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.part.SimPart;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.up.ReqQASStateMsg;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.up.ReqQNetStateMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @title: MachineHealthRealTimeService
 * @projectName pig
 * @description: TODO
 */
@Component
public class MachineHealthRealTimeService {

	@Autowired
	MachineOpsClient machineOpsClient;

	@Value("${device.client.aisle.retrytimes:1}")
	int retryTimes  = 1;

	/**
	 * 通过发送实时指令的方式检查设备网络状况
	 * @param m
	 * @return
	 */
	public ReqQNetStateMsg checkMachineNetStateByRealTime(MachineInfo m) {
		if(m == null) return null;
		AisleComandMsg msg = new AisleComandMsg(AisleComandMsg.TYPE_CMD_NETSTATE);
		MsgConvertorForClient.toCmdMsg(m, msg);

		ReqQNetStateMsg rMsg = (ReqQNetStateMsg)machineOpsClient.postCmd(msg, retryTimes);
		if(rMsg == null)
			throw new RuntimeException("发送检查机器网络状态指令失败！");

		return rMsg;
	}

	/**
	 * 通过发送指令的方式检查设备货道状态信息
	 * @param m
	 * @param aisle
	 * @return
	 */
	public ReqQASStateMsg checkMachineAisleStateByRealTime(MachineInfo m, String aisle) {
		if(m == null) return null;
		CmdQASState msg = new CmdQASState();
		MsgConvertorForClient.toCmdMsg(m, msg);
		msg.setAsId(StrUtil.isBlank(aisle) ? CmdQASState.ASID_OF_ALL : aisle);

		ReqQASStateMsg rMsg = (ReqQASStateMsg) machineOpsClient.postCmd(msg, retryTimes);
		if(rMsg == null || CollUtil.isEmpty(rMsg.getParts())) {
			throw new AisleMachineOperException("指令测试货道状态失败！");
		}
		return rMsg;
	}

	/**
	 * 检查设备健康状态
	 * @param m
	 * @return
	 */
	public SaleMachineHealth checkMachineHealthByRealTime(MachineInfo m){
		if(m == null) return null;
		SaleMachineHealth smh = new SaleMachineHealth();
		smh.setMachineCode(m.getCode());
		//目前健康状况检查项: 1、网络状况 ,2、主机货道状态
		//触发：货道状态检查
		ReqQASStateMsg qaRMsg = checkMachineAisleStateByRealTime(m,null);
		updateQAState(smh,qaRMsg);
		//触发：网络状态检查
		ReqQNetStateMsg netRmsg = checkMachineNetStateByRealTime(m);
		updateNetState(smh,netRmsg);
		//更新时间戳
		smh.updateTimeStamp();
		return smh;
	}

	/**
	 * 更新货道状态信息
	 * @param smh
	 * @param msg
	 */
	public void updateQAState(SaleMachineHealth smh ,ReqQASStateMsg msg) {
		if(smh == null || msg == null) return ;
		for(AsPart ap : msg.getParts()){
			smh.getAislesStatus().put(ap.getId(),ap.getCmState());
		}
	}

	/**
	 * 更新设备网络状态信息
	 * @param smh
	 * @param msg
	 */
	public void updateNetState(SaleMachineHealth smh,ReqQNetStateMsg msg){
		if(smh == null || msg == null) return ;
		SimPart sim = msg.getSim();
		smh.setIccid(sim.getIccId());
		smh.setSignalStrenth(sim.getRssi());
	}

}
