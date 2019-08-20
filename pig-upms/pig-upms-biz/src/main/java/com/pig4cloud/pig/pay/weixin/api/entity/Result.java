package com.pig4cloud.pig.pay.weixin.api.entity;

import lombok.Data;

/**
 * <p>Title: Result</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月14日
 * @since 1.8
 */
@Data
public abstract class Result {
	/**
	 * 返回状态码
	 **/
	private String return_code;
	/**
	 * 返回信息
	 **/
	private String return_msg;
	/**
	 * 返回信息
	 **/
	private String retmsg;
	/**
	 * 错误代码
	 **/
	private String err_code;
	/**
	 * 错误代码描述
	 **/
	private String err_code_des;

}
