package com.pig4cloud.pig.device.core.msg.aisle.cmd.down;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @title: CmdLayGoodsMsg
 * @projectName eden
 * @description: 货道发货消息
 */
@Getter
@Setter
@ToString
public class CmdLayGoodsMsg extends AisleComandMsg {
	//货道编号
	String asId;
	//出货数量
	int    num;

	public CmdLayGoodsMsg(){
		super(TYPE_CMD_LAYGOODS);
	}

	public CmdLayGoodsMsg(String iotProto){
		super(TYPE_CMD_LAYGOODS,iotProto);
	}

}
