/*
 *  Copyright (c) 2019-2020, 冷冷 (wangiegie@gmail.com).
 *  <p>
 *  Licensed under the GNU Lesser General Public License 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 * https://www.gnu.org/licenses/lgpl.html
 *  <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pig4cloud.pig.admin.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.UserDTO;
import com.pig4cloud.pig.admin.api.entity.SysUser;
import com.pig4cloud.pig.admin.service.SysUserService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.annotation.Inner;
import com.pig4cloud.pig.common.security.util.ClientDetailsThreadLocal;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@Api(value = "sysuser", tags = "系统用户管理")
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserController {
	private final SysUserService userService;

	/**
	 * 获取当前用户全部信息
	 *
	 * @return 用户信息
	 */
	@GetMapping(value = {"/info"})
	@ApiOperation(value = "获取当前用户全部信息", notes = "获取当前用户全部信息")
	public R info() {
		String username = SecurityUtils.getUser().getUsername();
		SysUser user = userService.getOne(Wrappers.<SysUser>query()
			.lambda().eq(SysUser::getUsername, username));
		if (user == null) {
			return new R<>(Boolean.FALSE, "获取当前用户信息失败");
		}
		return new R<>(userService.getUserInfo(user));
	}

	/**
	 * 获取指定用户全部信息
	 *
	 * @return 用户信息
	 */
	@Inner
	@ApiOperation(value = "获取指定用户全部信息", notes = "获取指定用户全部信息")
	@GetMapping("/info/{username}")
	public R info(@PathVariable @ApiParam("用户名") String username) {
		return info(username, "");
	}

	/**
	 * 通过用户名和用户类型查询用户、角色信息
	 *
	 * @param username 用户名
	 * @param usertype 用户类型
	 * @return R
	 */
	@Inner
	@ApiOperation(value = "通过用户名和用户类型查询用户、角色信息", notes = "通过用户名和用户类型查询用户、角色信息")
	@GetMapping("/info/{username}/{usertype}")
	public R info(@PathVariable("username") @ApiParam("用户名") String username
		, @PathVariable("usertype") @ApiParam("用户类型") String usertype) {
		Map<SFunction<SysUser, ?>, String> params = new HashMap();
		params.put(SysUser::getUsername, username);
		if (StringUtils.isNotEmpty(usertype))
			params.put(SysUser::getUsertype, usertype);
		SysUser user = userService.getOne(Wrappers.<SysUser>query()
			.lambda().allEq(params));
		if (user == null) {
			return new R<>(Boolean.FALSE, String.format("用户信息为空 %s", username));
		}
		return new R<>(userService.getUserInfo(user));
	}

	@Inner
	@ApiOperation("通过openid和用户类型查询用户、角色信息")
	@GetMapping("infoByWX/{openid}/{usertype}")
	public R infoByWX(@PathVariable("openid") @ApiParam("用户名") String openid
		, @PathVariable("usertype") @ApiParam("用户类型") String usertype) {
		Map<SFunction<SysUser, ?>, String> params = new HashMap();
		params.put(SysUser::getOperatorId, openid);
		if (StringUtils.isNotEmpty(usertype))
			params.put(SysUser::getUsertype, usertype);
		SysUser user = userService.getOne(Wrappers.<SysUser>query()
			.lambda().allEq(params));
		if (user == null) {
			return new R<>(Boolean.FALSE, String.format("用户信息为空 openid: %s", openid));
		}
		return new R<>(userService.getUserInfo(user));
	}


	/**
	 * 通过ID查询用户信息
	 *
	 * @param id ID
	 * @return 用户信息
	 */
	@ApiOperation(value = "通过ID查询用户信息")
	@GetMapping("/{id}")
	public R user(@PathVariable @ApiParam("用户ID") Integer id) {
		return new R<>(userService.getUserVoById(id));
	}

	/**
	 * 根据用户名查询用户信息
	 *
	 * @param username 用户名
	 * @return
	 */
	@ApiOperation(value = "根据用户名查询用户信息")
	@GetMapping("/details/{username}")
	public R user(@PathVariable @ApiParam("用户名") String username) {
		SysUser condition = new SysUser();
		condition.setUsername(username);
		return new R<>(userService.getOne(new QueryWrapper<>(condition)));
	}

	/**
	 * 删除用户信息
	 *
	 * @param id ID
	 * @return R
	 */
	@ApiOperation(value = "删除用户信息")
	@SysLog("删除用户信息")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_account')")
	public R userDel(@PathVariable @ApiParam("用户ID") Integer id) {
		SysUser sysUser = userService.getById(id);
		return new R<>(userService.removeUserById(sysUser));
	}

	/**
	 * 添加用户
	 *
	 * @param userDto 用户信息
	 * @return success/false
	 */
	@ApiOperation(value = "添加用户")
	@SysLog("添加用户")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_account')")
	public R user(@RequestBody @ApiParam("用户") UserDTO userDto) {
		userDto.setOperatorId(SecurityUtils.getUser().getId());
		userDto.setUpdateTime(LocalDateTime.now());
		userDto.setUsertype(ClientDetailsThreadLocal.INNER_USER_TYPE);
		return new R<>(userService.saveUser(userDto));
	}

	/**
	 * 更新用户信息
	 *
	 * @param userDto 用户信息
	 * @return R
	 */
	@ApiOperation(value = "更新用户信息")
	@SysLog("更新用户信息")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_account')")
	public R updateUser(@Valid @RequestBody @ApiParam("用户") UserDTO userDto) {
		userDto.setOperatorId(SecurityUtils.getUser().getId());
		userDto.setUpdateTime(LocalDateTime.now());
		userDto.setUsertype(ClientDetailsThreadLocal.INNER_USER_TYPE);
		return new R<>(userService.updateUser(userDto));
	}

	/**
	 * 分页查询用户
	 *
	 * @param page    参数集
	 * @param userDTO 查询参数列表
	 * @return 用户集合
	 */
	@ApiOperation(value = "分页查询用户")
	@GetMapping("/page")
	public R getUserPage(Page page, UserDTO userDTO) {
		userDTO.setUsertype(ClientDetailsThreadLocal.INNER_USER_TYPE);
		return new R<>(userService.getUserWithRolePage(page, userDTO));
	}

	/**
	 * 修改个人信息
	 *
	 * @param userDto userDto
	 * @return success/false
	 */
	@ApiOperation(value = "修改个人信息")
	@SysLog("修改个人信息")
	@PutMapping("/edit")
	public R updateUserInfo(@Valid @RequestBody @ApiParam("用户") UserDTO userDto) {
		return userService.updateUserInfo(userDto);
	}

	/**
	 * @param username 用户名称
	 * @return 上级部门用户列表
	 */
	@ApiOperation(value = "查询上级部门的用户信息")
	@ApiResponse(code = 200, response = SysUser.class, responseContainer = "List", message = "")
	@GetMapping("/ancestor/{username}")
	public R listAncestorUsers(@PathVariable @ApiParam("用户名称") String username) {
		return new R<>(userService.listAncestorUsersByUsername(username));
	}
}
