package com.pig4cloud.pig.common.security.extension;

import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.pig4cloud.pig.admin.api.dto.UserInfo;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.admin.api.feign.RemoteUserService;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.core.constant.SecurityConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.service.PigUser;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Title: DefaultAuthenticator</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月14日
 * @since 1.8
 */
@Service
@AllArgsConstructor
public class DefaultAuthenticator implements Authenticator {

	protected final RemoteUserService remoteUserService;

	@Override
	public boolean support() {
		return AuthenticationExt.getExt() == null;
	}

	@Override
	public void prepare() {

	}

	@Override
	public void complete() {

	}

	@Override
	public UserDetails authenticate(String userName, String userType) {
		R<UserInfo> result = remoteUserService.info(userName, userType, SecurityConstants.FROM_IN);
		UserDetails userDetails = getUserDetails(result);
		return userDetails;
	}

	protected PigUser getUserDetails(R<UserInfo> result) {
		return getUserDetails(null, result);
	}

	/**
	 * 构建userdetails
	 *
	 * @param result 用户信息
	 * @return
	 */
	protected PigUser getUserDetails(String resetPwd, R<UserInfo> result) {
		if (result == null || result.getData() == null) {
			throw new UsernameNotFoundException("用户不存在");
		}

		UserInfo info = result.getData();
		if (info.getSysUser().getIsLocked() != null && info.getSysUser().getIsLocked() == 1) {
			throw new LockedException("帐号被锁定");
		}
		Set<String> dbAuthsSet = new HashSet<>();
		if (ArrayUtil.isNotEmpty(info.getRoles())) {
			// 获取角色
			Arrays.stream(info.getRoles()).forEach(role -> dbAuthsSet.add(SecurityConstants.ROLE + role));
			// 获取资源
			dbAuthsSet.addAll(Arrays.asList(info.getPermissions()));

		}
		Collection<? extends GrantedAuthority> authorities
			= AuthorityUtils.createAuthorityList(dbAuthsSet.toArray(new String[0]));
		SysUser user = info.getSysUser();
		String pwd = StringUtils.isEmpty(resetPwd) ? user.getPassword() : resetPwd;
		// 构造security用户
		return new PigUser(user.getUserId(), user.getDeptId(), user.getUsername(), SecurityConstants.BCRYPT + pwd,
			StrUtil.equals(user.getLockFlag(), CommonConstants.STATUS_NORMAL), true, true, true, authorities);
	}
}
