package com.pig4cloud.pig.device.core.master;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pig.device.core.msg.MachineMsg;
import com.pig4cloud.pig.device.core.msg.MachineMsgListener;
import com.pig4cloud.pig.device.core.msg.MsgContext;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.up.AisleMachineReq;
import lombok.Setter;
import lombok.extern.java.Log;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @title: BrokerMaster
 * @projectName eden
 * @description: TODO
 */
@Component("saleMachineBrokerMaster")
@Setter
@Log
public class SaleMachineBrokerMaster implements MachineInteraction, InitializingBean, DisposableBean {

	//非启动状态
	volatile short  status = STATUS_INITING;

	@Autowired
	public List<BrokerAgent> brokerAgentList = null;

	@Autowired
	MasterMsgCached masterMsgCached;

	Set<MachineMsgListener> machineListenerSet = new HashSet<>();

	@Override
	public void init() {
		if(brokerAgentList == null || brokerAgentList.size() < 1)
			throw new RuntimeException("启动SaleMachineBrokerMaster失败，brokerAgentList实例为空！");

		brokerAgentList.sort(null);

		for(BrokerAgent ba : brokerAgentList) {
			ba.init(this);
			log.info("SaleMachineBrokerMaster成功初始化："+ba+"!");
		}
		//修改状态
		setStatus(STATUS_RUNNING);
	}

	public void asynSend(MachineMsg req) {
		if(req == null) return ;

		BrokerAgent ba = getBrokeAgent(req.getCtx());
		if(ba == null) {
			log.warning("请求消息：["+req+"],处理失败，brokerAgent匹配失败！");
			throw new RuntimeException("后台配置异常，brokerAgent配置匹配失败!");
		}

		if(!checkStatus(STATUS_RUNNING)){
			log.warning("请求消息：["+req+"],处理失败，Master状态异常："+status);
			throw new RuntimeException("后台配置异常，Master状态异常："+status);
		}
		ba.sendMsg(req);
		masterMsgCached.putTxMsgCached(req);
	}

	public void asynRecv(MachineMsg rMsg){
		machineListenerSet.forEach(machineMsgListener -> {
			machineMsgListener.receiveData(rMsg);
		});
	}

	BrokerAgent getBrokeAgent(MsgContext ctx){
		if(CollectionUtil.isNotEmpty(brokerAgentList)){
			for(BrokerAgent ba : brokerAgentList) {
				if(ba.match(ctx)) return ba;
			}
		}
		return null;
	}

	public synchronized void setStatus(short status){
		this.status = status;
	}

	public synchronized boolean checkStatus(short status){
		return this.status == status;
	}

	@Override
	public void release() {
		setStatus(STATUS_STOPPING);
		if(CollectionUtil.isNotEmpty(brokerAgentList)){
			for(BrokerAgent ba : brokerAgentList) {
				ba.release();
				log.info("成功释放资源："+ba);
			}
		}
		setStatus(STATUS_STOPPED);
	}

	public short getStatus(){
		return status;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		init();
	}

	@Override
	public void destroy() throws Exception {
		release();
	}


	public void addMachineListener(MachineMsgListener machineListener) {
		machineListenerSet.add(machineListener);
	}

}
