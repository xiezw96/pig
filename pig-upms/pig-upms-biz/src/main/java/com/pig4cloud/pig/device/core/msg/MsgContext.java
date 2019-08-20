package com.pig4cloud.pig.device.core.msg;

import cn.hutool.core.util.ObjectUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: MsgContext
 * @projectName eden
 * @description: TODO
 */
@Getter
@Setter
@ToString
public class MsgContext implements Serializable {
	private static final long serialVersionUID = 1L;
	//设备厂商编码
	String manufactoryId;
	//设备产品系列
	String machineSerial;

	//涉及到的服务器标识
	String serverId;

	//设备物联网通讯协议
	String iotProto;

	//其他上下文信息
	Map<String,String> context = new HashMap<>();

	//关联消息 (可为空)
	public MachineMsg  relMsg = null;

	public void  setKV(String key , String value){
		if(context != null) context.put(key,value);
	}

	public String getV(String key) {
		if(context == null) return null;
		return context.get(key);
	}

	public <T extends MsgContext> void   copy2Children(T t){
		t.setManufactoryId(this.getManufactoryId());
		t.setMachineSerial(this.getMachineSerial());
		t.setServerId(this.getServerId());
		t.setIotProto(this.getIotProto());
		t.setContext(this.getContext());
	}

}
