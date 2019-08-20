package com.pig4cloud.pig.device.core.msg;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @title: MqttMsgContext
 * @projectName eden
 * @description: TODO
 */
@Getter
@Setter
@ToString
public class MqttMsgContext extends  MsgContext{
	//操作涉及的主题
	public String topic;
	//操作涉及的mqtt内置消息标识
	public int mqttMsgId;
	//操作涉及的qos
	public int msgQos;
}
