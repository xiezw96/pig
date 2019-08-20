package com.pig4cloud.pig.device.core.msg;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * @title: MachineMsg
 * @projectName eden
 * @description: 物理设备发起的请求
 */
@Setter
@Getter
@ToString
public class MachineMsg implements Serializable {
	private static final long serialVersionUID = 1L;
	//消息事务ID,与命令请求对应
	protected String txId;
	//消息ID（平台生成）
	protected long msgId;
	//设备业务标识码（如：以IMEI或ICCID作为标识）
	protected String machineCode;
	//操作码
	protected String operCode;

	//消息上下文
	private MsgContext ctx;

	//部件状态
	List<MachinePart> machinePartList;

	public MsgContext getCtx() {
		return ctx == null ? new MsgContext() : ctx;
	}

	public void initContext(String iotProto){
		if(MsgConstants.PROTOCAL_TYPE_MQTT.equals(iotProto) || StrUtil.isBlank(iotProto)){
			setCtx(new MqttMsgContext());
		}else{
			setCtx(new MsgContext());
		}
		this.ctx.setIotProto(iotProto);
	}

	public String toString(){
		return JSONUtil.toJsonStr(this);
	}
}
