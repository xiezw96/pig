package com.pig4cloud.pig.wx.entity;

public enum EventType {
	// 用户未关注时，进行关注后的事件推送
	subscribe,
	// 取消关注事件
	unsubscribe,
	// 用户已关注时的事件推送
	SCAN,
	// 上报地理位置事件
	LOCATION,
	// 点击菜单拉取消息时的事件推送
	CLICK,
	// 点击菜单跳转链接时的事件推送
	VIEW,
	// 打开微信小程序
	view_miniprogram
}
