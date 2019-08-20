package com.pig4cloud.pig.device.core.master;

import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pig.device.core.msg.MachineMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @title: MasterMsgCached
 * @projectName pig
 * @description: TODO
 */
@Component
public class MasterMsgCached {

	@Value("${device.master.ba.redis.timeoutces:30}")
	long redisTimeoutSecs;

	@Autowired
	RedisTemplate redisTemplate;
	//请求事务关联的消息KEY （REDIS）模板
	public static final String REL_MSG_MQTT_TX_REDIS_KEY_TMPL = "rel_msg_mqtt_tx_%s";

	/**
	 * 缓存事务关联的消息对象
	 * @param msg
	 */
	public void  putTxMsgCached(MachineMsg msg) {
		String  key = String.format(REL_MSG_MQTT_TX_REDIS_KEY_TMPL, msg.getTxId());
		redisTemplate.opsForValue().set(key,msg,redisTimeoutSecs, TimeUnit.SECONDS);
	}

	/**
	 * 根觉txId获取并删除缓存消息
	 * @param txId
	 * @return
	 */
	public MachineMsg popTxMsgCached(String txId){
		if(StrUtil.isBlank(txId)) return  null;
		String  key = String.format(REL_MSG_MQTT_TX_REDIS_KEY_TMPL, txId);
		MachineMsg rMsg = (MachineMsg)redisTemplate.opsForValue().get(key);
		return rMsg;
	}

}
