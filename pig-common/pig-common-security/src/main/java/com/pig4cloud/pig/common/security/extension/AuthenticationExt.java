package com.pig4cloud.pig.common.security.extension;

import lombok.Builder;
import lombok.Data;

import java.util.Map;

/**
 * <p>Title: AuthenticationExt</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月14日
 * @since 1.8
 */
@Data
@Builder
public class AuthenticationExt {

	private final static ThreadLocal<AuthenticationExt> context = new ThreadLocal<>();

	// 登陆类型
	private AuthType authType;
	private Map<String, String> paramsMap;

	public static AuthenticationExt getExt() {
		return context.get();
	}

	public static void setExt(AuthenticationExt authenticationExt) {
		context.remove();
		context.set(authenticationExt);
	}

	public static void removeExt() {
		setExt(null);
	}
}
