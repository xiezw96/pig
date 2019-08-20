package com.pig4cloud.pig.common.security.extension;

import com.pig4cloud.pig.admin.api.dto.UserInfo;
import com.pig4cloud.pig.admin.api.feign.RemoteUserService;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.PigUser;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>Title: WXAuthenticator</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月14日
 * @since 1.8
 */
@Service
public class WXAuthenticator extends DefaultAuthenticator {
	private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();

	public WXAuthenticator(RemoteUserService remoteUserService) {
		super(remoteUserService);
	}

	@Override
	public boolean support() {
		return AuthenticationExt.getExt() != null && AuthenticationExt.getExt().getAuthType() == AuthType.WX;
	}

	@Override
	public UserDetails authenticate(String openid, String userType) {
		// 前端传的实际上应该是微信OAuth2获取的access_token
		String access_token = AuthenticationExt.getExt().getParamsMap().get("password");
		// 查询用户
		R<UserInfo> result = remoteUserService.info(openid, userType, SecurityConstants.FROM_IN);

		PigUser userDetails = getUserDetails(ENCODER.encode(access_token), result);
		// TODO 验证微信token
		
		return userDetails;
	}
}
