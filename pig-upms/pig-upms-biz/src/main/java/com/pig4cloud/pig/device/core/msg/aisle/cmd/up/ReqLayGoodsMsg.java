package com.pig4cloud.pig.device.core.msg.aisle.cmd.up;

import com.pig4cloud.pig.device.core.msg.MqttMsgContext;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @title: ReqLayGoodsMsg
 * @projectName eden
 * @description: 机器应答出货情况
 */
@Setter
@Getter
@ToString
public class ReqLayGoodsMsg extends AisleMachineReq{

	//结果码-开门成功
	public static final String  RESULTCODE_OPEN_SUCESS = "0";
	//结果码-缺货
	public static final String  RESULTCODE_LACK_GOODS  = "1";
	//结果码-开门失败
	public static final String  RESULTCODE_OPEN_FAILED = "2";
	//结果码-电机短路
	public static final String  RESULTCODE_CIRCUIT_SHORTED = "3";
	//结果码-订单号重复(重复出货)
	public static final String  RESULTCODE_ID_REPEATED = "5";

	//货道编号
	String aisleId;
	//执行情况
	String resultCode;

	public ReqLayGoodsMsg(){
		super(TYPE_CMD_R_LAYGOODS);
	}

	public ReqLayGoodsMsg(String iotProto){
		super(TYPE_CMD_R_LAYGOODS,iotProto);
	}




}
