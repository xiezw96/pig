package com.pig4cloud.pig.pay.weixin.sdk;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.InputStream;

/**
 * <p>Title: DefaultWXPayConfig</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月12日
 * @since 1.8
 */
@Component
public class DefaultWXPayConfig extends WXPayConfig {

	@Value("${pay.weixin.config.appid}")
	private String appID;
	@Value("${pay.weixin.config.mchid}")
	private String mchID;
	@Value("${pay.weixin.config.key}")
	private String key;
	@Value("${pay.weixin.config.notify_url}")
	private String notify_url;
	@Value("${pay.weixin.config.usesandbox:false}")
	private boolean useSandbox;

	@Override
	public String getAppID() {
		return this.appID;
	}

	@Override
	String getMchID() {
		return this.mchID;
	}

	@Override
	public String getKey() {
		return this.key;
	}

	@Override
	InputStream getCertStream() {
		return this.getClass().getResourceAsStream("/wx.cert");
	}

	@Override
	IWXPayDomain getWXPayDomain() {
		return new DefaultWXPayDomain();
	}

	@Override
	public String getNotify_url() {
		return this.notify_url;
	}

	@Override
	boolean isUseSandbox() {
		return this.useSandbox;
	}
}
