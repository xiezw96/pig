package com.pig4cloud.pig.common.security.util;

import org.springframework.security.oauth2.provider.TokenRequest;

/**
 * <p>Title: TokenRequestThreadLocal</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月02日
 * @since 1.8
 */
public class TokenRequestThreadLocal {
	private static final ThreadLocal<TokenRequest> threadLocal = new ThreadLocal<>();

	public static void set(TokenRequest tokenRequest) {
		threadLocal.set(tokenRequest);
	}

	public static TokenRequest get() {
		return threadLocal.get();
	}

	public static void remove() {
		threadLocal.remove();
	}

}
