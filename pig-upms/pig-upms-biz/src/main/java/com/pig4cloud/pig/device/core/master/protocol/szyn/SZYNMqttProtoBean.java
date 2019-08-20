package com.pig4cloud.pig.device.core.master.protocol.szyn;

import cn.hutool.json.JSONUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 深圳宇楠格子柜门式MQTT协议类
 */
@Setter
@Getter
@ToString
public class SZYNMqttProtoBean {
	//指令
	int c = 0;
	//发送方ID
	String f;
	//接收方ID
	String t;
	//消息体
	String m;
	//销售订单ID
	String s;
	//每条消息标识
	int mi;

	public String toJson() {
		return JSONUtil.toJsonStr(this);
	}

	public static SZYNMqttProtoBean toBean(byte[] bytes) {
		if(bytes != null && bytes.length > 0) {
			String c = new String(bytes);
			return JSONUtil.toBean(c,SZYNMqttProtoBean.class);
		}
		return  null;
	}
}
