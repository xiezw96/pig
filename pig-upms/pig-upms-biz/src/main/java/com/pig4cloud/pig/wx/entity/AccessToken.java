package com.pig4cloud.pig.wx.entity;

import lombok.Data;

/**
 * <p>Title: AccessToken</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月20日
 * @since 1.8
 */
@Data
public class AccessToken {
	private String errorcode;
	private String errmsg;

	private String access_token;
	private Long expires_in;
	private String refresh_token;
	private String openid;
	private String scope;
}
