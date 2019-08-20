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
package com.pig4cloud.pig.accountmanage.userlog.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.userlog.entity.UserLog;
import com.pig4cloud.pig.accountmanage.userlog.service.UserLogService;
import com.pig4cloud.pig.common.core.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 消费者活动记录
 *
 * @author zhuzubin
 * @date 2019-04-05 22:47:03
 */
@Api(value = "fxuserlog", tags = "消费者活动记录")
@RestController
@AllArgsConstructor
@RequestMapping("/fxuserlog")
public class UserLogController {

	private final UserLogService userLogService;

	/**
	 * 简单分页查询
	 *
	 * @param page 分页对象
	 * @param userLog 消费者活动记录
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<UserLog>> getUserLogPage(Page<UserLog> page, UserLog userLog) {
		return new R<>(userLogService.getUserLogPage(page, userLog));
	}

}
