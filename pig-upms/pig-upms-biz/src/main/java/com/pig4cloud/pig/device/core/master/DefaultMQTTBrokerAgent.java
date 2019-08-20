package com.pig4cloud.pig.device.core.master;

import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pig.device.core.msg.*;
import lombok.Setter;
import lombok.extern.java.Log;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * @title: DefaultMQTTBrokerAgent
 * @projectName eden
 * @description: TODO
 */
@Component
@Log
@Setter
public class DefaultMQTTBrokerAgent implements BrokerAgent {

	//注入
	@Value("${device.master.ba.defmqtt.priority:-1}")
	short priority;

	@Value("${device.master.ba.defmqtt.brokerurl:tcp://www.fxly69.cn:1883}")
	String brokerUrl;

	@Value("${device.master.ba.defmqtt.username:fxly}")
	String userName;

	@Value("${device.master.ba.defmqtt.passwd:fxly698}")
	String passwd;

	@Value("${device.master.ba.defmqtt.serverid:FXLY001}")
	String serverId;

	@Value("${device.master.ba.defmqtt.topictag:ZHZN}")
	String topicTag;

	@Value("${device.master.ba.defmqtt.inflightnum:50}")
	int inflightNumLimited;

	@Value("${device.master.ba.defmqtt.workernum:50}")
	int workerThreadNum;

	@Value("${device.master.ba.defmqtt.conntimeout:180}")
	int connTimeout;

	@Value("${device.master.ba.defmqtt.kaliveinterval:30}")
	int keepAliveIntervalSecs;

	@Value("${device.master.ba.defmqtt.qos:1}")
	int qos;

	MqttAsyncClient mqttAsyncClient;

	ScheduledExecutorService scheduledExecutorService;

	MachineInteraction machineInteraction = null;

	String clientId = null;

	@Autowired
	public List<MachineProtocolParser> protoParserList = null;

	public boolean match(MsgContext ctx) {
		//TODO 目前所有MQTT暂调转至本处理类
		return StrUtil.equalsAnyIgnoreCase(ctx.getIotProto(), MsgConstants.PROTOCAL_TYPE_MQTT);
	}

	public void init(MachineInteraction machineInteraction) {
		if (protoParserList == null || protoParserList.size() < 1)
			throw new RuntimeException("启动SaleMachineBrokerMaster失败，protoParserList实例为空！");

		this.machineInteraction = machineInteraction;
		this.clientId = getClientId();
		//初始化MQTT的客户端及连接
		mqttAsyncClient = createMqttClient();
		mqttAsyncClient.setCallback(new DefaultMqttCallBack());
		openMqttConn();
	}

	MachineProtocolParser getProtoParser(MsgContext ctx) {
		for (MachineProtocolParser parser : protoParserList) {
			if (parser.isMatched(ctx)) {
				return parser;
			}
		}
		return null;
	}

	/**
	 * 初始化MQTT的客户端
	 *
	 * @return
	 */
	MqttAsyncClient createMqttClient() {
		try {
			//TODO Redis 存储改造
			scheduledExecutorService = Executors.newScheduledThreadPool(workerThreadNum);
			mqttAsyncClient = new MqttAsyncClient(brokerUrl, this.clientId, new MemoryPersistence(), new ScheduledExecutorPingSender(scheduledExecutorService), scheduledExecutorService);
		} catch (MqttException e) {
			throw new RuntimeException("创建MqttClient失败!", e);
		}
		return mqttAsyncClient;
	}

	String getClientId() {
		InetAddress ia = null;
		try {
			ia = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			log.warning("获取本地主机地址失败!," + ExceptionUtil.stacktraceToString(e));
		}
		return ia != null ? serverId + "_" + ia.getHostAddress() : serverId + "_" + UUID.randomUUID();
	}

	/**
	 * 创建MqttClient连接
	 */
	void openMqttConn() {
		MqttConnectOptions connOpts = new MqttConnectOptions();
		// 设置是否清空session,这里如果设置为false表示服务器会保留客户端的连接记录，这里设置为true表示每次连接到服务器都以新的身份连接
		connOpts.setCleanSession(true);
		// 设置连接的用户名
		connOpts.setUserName(userName);
		connOpts.setPassword(passwd.toCharArray());
		// 设置超时时间 单位为秒
		connOpts.setConnectionTimeout(connTimeout);
		// 设置会话心跳时间 单位为秒 服务器会每隔1.5*N秒的时间向客户端发送个消息判断客户端是否在线，但这个方法并没有重连的机制
		connOpts.setKeepAliveInterval(keepAliveIntervalSecs);
		// 自动重连
		connOpts.setAutomaticReconnect(true);
		// 设置最大在途消息数
		connOpts.setMaxInflight(inflightNumLimited);
		// 建立连接
		try {
			mqttAsyncClient.connect(connOpts).waitForCompletion();
		} catch (MqttException e) {
			log.warning("默认Mqtt网关连接失败!，code=" + e.getReasonCode());
			log.warning(ExceptionUtil.getMessage(e));
		}
	}

	/**
	 * 订阅主题消息
	 */
	void subscribe() {
		try {
			mqttAsyncClient.subscribe(new String[]{topicTag + MqttTopic.TOPIC_LEVEL_SEPARATOR + serverId},
									  new int[]{1},
									  new IMqttMessageListener[]{new IMqttMessageListener() {
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					log.info("+++收到订阅消息：" + message.toString());
					MqttMsgContext mqttCtx = new MqttMsgContext();
					mqttCtx.setMqttMsgId(message.getId());
					mqttCtx.setMsgQos(message.getQos());
					mqttCtx.setTopic(topic);
					mqttCtx.setServerId(serverId);
					//TODO 设备的厂家及系列信息优化
					if (topic.startsWith(topicTag)) {
						mqttCtx.setManufactoryId(topicTag);
						mqttCtx.setIotProto(MsgConstants.PROTOCAL_TYPE_MQTT);
					}
					try{
						MachineProtocolParser msgParser = getProtoParser(mqttCtx);
						MachineMsg rMsg = msgParser.format(message.getPayload(), mqttCtx);
						machineInteraction.asynRecv(rMsg);
					}catch (RuntimeException e){
						log.warning("+++解析设备应答消息失败！，消息内容："+message.toString()+",异常："+ExceptionUtil.stacktraceToString(e));
					}
				}
			}});
		} catch (MqttException e) {
			log.warning("订阅消息异常：" + ExceptionUtil.stacktraceToString(e));
			throw new RuntimeException("订阅消息异常：", e);
		}

	}

	/**
	 * 断开mqtt连接
	 */
	void disConn() {
		try {
			mqttAsyncClient.disconnect().waitForCompletion();
		} catch (MqttException e) {
			log.warning("++断开mqtt连接失败：" + ExceptionUtil.stacktraceToString(e));
			try {
				mqttAsyncClient.disconnectForcibly();
			} catch (MqttException ex) {
				log.warning("++强制断开mqtt连接失败：" + ExceptionUtil.stacktraceToString(ex));
			}
		}
	}

	@Override
	public void release() {
		disConn();
		try {
			mqttAsyncClient.close(true);
		} catch (MqttException e) {
			log.warning("++强制关闭mqtt客户端失败：" + ExceptionUtil.stacktraceToString(e));
		}
	}

	@Override
	public short getPriority() {
		return this.priority;
	}

	public void sendMsg(MachineMsg msg) {
		try {
			MqttMsgContext ctx = (MqttMsgContext) msg.getCtx();
			MachineProtocolParser msgParser = getProtoParser(ctx);
			ctx.setServerId(serverId);
			ctx.setMqttMsgId(11);
			byte[] bts = msgParser.parser(msg);
			MqttMessage mqttMsg = new MqttMessage(bts);
			mqttMsg.setQos(qos);
			log.info("+++发送指令,内容：" + mqttMsg.toString());
			mqttAsyncClient.publish(topicTag + MqttTopic.TOPIC_LEVEL_SEPARATOR + msg.getMachineCode(), mqttMsg, null, null);
		} catch (MqttException e) {
			log.warning("++发布消息：[" + msg + "],失败！，原因：ReasonCode=" + e.getReasonCode());
			throw new CommunicationException(e.getReasonCode(), "MqttBrokeAgent 发布消息失败！", e);
		}
	}


	/**
	 * Mqtt回调默认实现
	 */
	class DefaultMqttCallBack implements MqttCallbackExtended {
		@Override
		public void connectionLost(Throwable cause) {
			log.warning("+++连接丢失，原因：" + ExceptionUtil.stacktraceToOneLineString(cause));
		}

		@Override
		public void messageArrived(String topic, MqttMessage message) {
			log.info("主题：" + topic + ",消息到达：" + message);

		}

		@Override
		public void deliveryComplete(IMqttDeliveryToken token) {
		}

		@Override
		public void connectComplete(boolean b, String s) {
			//消息订阅设置
			subscribe();
			log.info("+++连接成功,重新订阅主题成功!");
		}
	}
}
