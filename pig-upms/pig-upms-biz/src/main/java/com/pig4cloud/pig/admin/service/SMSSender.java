package com.pig4cloud.pig.admin.service;

/**
 * <p>Title: SMSSender</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月21日
 * @since 1.8
 */
public interface SMSSender {
	boolean send(String templateId,String phone, String code,String times);
}
