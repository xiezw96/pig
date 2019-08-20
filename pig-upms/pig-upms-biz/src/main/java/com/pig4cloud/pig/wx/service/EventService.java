package com.pig4cloud.pig.wx.service;

/**
 * <p>Title: EventService</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月20日
 * @since 1.8
 */
public interface EventService {
	// 赠送避孕套
	String ZSBYT_PREFIX = "ZS-";

	String acceptEvent(String xml) throws Exception;
}
