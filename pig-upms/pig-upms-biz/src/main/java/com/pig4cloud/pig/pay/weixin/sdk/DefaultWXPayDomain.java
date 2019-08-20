package com.pig4cloud.pig.pay.weixin.sdk;

/**
 * <p>Title: DefaultWXPayDomain</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月12日
 * @since 1.8
 */
public class DefaultWXPayDomain implements IWXPayDomain {

	@Override
	public void report(String domain, long elapsedTimeMillis, Exception ex) {

	}

	@Override
	public DomainInfo getDomain(WXPayConfig config) {
		return new DomainInfo(WXPayConstants.DOMAIN_API, true);
	}
}
