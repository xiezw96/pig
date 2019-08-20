package com.pig4cloud.pig.wx.service;

import feign.Param;
import feign.RequestLine;

/**
 * <p>Title: AuthService</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月20日
 * @since 1.8
 */
public interface AuthService {

	@RequestLine("GET /sns/oauth2/access_token?appid={appid}&secret={secret}&code={code}&grant_type=authorization_code")
	String getAccessToken(@Param("appid") String appid,
						  @Param("secret") String secret,
						  @Param("code") String code);

	@RequestLine("GET /cgi-bin/token?appid={appid}&secret={secret}&grant_type=client_credential")
	String getAccessToken(@Param("appid") String appid,
						  @Param("secret") String secret);

	@RequestLine("GET /cgi-bin/ticket/getticket?access_token={access_token}&type={type}")
	String getTicket(@Param("access_token") String access_token,
					 @Param("type") String type);

	@RequestLine("POST /cgi-bin/shorturl?access_token={access_token}")
	String getShorturl(@Param("access_token") String access_token,
					   String body);

}
