package com.pig4cloud.pig.common.security.extension;

import org.springframework.security.core.userdetails.UserDetails;

public interface Authenticator {

	boolean support();

	void prepare();

	void complete();

	UserDetails authenticate(String userName, String userType);
}
