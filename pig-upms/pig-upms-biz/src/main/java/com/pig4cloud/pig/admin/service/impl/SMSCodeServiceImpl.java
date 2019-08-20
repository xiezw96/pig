package com.pig4cloud.pig.admin.service.impl;

import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.UUID;
import com.pig4cloud.pig.admin.service.SMSCodeService;
import com.pig4cloud.pig.admin.service.SMSSender;
import lombok.AllArgsConstructor;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * <p>Title: SMSCodeServiceImpl</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月21日
 * @since 1.8
 */
@Service
@AllArgsConstructor
public class SMSCodeServiceImpl implements SMSCodeService, EnvironmentAware {
	private final RedisTemplate<String, String> redisTemplate;
	private Environment environment;
	private final SMSSender smsSender;
	private static final String SMS_CODE_TEMPLATE_PROPERTY_KEY = "sms.template.%s";
	private static final String SMS_CODE_LIMIT_PROPERTY_KEY = "sms.limit.%s";
	private static final String SMS_CODE_COUNT_CACHE_KEY = "sms.code.count.cache.%s.%s";
	private static final String SMS_CODE_CACHE_KEY = "sms.code.cache.%s.%s";
	private static final String SMS_CODE_TOKEN_CACHE_KEY = "sms.code.token.cache.%s.%s";

	private static final int timeOutMin = 1, timeOutSec = timeOutMin * 60;

	@Override
	public boolean get(String phone, String type) {
		String oldCode = redisTemplate.opsForValue().get(String.format(SMS_CODE_CACHE_KEY, type, phone));
		if (StringUtils.hasText(oldCode)) {
			throw new RuntimeException("请稍后再试");
		}
		Integer dayNum = outofLimit(phone, type);

		String template = environment.getProperty(String.format(SMS_CODE_TEMPLATE_PROPERTY_KEY, type));
		if (StringUtils.isEmpty(template)) {
			throw new RuntimeException("业务类型（" + type + "）的短信模板未配置");
		}
		String smsCode = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));
//		String msg = String.format(template, smsCode);
		boolean result = smsSender.send(template,phone, smsCode,String.valueOf(timeOutMin));
		if (result) {
			// 存储短信
			redisTemplate.opsForValue().set(String.format(SMS_CODE_CACHE_KEY, type, phone), smsCode, timeOutSec, TimeUnit.SECONDS);
			if (dayNum != null && dayNum > 0) {
				addCount(phone, type);
			}
		}
		return result;
	}

	private Integer outofLimit(String phone, String type) {
		// 业务类型的每日单号码次数限制
		Integer dayNum = environment.getProperty(String.format(SMS_CODE_LIMIT_PROPERTY_KEY, type), Integer.class);
		Integer count  = getCount(phone, type);
		if (dayNum != null && dayNum > 0 && count != null && count.intValue() > dayNum) {
			throw new RuntimeException(String.format("该号码短信验证码超过%s次，请明日再试", dayNum));
		}
		return dayNum;
	}

	private Integer getCount(String phone, String type) {
		String countStr = redisTemplate.opsForValue().get(String.format(SMS_CODE_COUNT_CACHE_KEY, type, phone));
		if (StringUtils.hasText(countStr)) {
			return Integer.valueOf(countStr);
		}
		return null;
	}

	private void addCount(String phone, String type) {
		String key = String.format(SMS_CODE_COUNT_CACHE_KEY, type, phone);
		Integer count = getCount(phone, type);
		if (count == null) {
			long timeout = DateUtil.between(new Date(), DateUtil.endOfDay(new Date()), DateUnit.SECOND);
			redisTemplate.opsForValue().set(key, "0", timeout, TimeUnit.SECONDS);
		} else {
			redisTemplate.opsForValue().set(key, (count + 1) + "");
		}
	}

	@Override
	public boolean verify(String phone, String type, String code) {
		if (StringUtils.isEmpty(code) || StringUtils.isEmpty(type) || StringUtils.isEmpty(phone)) {
			return false;
		}
		return verifyAndDeleteCache(String.format(SMS_CODE_CACHE_KEY, type, phone), code);
	}

	@Override
	public String getToken(String phone, String type, String code) {
		if (verify(phone, type, code)) {
			String token = UUID.randomUUID().toString();
			// 存储令牌
			redisTemplate.opsForValue().set(String.format(SMS_CODE_TOKEN_CACHE_KEY, type, phone), token, timeOutSec,
					TimeUnit.SECONDS);
			return token;
		}
		return null;
	}

	@Override
	public boolean verifyToken(String phone, String type, String token) {
		if (StringUtils.isEmpty(token) || StringUtils.isEmpty(type) || StringUtils.isEmpty(phone)) {
			return false;
		}
		return verifyAndDeleteCache(String.format(SMS_CODE_TOKEN_CACHE_KEY, type, phone), token);
	}

	private boolean verifyAndDeleteCache(String key, String value) {
		if (StringUtils.isEmpty(key)) {
			return false;
		}
		boolean result = value.equals(redisTemplate.opsForValue().get(key));
		if (result) {
			redisTemplate.delete(key);
		}
		return result;
	}

	@Override
	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}
