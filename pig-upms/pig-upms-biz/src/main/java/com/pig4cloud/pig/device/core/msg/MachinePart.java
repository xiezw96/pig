package com.pig4cloud.pig.device.core.msg;

import cn.hutool.core.util.NumberUtil;
import lombok.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @title: MachinePart
 * @projectName eden
 * @description: 设备传感器或机电控制开关
 */
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MachinePart implements  Comparable<String> , Serializable {

	//部件类型
	String  type;
	//部件标识
	String  id;

	//属性键值对
	private Map<String,String> kv = new HashMap<>();

	//子部件
	List<MachinePart> children;

	public  Integer getIntValue(String key){
		String val = kv.get(key);
		if(val == null) return null;
		return Integer.valueOf(val);
	}

	public Double getDoubleValue(String key){
		String val = kv.get(key);
		if(val == null) return null;
		return Double.valueOf(val);
	}

	public Boolean getBoolValue(String key){
		String val = kv.get(key);
		if(val == null) return null;
		return Boolean.valueOf(val);
	}

	public String getStrValue(String key){
		String val = kv.get(key);
		return val;
	}

	public void setKV(String key,String value){
		kv.put(key,value);
	}

	public void rmKey(String key){
		kv.remove(key);
	}

	@Override
	public int compareTo(String o) {
		if(o == null) return 1;
		else if(!NumberUtil.isNumber(o) || !NumberUtil.isInteger(this.id)){
			return this.id.compareTo(o);
		}else{
			return Integer.valueOf(this.id) - Integer.valueOf(o);
		}
	}
}
