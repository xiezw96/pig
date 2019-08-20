package com.pig4cloud.pig.wx.service;

import com.pig4cloud.pig.wx.entity.SubscribeEvent;

/**
 * <p>Title: ZsbytAcceptService</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月25日
 * @since 1.8
 */
public interface ZsbytAcceptService {

	String zsbyt(SubscribeEvent subscribeEvent, String eventKey) throws Exception;
}
