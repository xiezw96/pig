package com.pig4cloud.pig.device.core.client.transport;

import com.pig4cloud.pig.device.core.msg.CommunicationException;
import com.pig4cloud.pig.device.core.msg.MachineMsg;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.java.Log;

/**
 * @title: MsgState
 * @projectName eden
 * @description: TODO
 */
@Setter
@Getter
@ToString
@Log
public class MsgState {
	//指令发送完成
	public static short STATE_CMD_INITED = 0;
	//指令发送完成
	public static short STATE_CMD_POSTED = 1;
	//指令发送异常
	public static short STATE_CMD_POSTEXCEPTION = 2;
	//指令接收异常
	public static short STATE_CMD_RCVEXCEPTION = 3;
	//指令接收完成
	public static short STATE_CMD_COMPLETED = 4;

	//消息事务ID
	String txId;
	//发送指令内容
	MachineMsg cmdMsg;
	//等待锁
	Object wtLock = new Object();
	//异常信息
	Throwable t;
	//状态
	volatile short status = STATE_CMD_INITED;
	//应答消息内容
	volatile MachineMsg accMsg = null;

	public MsgState(MachineMsg cmdMsg) {
		if (cmdMsg == null) {
			return;
		}
		this.txId = cmdMsg.getTxId();
		this.cmdMsg = cmdMsg;
	}

	public MachineMsg waitForCompleted(long timeMillcs) {
		synchronized (this.wtLock) {
			while (!isCompleted()) {
				long st = System.nanoTime();
				try {
					if (timeMillcs < 0) {
						this.wtLock.wait();
					} else {
						this.wtLock.wait(timeMillcs);
					}
				} catch (InterruptedException e) {
					this.t = e;
					setStatus(STATE_CMD_RCVEXCEPTION);
					throw new RuntimeException(e);
				}

				if (this.t != null) {
					throw new RuntimeException("发生异常",this.t);
				}else if(System.nanoTime() - st >= timeMillcs * 1000000){
					log.warning("+++消息："+cmdMsg.toString()+",接收超时，limited="+timeMillcs);
					throw new CommunicationException(CommunicationException.REASON_CODE_TIMEOUT,"超时异常,cmdMsg="+cmdMsg.toString(),null);
				}
			}
		}
		return accMsg;
	}

	public void completedByNoWait(){
		this.status = STATE_CMD_COMPLETED;
	}


	public void notifyCompleted(MachineMsg rMsg) {
		this.status = STATE_CMD_COMPLETED;
		this.accMsg = rMsg;
		notifyAllBlk();
	}

	public void notifyAllBlk() {
		synchronized (this.wtLock) {
			wtLock.notifyAll();
		}
	}

	public void postException(Throwable t) {
		this.status = STATE_CMD_POSTEXCEPTION;
		this.t = t;
	}

	public void recvException(Throwable t) {
		this.status = STATE_CMD_RCVEXCEPTION;
		this.t = t;
	}

	public boolean isCompleted() {
		return this.status == STATE_CMD_COMPLETED;
	}


}
