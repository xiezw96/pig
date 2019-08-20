package com.pig4cloud.pig.device.core.client.transport;

import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pig.device.core.client.MachineOpsClient;
import com.pig4cloud.pig.device.core.msg.MachineMsg;
import com.pig4cloud.pig.device.core.msg.MachineMsgListener;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.down.AisleComandMsg;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @title: AisleMachineOpsClientImpl
 * @projectName eden
 * @description: 货道式机器客户端实现,一阶段简单实现
 */
@Component
@Setter
@Log
@ToString
public class AisleMachineOpsClientImpl implements MachineOpsClient, InitializingBean {

	//等待时间
	@Value("${device.client.aisle.waitfor:10000}")
	long waitForTimeMillcs ;

	@Autowired
	public ClientBuffer clientBuffer;

	IdGenerator idGenerator = new IdGenerator();

	void beforePost(MachineMsg msg) {
		if(!AisleComandMsg.class.isAssignableFrom(msg.getClass())){
			log.warning("++发送的指令类型非：AisleComandMsg！");
			throw new RuntimeException("发送的指令类型非：AisleComandMsg！");
		}
		//设置平台统一消息标识
		msg.setMsgId(idGenerator.next());
		//未设置txid情况
		if(StrUtil.isEmpty(msg.getTxId())){
			msg.setTxId(String.valueOf(Long.MAX_VALUE - msg.getMsgId()));
		}
	}

	@Override
	public MachineMsg postCmd(MachineMsg msg) {
		beforePost(msg);
		//客户端消息发送
		//TODO 消息分发
		MachineMsg rMsg =  clientBuffer.sendCmd(msg).waitForCompleted(waitForTimeMillcs);
		return rMsg;
	}

	@Override
	public MachineMsg postCmd(MachineMsg msg, int retryTimes) {
		MachineMsg rMsg = null;

		do{
			try{
				rMsg = postCmd(msg);
				if(rMsg != null)
					break ;
			}catch (RuntimeException e) {
				log.warning("++发送指令:["+msg+"]失败，重试次数还剩："+retryTimes);
				//抛出最后一次重试的异常
				if(retryTimes < 1) {
					throw e ;
				}
			}
		}
		while(retryTimes-- > 0);

		return rMsg;
	}

	public void  asynPostCmd(MachineMsg msg){
		beforePost(msg);
		clientBuffer.sendCmd(msg).completedByNoWait();
	}

	@Override
	public void addMachineListener(String msgCode, MachineMsgListener machineListener) {
		clientBuffer.addMachineListener(msgCode,machineListener);
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		clientBuffer.init();
	}

	/**
	 * 简易的ID生成器
	 */
	class IdGenerator {
		long start = 0;
		AtomicLong incr = null;
		IdGenerator(){
			start = System.currentTimeMillis() << 4;
			incr  = new AtomicLong(start);
		}
		long next(){
			return incr.incrementAndGet();
		}
	}
}
