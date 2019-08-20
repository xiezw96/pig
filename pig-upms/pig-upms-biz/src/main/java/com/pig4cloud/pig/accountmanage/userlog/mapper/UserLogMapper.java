/*
 *    Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * Neither the name of the pig4cloud.com developer nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 * Author: lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.accountmanage.userlog.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.userlog.entity.UserLog;
import org.apache.ibatis.annotations.Param;

/**
 * 消费者活动记录
 *
 * @author zhuzubin
 * @date 2019-04-05 22:47:03
 */
public interface UserLogMapper extends BaseMapper<UserLog> {
	/**
	 * 消费者活动记录简单分页查询
	 *
	 * @param userLog 消费者活动记录
	 * @return
	 */
	IPage<UserLog> getUserLogPage(Page page, @Param("userLog") UserLog userLog);

	/**
	 * 根据微信openid获取今日活动记录次数
	 * <p>Title: getTodayUserLogByWxOpenidAndType</p>
	 * <p>Description: </p>
	 *
	 * @return int
	 * @date 2019年06月24日
	 * @author 余新引
	 */
	int getTodayUserLogByWxOpenidAndType(@Param("wxOpenid") String wxOpenid, @Param("type") String type);
}
