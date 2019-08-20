/*
 * Copyright (c) 2018-2025, lengleng All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. Neither the name of the pig4cloud.com developer nor
 * the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission. Author:
 * lengleng (wangiegie@gmail.com)
 */
package com.pig4cloud.pig.settings.message.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.settings.message.entity.Message;
import com.pig4cloud.pig.settings.message.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 系统消息
 *
 * @author zhuzubin
 * @date 2019-04-05 11:25:21
 */
@Api(value = "message", tags = "系统消息管理")
@RestController
@AllArgsConstructor
@RequestMapping("/message")
public class MessageController {

	private final MessageService messageService;

	/**
	 * 简单分页查询
	 *
	 * @param page    分页对象
	 * @param message 系统消息
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<Message>> getMessagePage(Page<Message> page, Message message) {
		message.setUserId(SecurityUtils.getUser().getId());
		return new R<>(messageService.getMessagePage(page, message));
	}

	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
	@GetMapping("/{id}")
	public R<Message> getById(@PathVariable("id") Integer id) {
		Message message = messageService.getById(id);
		if (message.getUserId() != SecurityUtils.getUser().getId()) {
			return new R(Boolean.FALSE, "不是您的消息，无法阅读");
		}
		message.setReadStatus(1);
		messageService.updateById(message);
		return new R<>(message);
	}

	/**
	 * 修改记录
	 * @param message
	 * @return R
	 */
	@ApiOperation(value = "修改消息信息", notes = "修改消息信息")
	@SysLog("修改消息信息")
	@PutMapping
	public R update(@RequestBody Message message) {
		message.setCreatorId(SecurityUtils.getUser().getId());
		message.setCreateDate(LocalDateTime.now());
		return new R<>(messageService.updateById(message));
	}
}
