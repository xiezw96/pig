package com.pig4cloud.pig.device.core.msg.aisle.cmd.part;

import com.pig4cloud.pig.device.core.msg.MachinePart;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @title: AsPart
 * @projectName eden
 * @description: 货道信息
 */
@Setter
@Getter
@ToString
public class AsPart extends MachinePart {
	//类型货道
	public static final String PART_TYPE = "p_as";
	//电机正常
	public static final short CM_STATE_NORMAL = 0 ;
	//检测到超时状态
	public static final short CM_STATE_TIMEOUT = 2 ;
	//检测到电机短路
	public static final short CM_STATE_CC_CUT  = 4 ;


	//门状态-打开状态
	public static final short DOOR_STATE_OPEN  = 0 ;
	//门状态-关闭状态
	public static final short DOOR_STATE_CLOSE = 2 ;

	//货道电机状态
	public Integer cmState;

	//门状态
	public Integer doorState;

	public AsPart(){
		this.setType(PART_TYPE);
	}


}
