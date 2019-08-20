package com.pig4cloud.pig.device.core.msg.aisle.cmd.part;

import com.pig4cloud.pig.device.core.msg.MachinePart;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @title: SimPart
 * @projectName eden
 * @description: Sim卡相关
 */
@Setter
@Getter
@ToString
public class SimPart extends MachinePart {
	//SIM卡类型定义
	public static final String PARTTYPE_SIM = "p_sim";
	//SIM卡的iccid
	public String iccId;
	//射频信号强度
	public String rssi;

}
