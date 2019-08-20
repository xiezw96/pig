package com.pig4cloud.pig.admin.service;

/**
 * <p>Title: SMSCodeService</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月21日
 * @since 1.8
 */
public interface SMSCodeService {

	/**
	 * 获取验证码
	 * <p>Title: get</p>
	 * <p>Description: </p>
	 *
	 * @return boolean
	 * @date 2019年04月21日
	 * @author 余新引
	 */
	boolean get(String phone, String type);

	/**
	 * 验证并创建token
	 * <p>Title: verify</p>
	 * <p>Description: </p>
	 *
	 * @return String
	 * @date 2019年04月21日
	 * @author 余新引
	 */
	String getToken(String phone, String type, String code);

	/**
	 * 验证
	 * <p>Title: verify</p>
	 * <p>Description: </p>
	 *
	 * @return String
	 * @date 2019年04月21日
	 * @author 余新引
	 */
	boolean verify(String phone, String type, String code);

	/**
	 * 验证token
	 * <p>Title: verify</p>
	 * <p>Description: </p>
	 *
	 * @return String
	 * @date 2019年04月21日
	 * @author 余新引
	 */
	boolean verifyToken(String phone, String type, String token);
}
