package com.pig4cloud.pig.wx.entity;

import lombok.Data;

/**
 * <p>Title: Event</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月20日
 * @since 1.8
 */
@Data
public class Event {
	private String ToUserName;
	private String FromUserName;
	private String CreateTime;
	private MsgType MsgType;
	private EventType Event;
	private String EventKey;
}
