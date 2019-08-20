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
public class Ticket {
	private String errorcode;
	private String errmsg;

	private String ticket;
	private Long expires_in;
}
