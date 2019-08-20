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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.admin.api.dto.RoleMenuDTO;
import com.pig4cloud.pig.admin.api.entity.SysRole;
import com.pig4cloud.pig.admin.service.SysRoleMenuService;
import com.pig4cloud.pig.admin.service.SysRoleService;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

/**
 * @author lengleng
 * @date 2019/2/1
 */
@Api(value = "role", tags = "角色管理")
@RestController
@AllArgsConstructor
@RequestMapping("/role")
public class RoleController {
	private final SysRoleService sysRoleService;
	private final SysRoleMenuService sysRoleMenuService;

	/**
	 * 通过ID查询角色信息
	 *
	 * @param id ID
	 * @return 角色信息
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "通过ID查询角色信息", notes = "通过ID查询角色信息")
	public R getById(@PathVariable @ApiParam("角色ID") Integer id) {
		return new R<>(sysRoleService.getById(id));
	}

	/**
	 * 添加角色
	 *
	 * @param sysRole 角色信息
	 * @return success、false
	 */
	@SysLog("添加角色")
	@PostMapping
	@PreAuthorize("@pms.hasPermission('sys_role')")
	@ApiOperation(value = "添加角色", notes = "添加角色息")
	public R save(@Valid @RequestBody @ApiParam("角色") SysRole sysRole) {
		sysRole.setCreateTime(LocalDateTime.now());
		sysRole.setUpdateTime(sysRole.getCreateTime());
		sysRole.setOperatorId(SecurityUtils.getUser().getId());
		return new R<>(sysRoleService.save(sysRole));
	}

	/**
	 * 修改角色
	 *
	 * @param sysRole 角色信息
	 * @return success/false
	 */
	@SysLog("修改角色")
	@PutMapping
	@PreAuthorize("@pms.hasPermission('sys_role')")
	@ApiOperation(value = "修改角色", notes = "修改角色")
	public R update(@Valid @RequestBody @ApiParam("角色") SysRole sysRole) {
		sysRole.setUpdateTime(LocalDateTime.now());
		sysRole.setOperatorId(SecurityUtils.getUser().getId());
		return new R<>(sysRoleService.updateById(sysRole));
	}

	/**
	 * 删除角色
	 *
	 * @param id
	 * @return
	 */
	@SysLog("删除角色")
	@DeleteMapping("/{id}")
	@PreAuthorize("@pms.hasPermission('sys_role')")
	@ApiOperation(value = "删除角色", notes = "删除角色")
	public R removeById(@PathVariable @ApiParam("角色ID") Integer id) {
		return new R<>(sysRoleService.removeRoleById(id));
	}

	/**
	 * 获取角色列表
	 *
	 * @return 角色列表
	 */
	@GetMapping("/list")
	@ApiOperation(value = "获取角色列表", notes = "获取角色列表")
	public R listRoles() {
		return new R<>(sysRoleService.list(Wrappers.emptyWrapper()));
	}


	/**
	 * 简单分页查询
	 *
	 * @param page    分页对象
	 * @param sysRole 系统角色
	 * @return
	 */
	@GetMapping("/page")
	@ApiOperation("分页查询角色信息")
	public R<IPage<SysRole>> getRolePage(Page<SysRole> page, SysRole sysRole) {
		return new R<>(sysRoleService.getRolePage(page, sysRole));
	}

	/**
	 * 更新角色菜单
	 *
	 * @param roleMenu 角色菜单实体
	 * @return success、false
	 */
	@SysLog("更新角色菜单")
	@PutMapping("/menu")
	@PreAuthorize("@pms.hasPermission('sys_role')")
	@ApiOperation(value = "更新角色菜单", notes = "更新角色菜单")
	public R saveRoleMenus(@RequestBody RoleMenuDTO roleMenu) {
		if (roleMenu.getRoleId() == null) {
			return new R(Boolean.FALSE, "角色ID不能为空");
		}
		SysRole sysRole = sysRoleService.getById(roleMenu.getRoleId());
		if (sysRole == null) {
			return new R(Boolean.FALSE, "角色不存在");
		}
		return new R<>(sysRoleMenuService.saveRoleMenus(sysRole.getRoleCode(), sysRole.getRoleId(), roleMenu.getMenuIds()));
	}
}
