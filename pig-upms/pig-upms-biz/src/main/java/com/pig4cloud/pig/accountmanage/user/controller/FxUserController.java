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
package com.pig4cloud.pig.accountmanage.user.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.accountmanage.user.entity.User;
import com.pig4cloud.pig.accountmanage.user.service.UserService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户管理
 *
 * @author zhuzubin
 * @date 2019-04-05 22:47:03
 */
@Api(value = "fxuser", tags = "用户管理")
@RestController
@AllArgsConstructor
@RequestMapping("/fxuser")
public class FxUserController {

	private final UserService userService;

	/**
	 * 简单分页查询
	 *
	 * @param page 分页对象
	 * @param user 用户管理
	 * @return
	 */
	@ApiOperation(value = "分页查询", notes = "分页查询")
	@GetMapping("/page")
	public R<IPage<User>> getUserPage(Page<User> page, User user) {
		return new R<>(userService.getUserPage(page, user));
	}


	/**
	 * 通过id查询单条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id查询单条记录", notes = "通过id查询单条记录")
	@GetMapping("/{id}")
	public R<User> getById(@PathVariable("id") Integer id) {
		return new R<>(userService.getById(id));
	}

	/**
	 * 新增记录
	 *
	 * @param user
	 * @return R
	 */
	@ApiOperation(value = "新增记录", notes = "新增记录")
	@SysLog("新增用户管理")
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('user_user_add')")
	public R save(@RequestBody User user) {
		return new R<>(userService.save(user));
	}

	/**
	 * 修改记录
	 *
	 * @param user
	 * @return R
	 */
	@ApiOperation(value = "修改记录", notes = "修改记录")
	@SysLog("修改用户管理")
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('user_user_edit')")
	public R update(@RequestBody User user) {
		return new R<>(userService.updateById(user));
	}

	/**
	 * 通过id删除一条记录
	 *
	 * @param id
	 * @return R
	 */
	@ApiOperation(value = "通过id删除一条记录", notes = "通过id删除一条记录")
	@SysLog("删除用户管理")
	@DeleteMapping("/{id}")
//	@PreAuthorize("@pms.hasPermission('user_user_del')")
	public R removeById(@PathVariable Integer id) {
		return new R<>(userService.removeById(id));
	}

	@ApiModelProperty("更新状态")
	@PutMapping("/updateState/{id}")
	public R updateState(@PathVariable Integer id, @RequestParam Integer state) {
		User user = userService.getById(id);
		if (user == null)
			return new R(Boolean.FALSE, "用户不存在");
		user.setState(state);
		return new R(userService.updateById(user));
	}
}
