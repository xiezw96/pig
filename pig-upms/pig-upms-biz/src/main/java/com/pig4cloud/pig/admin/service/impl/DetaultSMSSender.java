package com.pig4cloud.pig.admin.service.impl;

import com.pig4cloud.pig.admin.service.SMSSender;
import lombok.extern.slf4j.Slf4j;

/**
 * <p>Title: DetaultSMSSender</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月21日
 * @since 1.8
 */
@Slf4j
public class DetaultSMSSender implements SMSSender {

	@Override
	public boolean send(String templateId,String phone, String code,String times) {
		return false;
	}
}
