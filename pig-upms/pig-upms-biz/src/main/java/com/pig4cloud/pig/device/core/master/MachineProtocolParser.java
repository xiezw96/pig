package com.pig4cloud.pig.device.core.master;

import com.pig4cloud.pig.device.core.msg.MachineMsg;
import com.pig4cloud.pig.device.core.msg.MsgContext;

/**
 * @title: MachineProtocolParser
 * @projectName eden
 * @description: 机器协议解析器
 */
public interface MachineProtocolParser {

	/**
	 * 将厂家设备协议转换为平台返回对象格式
	 * @param result
	 * @param ctx
	 * @return
	 */
	public MachineMsg format(byte[] result, MsgContext ctx);

	/**
	 * 待解析消息匹配
	 * @param ctx
	 * @return
	 */
	public boolean isMatched(MsgContext ctx);

	/**
	 * 解析平台请求为厂家设备请求协议格式
	 * @param req
	 * @return
	 */
	public byte[] parser(MachineMsg req);
}
