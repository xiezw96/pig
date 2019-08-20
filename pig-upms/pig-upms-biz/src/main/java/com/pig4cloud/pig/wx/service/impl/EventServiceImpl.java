package com.pig4cloud.pig.wx.service.impl;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.pig4cloud.pig.pay.weixin.sdk.WXPayUtil;
import com.pig4cloud.pig.wx.entity.Event;
import com.pig4cloud.pig.wx.entity.SubscribeEvent;
import com.pig4cloud.pig.wx.service.EventService;
import com.pig4cloud.pig.wx.service.ZsbytAcceptService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: EventServiceImpl</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月20日
 * @since 1.8
 */
@Component
@Slf4j
public class EventServiceImpl implements EventService {

	@Autowired
	private ZsbytAcceptService zsbytAcceptService;

	@Value("${mp.weixin.config.welcome_speech:欢迎关注}")
	private String WELCOME_SPEECH;

	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	@Override
	public String acceptEvent(String xml) throws Exception {
		Map<String, String> map = WXPayUtil.xmlToMap(xml);
		JSONObject jsonObject = JSONUtil.parseFromMap(map);
		Event event = JSONUtil.toBean(jsonObject, Event.class);
		String returnStr = "";
		try {
			String cacheKey = "WX_EVENT_CACHE_" + event.getFromUserName() + "_" + event.getCreateTime();
			String cache = redisTemplate.opsForValue().get(cacheKey);
			if (StringUtils.hasText(cache))
				return "";
			redisTemplate.opsForValue().set(cacheKey, event.getCreateTime(), 60, TimeUnit.SECONDS);
			switch (event.getMsgType()) {
				case event:
					// 处理事件
					returnStr = acceptEvent(jsonObject, event);
					break;
				default:
					// 其他类型暂时都不处理
					returnStr = "";
			}
		} catch (Exception e) {
			log.error("处理微信消息异常，{}", e.getMessage(), e);
			return generateTextReturnMsg(event.getToUserName(), event.getFromUserName(), "服务器异常", System.currentTimeMillis() + "");
		}
		log.debug("微信消息处理结果：{}，请求：{}", returnStr, jsonObject);
		return returnStr;
	}

	protected String acceptEvent(JSONObject jsonObject, Event event) throws Exception {
		String returnStr = "";
		switch (event.getEvent()) {
			case subscribe:
				// 扫描带值二维码关注，其他情况为通过其他方式关注
				if (StringUtils.hasText(event.getEventKey())) {
					SubscribeEvent subscribeEvent = JSONUtil.toBean(jsonObject, SubscribeEvent.class);
					// 扫码关注后要去除前缀
					subscribeEvent.setEventKey(subscribeEvent.getEventKey().replace("qrscene_", ""));
					returnStr = acceptGift(subscribeEvent);
				} else
					returnStr = WELCOME_SPEECH;
				break;
			case SCAN:
				// 扫描带值二维码，已关注公众号
				if (StringUtils.hasText(event.getEventKey())) {
					returnStr = acceptGift(JSONUtil.toBean(jsonObject, SubscribeEvent.class));
				}
				break;
			default:
				return "";
		}
		returnStr = generateTextReturnMsg(event.getToUserName(), event.getFromUserName(), returnStr, System.currentTimeMillis() + "");
		return returnStr;
	}

	/**
	 * 处理关注送赠品逻辑
	 * <p>Title: acceptGift</p>
	 * <p>Description: </p>
	 *
	 * @return java.lang.String
	 * @date 2019年06月21日
	 * @author 余新引
	 */
	protected String acceptGift(SubscribeEvent subscribeEvent) {
		String eventKey = subscribeEvent.getEventKey();
		if (eventKey.startsWith(ZSBYT_PREFIX)) {
			try {
				return zsbytAcceptService.zsbyt(subscribeEvent, eventKey);
			} catch (Exception e) {
				log.error("关注领取赠品异常，{}，二维码参数：{}，subscribeEvent：{}", e.getMessage(), eventKey, JSONUtil.toJsonStr(subscribeEvent), e);
				return "赠品领取失败了，请换一台柜子试试吧";
			}
		} else {
			log.info("微信推送事件未处理：{}", JSONUtil.toJsonStr(subscribeEvent));
			return "活动无效，请扫描其他活动二维码";
		}
	}


	public String generateTextReturnMsg(String from, String to, String content, String createTime) throws Exception {
		Map<String, String> returnMap = generateReturnMsg(from, to, content, "text", createTime);
		returnMap.put("Content", content);
		return WXPayUtil.mapToXml(returnMap);
	}

	public Map<String, String> generateReturnMsg(String from, String to, String content, String type, String createTime) {
		Map<String, String> returnMap = new HashMap<>();
		returnMap.put("ToUserName", to);
		returnMap.put("FromUserName", from);
		returnMap.put("CreateTime", createTime);
		returnMap.put("MsgType", type);
		return returnMap;
	}
}
