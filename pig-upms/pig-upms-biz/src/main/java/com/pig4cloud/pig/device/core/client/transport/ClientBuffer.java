package com.pig4cloud.pig.device.core.client.transport;

import cn.hutool.core.collection.ConcurrentHashSet;
import com.pig4cloud.pig.device.core.client.transport.MsgState;
import com.pig4cloud.pig.device.core.master.MachineInteraction;
import com.pig4cloud.pig.device.core.msg.MachineMsg;
import com.pig4cloud.pig.device.core.msg.MachineMsgListener;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @title: ClientBuffer
 * @projectName eden
 * @description: TODO
 */
@Component
@Log
public class ClientBuffer {

	MsgListenerCache userLnCaches = new MsgListenerCache();

	ConcurrentHashMap<String, MsgState> stateBuf = new ConcurrentHashMap<>();

	@Autowired
	@Qualifier("saleMachineBrokerMaster")
	MachineInteraction machineInteraction;

	public void init(){
		this.machineInteraction.addMachineListener(msg -> {
			if(msg == null ){
				log.warning("++应答的消息内容为空!");
				return ;
			}
			notifyAll(msg);
			//用户消息监听处理
			userLnCaches.passMsg(msg);
		});
	}

	/**
	 * 唤醒线程
	 * @param accMsg
	 */
	public void notifyAll(MachineMsg accMsg) {
		if(accMsg == null) return ;
		MsgState msgState = stateBuf.get(accMsg.getTxId()) ;
		stateBuf.remove(accMsg.getTxId());
		if(msgState == null) {
			log.warning("++唤醒线程失败:无法根据返回txId查询到有关MsgState!,rMsg="+accMsg);
			return ;
		}
		msgState.notifyCompleted(accMsg);
	}

	public MsgState sendCmd(MachineMsg msg){
		if(msg == null) return null;
		MsgState msgState = new MsgState(msg);
		try{
			stateBuf.put(msg.getTxId(),msgState);
			this.machineInteraction.asynSend(msg);
		}catch (RuntimeException e){
			log.warning("++消息["+msg+"]发送异常！");
			msgState.postException(e);
			stateBuf.remove(msg.getTxId());
			throw e;
		}
		msgState.setStatus(MsgState.STATE_CMD_POSTED);
		return msgState;
	}


	public void addMachineListener(String msgCode,MachineMsgListener machineListener) {
		userLnCaches.add(msgCode,machineListener);
	}

	public void rmMachineListener(MachineMsgListener machineListener){
		userLnCaches.remove(machineListener);
	}

	class MsgListenerCache {

		ConcurrentHashMap<String, ConcurrentHashSet<MachineMsgListener>>  lnCache = new ConcurrentHashMap<>();

		public void add(String msgCode,MachineMsgListener ln) {
			synchronized (lnCache) {
				ConcurrentHashSet<MachineMsgListener>  cs = lnCache.get(msgCode);
				if(cs == null) {
					cs = new ConcurrentHashSet();
					cs.add(ln);
					lnCache.put(msgCode,cs);
				}
			}
		}

		public void remove(MachineMsgListener ln){
			synchronized (lnCache){
				for(ConcurrentHashSet cs : lnCache.values()){
					cs.remove(ln);
				}
			}
		}
		public ConcurrentHashSet get(String msgCode){
			return lnCache.get(msgCode);
		}

		public void passMsg(MachineMsg msg) {
			if(msg != null) {
				ConcurrentHashSet<MachineMsgListener> cs = lnCache.get(msg.getOperCode());
				if(cs == null && !msg.getOperCode().equals(MachineMsgListener.LISTEN_ALL_MSG_CODE)) return ;
				cs.forEach(machineMsgListener -> machineMsgListener.receiveData(msg));

				cs = lnCache.get(MachineMsgListener.LISTEN_ALL_MSG_CODE) ;
				if(cs == null) return ;
				cs.forEach(machineMsgListener -> machineMsgListener.receiveData(msg));
			}
		}

	}

}
