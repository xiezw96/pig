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
package com.pig4cloud.pig.settings.notice.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import com.pig4cloud.pig.settings.notice.entity.Notice;
import com.pig4cloud.pig.settings.notice.service.NoticeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * 公告信息
 *
 * @author zhuzubin
 * @date 2019-04-05 11:25:21
 */
@Api(value = "notice", tags = "公告管理")
@RestController
@AllArgsConstructor
@RequestMapping("/notice")
public class NoticeController {

	private final NoticeService noticeService;

	/**
	 * 简单分页查询
	 *
	 * @param page   分页对象
	 * @param notice 公告信息
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<Notice>> getNoticePage(Page<Notice> page, Notice notice) {
		return new R<>(noticeService.getNoticePage(page, notice));
	}

	/**
	 * 简单分页查询
	 *
	 * @param page   分页对象
	 * @param notice 公告信息
	 * @return
	 */
	@ApiOperation(value = "分页查询启用公告", notes = "分页查询启用公告")
	@GetMapping("/enabled/page")
	public R<IPage<Notice>> getEnableNoticePage(Page<Notice> page, Notice notice) {
		notice.setStatus(0);
		return new R<>(noticeService.getNoticePage(page, notice));
	}

	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
	@GetMapping("/{id}")
	public R<Notice> getById(@PathVariable("id") Integer id) {
		return new R<>(noticeService.getById(id));
	}

	/**
	 * 新增记录
	 *
	 * @param notice
	 * @return R
	 */
	@ApiOperation(value = "新增公告信息", notes = "新增公告信息")
	@SysLog("新增公告信息")
	@PostMapping
	public R save(@RequestBody Notice notice) {
		notice.setCreatorId(SecurityUtils.getUser().getId());
		notice.setCreateDate(LocalDateTime.now());
		return new R<>(noticeService.save(notice));
	}

	/**
	 * 修改记录
	 *
	 * @param notice
	 * @return R
	 */
	@ApiOperation(value = "修改公告信息", notes = "修改公告信息")
	@SysLog("修改公告信息")
	@PutMapping
	public R update(@RequestBody Notice notice) {
		notice.setCreatorId(SecurityUtils.getUser().getId());
		notice.setCreateDate(LocalDateTime.now());
		return new R<>(noticeService.updateById(notice));
	}

	/**
	 * 通过id删除一条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除一条记录", notes = "通过id删除一条记录")
	@SysLog("删除公告信息")
	@DeleteMapping("/{id}")
	public R removeById(@PathVariable Integer id) {
		return new R<>(noticeService.removeById(id));
	}

}
