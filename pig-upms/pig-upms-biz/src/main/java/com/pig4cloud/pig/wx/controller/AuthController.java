/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.wx.controller;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.pay.weixin.sdk.WXPayConfig;
import com.pig4cloud.pig.pay.weixin.sdk.WXPayUtil;
import com.pig4cloud.pig.utils.PasswordEncoder;
import com.pig4cloud.pig.wx.entity.AccessToken;
import com.pig4cloud.pig.wx.entity.Ticket;
import com.pig4cloud.pig.wx.service.AuthService;
import com.pig4cloud.pig.wx.service.EventService;
import feign.Feign;
import feign.codec.StringDecoder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 代理商管理
 *
 * @author zhuzubin
 * @date 2019-04-05 22:48:31
 */
@Api(value = "wx", tags = "微信认证")
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/wx")
public class AuthController {
	private final WXPayConfig wxPayConfig;
	private final EventService eventService;
	private final String token = "fxly7896";
	private final RedisTemplate<String, String> redisTemplate;

	@GetMapping("/auth")
	@ApiOperation(value = "微信AUTH认证", notes = "微信接入认证")
	public String wxAuth(@RequestParam("signature") String signature,
						 @RequestParam("echostr") String echoStr,
						 @RequestParam("timestamp") String timestamp,
						 @RequestParam("nonce") String nonce) {

		return validateSign(timestamp, nonce, signature) ? echoStr : "";
	}

	@PostMapping("/auth")
	@ApiOperation("微信公众号处理服务端")
	public String wxServer(@RequestParam("signature") String signature,
						   @RequestParam("timestamp") String timestamp,
						   @RequestParam("nonce") String nonce,
						   @RequestBody String xml) {
		if (validateSign(timestamp, nonce, signature)) {
			try {
				return eventService.acceptEvent(xml);
			} catch (Exception e) {
				log.error("微信公众号处理服务端异常，{}", e.getMessage(), e);
				return "";
			}
		}
		return "";
	}

	private boolean validateSign(String timestamp, String nonce, String signature) {
		if (!StringUtils.isEmpty(timestamp) && !StringUtils.isEmpty(nonce)) {
			String[] parms = new String[]{token, timestamp, nonce};// 将需要字典序排列的字符串放到数组中
			Arrays.sort(parms);// 按照api要求进行字典序排序
			// 第二步:将三个参数字符串拼接成一个字符串进行sha1加密
			// 拼接字符串
			String parmsString = "";// 注意，此处不能=null。
			for (int i = 0; i < parms.length; i++) {
				parmsString += parms[i];
			}
			String validSignature = PasswordEncoder.convertSha1(parmsString);
			return signature.equals(validSignature);
		}
		return false;
	}

	@ApiOperation("跳转微信认证")
	@GetMapping("/oauth2/authorize")
	public ResponseEntity authorize(@RequestParam("state") String state, @RequestParam("scope") String scope, @RequestParam("response_type") String response_type, @RequestParam("redirect_uri") String redirect_uri) throws Exception {
		String appId = wxPayConfig.getAppID();
		String uri = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type={2}&scope={3}&state={4}#wechat_redirect";
		return ResponseEntity.status(HttpStatus.FOUND).header("Location", MessageFormat.format(uri, appId, redirect_uri, response_type, scope, state)).build();
	}

	@ApiOperation("微信获取用户基本信息")
	@GetMapping("/baseapi/openid")
	public R getOpenId(@RequestParam("code") String code) {
		// 获取access_token
		String accessTokenStr = getApi().getAccessToken(wxPayConfig.getAppID(), wxPayConfig.getKey(), code);
		if (StringUtils.isEmpty(accessTokenStr)) {
			return new R(Boolean.FALSE, "调用微信接口失败");
		}
		AccessToken accessToken = JSONUtil.toBean(accessTokenStr, AccessToken.class);
		if (StringUtils.isNotEmpty(accessToken.getErrorcode()) && !"0".equals(accessToken.getErrorcode())) {
			return new R(Boolean.FALSE, accessToken.getErrmsg());
		}
		return new R(accessToken.getOpenid());
	}

	@ApiOperation("微信获取用户基本信息")
	@PostMapping("/bin/shorturl")
	public R getShorturl(@RequestBody String url) {
		Map<String, String> param = new HashMap<>();
		param.put("action", "long2short");
		param.put("long_url", url);
		return new R(getApi().getShorturl(getAccessToken(), JSONUtil.toJsonStr(param)));
	}


	@ApiOperation("微信获取用户基本信息")
	@GetMapping("/jsapi/signature")
	public R<Map<String, String>> getSignature(@RequestParam("url") String url) {
		String ticketStr = getApi().getTicket(getAccessToken(), "jsapi");
		if (StringUtils.isEmpty(ticketStr)) {
			return new R(Boolean.FALSE, "获取ticketStr失败");
		}
		Ticket ticket = JSONUtil.toBean(ticketStr, Ticket.class);
		if (StringUtils.isNotEmpty(ticket.getErrorcode()) && !"0".equals(ticket.getErrorcode())) {
			return new R(Boolean.FALSE, ticket.getErrmsg());
		}
		String nonceStr = WXPayUtil.generateNonceStr();
		String jsapi_ticket = ticket.getTicket();
		String timestamp = (System.currentTimeMillis() / 1000) + "";
		Map<String, String> signMap = new HashMap<>();
		signMap.put("appId", wxPayConfig.getAppID());
		signMap.put("timestamp", timestamp);
		signMap.put("nonceStr", nonceStr);
		signMap.put("signature", DigestUtils.sha1Hex(Arrays.asList(
			new String[]{"noncestr=" + nonceStr, "jsapi_ticket=" + jsapi_ticket, "timestamp=" + timestamp, "url=" + url})
			.stream()
			.sorted()
			.reduce((a, b) -> a + "&" + b)
			.orElse("")));
		return new R(signMap);
	}

	private String getAccessToken() {
		String key = "WX_ACCESSTOKEN_CACHE";
		String token = redisTemplate.opsForValue().get(key);
		if (token == null || StringUtils.isEmpty(token)) {
			AuthService authService = getApi();
			String accessTokenStr = authService.getAccessToken(wxPayConfig.getAppID(), wxPayConfig.getKey());
			if (StringUtils.isEmpty(accessTokenStr)) {
				throw new RuntimeException("获取access_token失败");
			}
			AccessToken accessToken = JSONUtil.toBean(accessTokenStr, AccessToken.class);
			if (StringUtils.isNotEmpty(accessToken.getErrorcode())) {
				throw new RuntimeException("errorCode:" + accessToken.getErrorcode() + ",errorMsg:" + accessToken.getErrmsg());
			}
			token = accessToken.getAccess_token();
			redisTemplate.opsForValue().set(key, token, accessToken.getExpires_in(), TimeUnit.SECONDS);
		}
		return token;

	}

	private AuthService getApi() {
		return Feign.builder()
			.decoder(new StringDecoder())
			.target(AuthService.class, "https://api.weixin.qq.com");
	}

}
