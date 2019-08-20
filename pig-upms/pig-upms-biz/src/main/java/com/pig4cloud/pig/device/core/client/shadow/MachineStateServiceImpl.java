package com.pig4cloud.pig.device.core.client.shadow;

import com.pig4cloud.pig.device.api.MachineInfo;
import com.pig4cloud.pig.device.api.SaleMachineHealth;
import com.pig4cloud.pig.device.core.client.MachineStateService;
import com.pig4cloud.pig.device.core.msg.MachineMsg;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.up.ReqQASStateMsg;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.up.ReqQNetStateMsg;
import lombok.extern.java.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @title: MachineStateService
 * @projectName pig
 * @description:
 */
@Component
@Log
public class MachineStateServiceImpl implements MachineStateService, InitializingBean {

	@Autowired
	MachineHealthRealTimeService  machineHealthRealTimeService;

	@Autowired
	MachineStateRedisBuff machineStateRedisBuff;

	public MachineInfo get(String machineCode) {
		return  machineStateRedisBuff.getMachineInfo(machineCode);
	}

	@Override
	public SaleMachineHealth getHealthState(String machineCode, boolean doRealTimeTest) {
		SaleMachineHealth smh = null;
		if(!doRealTimeTest)
			smh = machineStateRedisBuff.getMachineHealthState(machineCode);

		if(smh == null){
			MachineInfo info = machineStateRedisBuff.getMachineInfo(machineCode);
			smh = machineHealthRealTimeService.checkMachineHealthByRealTime(info);
		}
		return smh;
	}

	@Override
	public ReqQNetStateMsg checkMachineNetStateByRealTime(String machineCode) {
		MachineInfo info = machineStateRedisBuff.getMachineInfo(machineCode);
		return machineHealthRealTimeService.checkMachineNetStateByRealTime(info);
	}

	@Override
	public ReqQASStateMsg checkMachineAisleStateByRealTime(String machineCode, String aisle) {
		MachineInfo info = machineStateRedisBuff.getMachineInfo(machineCode);
		return machineHealthRealTimeService.checkMachineAisleStateByRealTime(info,aisle);
	}

	public void afterPropertiesSet() throws Exception {

	}

	/**
	 * 异步接收心跳信息
	 * @param msg
	 */
	public void receiveData(MachineMsg msg) {

		if(msg == null) return ;
		SaleMachineHealth smh = null;

		if(ReqQNetStateMsg.TYPE_CMD_R_NETSTATE.equals(msg.getOperCode())){
			smh = setOrNewMachineHealth(null,msg);
			machineHealthRealTimeService.updateNetState(smh,(ReqQNetStateMsg)msg);
		}else if(ReqQASStateMsg.TYPE_CMD_R_QASTATE.equals(msg.getOperCode())){
			smh = setOrNewMachineHealth(null,msg);
			machineHealthRealTimeService.updateQAState(smh,(ReqQASStateMsg)msg);
		}

		if(smh != null) {
			machineStateRedisBuff.updateMachineHealthState(smh);
		}
	}


	SaleMachineHealth setOrNewMachineHealth(SaleMachineHealth smh , MachineMsg msg){
		if(msg == null) return smh;
		if(smh == null) {
			smh = new SaleMachineHealth();
			smh.setMachineCode(msg.getMachineCode());
		}
		return smh ;
	}

}
