package com.pig4cloud.pig.common.security.util;

import org.springframework.security.oauth2.provider.ClientDetails;

/**
 * <p>Title: TokenRequestThreadLocal</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月02日
 * @since 1.8
 */
public class ClientDetailsThreadLocal {
	private static final ThreadLocal<ClientDetails> threadLocal = new ThreadLocal<>();

	public static final String USER_TYPE_KEY = "userType";

	public static final String INNER_USER_TYPE = "inner";
	public static final String AGENT_USER_TYPE = "agent";

	public static void set(ClientDetails clientDetails) {
		threadLocal.set(clientDetails);
	}

	public static ClientDetails get() {
		return threadLocal.get();
	}

	public static void remove() {
		threadLocal.remove();
	}

}
