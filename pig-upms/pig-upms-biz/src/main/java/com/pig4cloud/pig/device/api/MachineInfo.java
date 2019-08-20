package com.pig4cloud.pig.device.api;

import lombok.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @title: MachineInfo
 * @projectName pig
 * @description: 机器相关信息
 */
@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MachineInfo implements Serializable {
	private static final long serialVersionUID = 1L;
	//数据更新时间
	Date upDate;
	/****************  机器的基础信息     *****************************/
	//设备ID（内部存储ID）
	Integer id;
	//机器业务标识码（IMEI或ICCID）
	String code;
	//机器名称
	String name;
	//机器型号
	Integer specId;
	//厂家类型
	String specProvider;
	//采用的物联网通讯协议类型，mqtt等
	String iotProto;
	// "状态 0 待激活 1 正常  2 故障 3补货 4 离线 5回收"
	Integer status;
	//所有货道编号列表
	List<String> allAisleCodes = new ArrayList<>();

}
