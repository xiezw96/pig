package com.pig4cloud.pig.device.api;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.pig4cloud.pig.device.core.msg.aisle.cmd.part.AsPart;
import io.undertow.util.CopyOnWriteMap;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @title: SaleMachineHealth
 * @projectName eden
 * @description:  售货机器健康状态信息
 */
@Setter
@Getter
@ToString
@NoArgsConstructor
public class SaleMachineHealth implements Serializable {
	private static final long serialVersionUID = 1L;
	//货道状态（包含电机）-货道门打开状态
	public static final int AISLE_STATE_OPEN = 1 ;
	//货道状态（包含电机）-货道正常（门关闭）
	public static final int AISLE_STATE_NORAML = 2 ;
	//货道状态（包含电机）-货道电机故障
	public static final int AISLE_STATE_EC_FAIL = 3 ;

	//机器设备业务标识
	public String machineCode;

	public Date updateTimes;
	//是否在线
	public boolean isOnline ;
	/******* 网络信息   ************/
	//通讯卡ICCID
	public String iccid;
	//信号强度
	public String signalStrenth;
	//位置经度
	public long positionLongitude = -1L;
	//位置纬度
	public long positionLatitude  = -1L;

	/******* 硬件及机械相关信息   *****/
	//各货道电机状态
	public CopyOnWriteMap<String,Integer> aislesStatus = new CopyOnWriteMap<>();

	/**
	 * 判断设备是否在线，标准：在规定时间内，更新了网络信息
	 * @param timeSecs 时间秒数
	 * @return
	 */
	public boolean isOnLine(long timeSecs) {
		long dvTimes =  DateUtil.between(updateTimes,new Date(), DateUnit.SECOND);
		return ( timeSecs - dvTimes >= 0 );
	}

	public void updateTimeStamp(){
		setUpdateTimes(new Date());
	}

}
