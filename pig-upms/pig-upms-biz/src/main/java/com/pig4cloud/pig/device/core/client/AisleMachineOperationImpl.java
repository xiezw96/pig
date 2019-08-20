package com.pig4cloud.pig.device.core.client;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pig.device.api.*;
import com.pig4cloud.pig.device.core.msg.CommunicationException;
import com.pig4cloud.pig.device.core.msg.MachineMsgListener;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.down.AisleComandMsg;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.down.CmdLayGoodsMsg;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.down.OpenAisleMsg;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.part.AsPart;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.up.*;
import lombok.extern.java.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

/**
 * @title: AisleMachineOperationImpl
 * @projectName eden
 * @description:  货道式销售机器接口实现
 */
@Component
@Log
public class AisleMachineOperationImpl implements AisleSaleMachineOperation, InitializingBean {

	@Autowired
	MachineStateService machineQueries;

	@Autowired
	MachineOpsClient machineOpsClient;

	@Value("${device.client.aisle.retrytimes:1}")
	int retryTimes  = 1;

	@Value("${device.client.aisle.oltt:180}")
	int offLineTimeSecsTolTolerated;

	public void afterPropertiesSet() throws Exception {
		machineOpsClient.addMachineListener(MachineMsgListener.LISTEN_ALL_MSG_CODE,machineQueries);
	}

	@Override
	public void layTheGoods(String orderId, String machineCode, String aisle, int num) throws AisleMachineOperException {
		if (StrUtil.isBlank(orderId) || StrUtil.isBlank(aisle)) {
			log.warning("输入的参数有误，订单编号或货道编号为空!");
			throw new IllegalArgumentException("输入的参数有误，订单编号或货道编号为空!");
		}
		MachineInfo m = getMachineInfo(machineCode);
		//消息拼接
		CmdLayGoodsMsg msg = new CmdLayGoodsMsg();
		MsgConvertorForClient.toCmdMsg(m, msg);
		msg.setTxId(orderId);
		msg.setAsId(aisle);
		msg.setNum(num < 1 ? 1 : num);
		//应答
		ReqLayGoodsMsg rMsg = (ReqLayGoodsMsg) machineOpsClient.postCmd(msg, retryTimes);
		if(rMsg == null) {
			log.warning("+++发送出货指令异常，应答内容为空！");
			throw new RuntimeException("发送出货指令异常，应答内容为空");
		}

		if (ReqLayGoodsMsg.RESULTCODE_ID_REPEATED.equals(rMsg.getResultCode())) {
			log.warning("通过重发，设备端应答已处理！，详细[" + rMsg + "]");
		} else if (StrUtil.equalsAny(rMsg.getResultCode(), ReqLayGoodsMsg.RESULTCODE_CIRCUIT_SHORTED,
			ReqLayGoodsMsg.RESULTCODE_LACK_GOODS,
			ReqLayGoodsMsg.RESULTCODE_OPEN_FAILED)) {
			throw new AisleMachineOperException(rMsg.getOperCode(), rMsg.getResultCode(), "出货失败！");
		}
	}



	/**
	 * 根据设备编号获取设备相关信息
	 * @param machineCode
	 * @return
	 */
	MachineInfo getMachineInfo(String machineCode){
		if(StrUtil.isBlank(machineCode)) {
			log.warning("输入的参数有误，机器业务标识为空!");
			throw new IllegalArgumentException("输入的参数有误，机器编号为空!");
		}
		MachineInfo m = machineQueries.get(machineCode);
		if (m == null) {
			log.warning("机器设备的基础信息不存在，id=" + machineCode + "!");
			throw new RuntimeException("机器设备的基础信息不存在");
		}
		return m;
	}

	@Override
	public boolean checkTheOrder(String orderId, String machineCode) throws AisleMachineOperException {
		if (StrUtil.isBlank(orderId)) {
			log.warning("输入的参数有误，订单编号为空!");
			throw new IllegalArgumentException("输入的参数有误，订单编号为空!");
		}
		MachineInfo m = getMachineInfo(machineCode);
		AisleComandMsg msg = new AisleComandMsg(AisleComandMsg.TYPE_CMD_ODSTATE);
		MsgConvertorForClient.toCmdMsg(m, msg);
		msg.setTxId(orderId);
		ReqOdStateMsg rMsg = (ReqOdStateMsg)machineOpsClient.postCmd(msg, retryTimes);

		if (rMsg == null) {
			log.warning("发送检查订单状态指令失败!");
			throw new RuntimeException("发送检查订单状态指令失败！，orderId="+orderId+",machineCode="+machineCode);
		}

		return rMsg.isOdExisted;
	}

	@Override
	public void resetAfterReplenish(String machineCode) throws AisleMachineOperException {
		MachineInfo m = getMachineInfo(machineCode);
		AisleComandMsg msg = new AisleComandMsg(AisleComandMsg.TYPE_CMD_MERESET);
		MsgConvertorForClient.toCmdMsg(m, msg);

		AisleMachineReq rMsg = (AisleMachineReq)machineOpsClient.postCmd(msg, retryTimes);
		if (rMsg == null) {
			log.warning("发送机器补货复位指令失败!");
			throw new RuntimeException("发送机器补货复位指令失败！ ,machineCode="+machineCode);
		}
	}

	@Override
	public String[] openAllTheDoor(String machineCode) {
		//TODO 目前方式为模拟货道门全开
		MachineInfo m = getMachineInfo(machineCode);
		if(CollUtil.isEmpty(m.getAllAisleCodes())){
			throw new RuntimeException("打开机器失败，机器："+machineCode+",货道配置为空！");
		}
		Set<String> failAisle = new HashSet<>();
		boolean result = false;
		for(String aisle : m.getAllAisleCodes()) {
			try {
				result = openTheAsileDoor(machineCode,aisle);
			}catch (RuntimeException e){
				log.warning("模拟打开机器所有柜门失败，原因："+ ExceptionUtil.stacktraceToString(e));
				failAisle.add(aisle);
				continue;
			}
			if(!result) failAisle.add(aisle);
		}
		return failAisle.toArray(new String[failAisle.size()]);
	}

	@Override
	public boolean openTheAsileDoor(String machineCode, String aisle) {
		if (StrUtil.isBlank(aisle)) {
			log.warning("输入的参数有误，货道编号为空!");
			throw new IllegalArgumentException("输入的参数有误，货道编号为空!");
		}

		MachineInfo m = getMachineInfo(machineCode);
		//消息拼接
		OpenAisleMsg msg = new OpenAisleMsg();
		MsgConvertorForClient.toCmdMsg(m, msg);
		msg.setAisleId(aisle);
		//应答
		ReqOpenAisleMsg rMsg = (ReqOpenAisleMsg) machineOpsClient.postCmd(msg);

		if(rMsg == null) {
			log.warning("发送打开货道门指令失败!");
			throw new RuntimeException("发送打开货道门指令失败！ ,machineCode="+machineCode);
		}

		Integer result = rMsg.getResult();
		return result != null && result.intValue() == ReqOpenAisleMsg.RESULT_OPEN_DOOR_SUCESS;
	}

	@Override
	public boolean checkIfOnline(String machineCode,boolean doRealTimeTest) {
		if(StrUtil.isBlank(machineCode)) {
			log.warning("输入的参数有误，机器业务标识为空!");
			throw new IllegalArgumentException("输入的参数有误，机器业务标识为空!");
		}
		SaleMachineHealth smh = null;
		try{
			smh = machineQueries.getHealthState(machineCode,doRealTimeTest);
		}catch (CommunicationException ex){
			if(CommunicationException.REASON_CODE_TIMEOUT == ex.getReasonCode())
				return false;
			throw ex;
		}
		return smh.isOnLine(offLineTimeSecsTolTolerated);
	}

	@Override
	public int getAsileStatus(String machineCode, String aisle, boolean doRealTimeTest) {
		if (StrUtil.isBlank(aisle)) {
			log.warning("输入的参数有误，货道编号为空!");
			throw new IllegalArgumentException("输入的参数有误，货道编号为空!");
		}
		if(doRealTimeTest){
			ReqQASStateMsg rMsg = machineQueries.checkMachineAisleStateByRealTime(machineCode,aisle);
			if(rMsg == null ||
				rMsg.getParts() == null ||
				rMsg.getParts().get(0) == null ||
				rMsg.getParts().get(0).getCmState() == null)
				throw new RuntimeException("机器应答货道状态信息为空！，请求machineCode"+machineCode+",aisle="+aisle+",realTimeTest="+doRealTimeTest);
			for(AsPart ap : rMsg.getParts()) {
				if(ap.getId().equals(aisle)){
					return ap.getCmState();
				}
			}
			throw new RuntimeException("机器应答货道状态信息为空！，请求machineCode"+machineCode+",aisle="+aisle+",realTimeTest="+doRealTimeTest);
		} else {
			SaleMachineHealth smh = checkHealthOfMachine(machineCode,false);
			return smh.getAislesStatus().get(aisle);
		}
	}

	@Override
	public SaleMachineHealth checkHealthOfMachine(String machineCode,boolean doRealTimeTest) {
		if (StrUtil.isBlank(machineCode)) {
			log.warning("输入的参数有误，机器编号为空!");
			throw new IllegalArgumentException("输入的参数有误，机器业务标识为空!");
		}
		return machineQueries.getHealthState(machineCode,doRealTimeTest);
	}

}
