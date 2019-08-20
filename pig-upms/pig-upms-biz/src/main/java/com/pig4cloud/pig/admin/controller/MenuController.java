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

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.pig4cloud.pig.admin.api.dto.MenuTree;
import com.pig4cloud.pig.admin.api.entity.SysMenu;
import com.pig4cloud.pig.admin.api.vo.MenuVO;
import com.pig4cloud.pig.admin.api.vo.TreeUtil;
import com.pig4cloud.pig.admin.service.SysMenuService;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.log.annotation.SysLog;
import com.pig4cloud.pig.common.security.util.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author lengleng
 * @date 2019/2/1
 */
@RestController
@AllArgsConstructor
@RequestMapping("/menu")
@Api(value = "menu", tags = "菜单管理服务")
public class MenuController {
	private final SysMenuService sysMenuService;

	/**
	 * 返回当前用户的树形菜单集合
	 *
	 * @return 当前用户的树形菜单
	 */
	@GetMapping
	@ApiOperation(value = "返回当前用户的树形菜单集合", notes = "返回当前用户的树形菜单集合")
	public R<List<MenuTree>> getUserMenu() {
		// 获取符合条件的菜单
		Set<MenuVO> all = new HashSet<>();
		SecurityUtils.getRoles()
			.forEach(roleId -> all.addAll(sysMenuService.getMenuByRoleId(roleId)));
		List<MenuTree> menuTreeList = all.stream()
			.filter(menuVo -> CommonConstants.MENU.equals(menuVo.getType()))
			.map(MenuTree::new)
			.sorted(Comparator.comparingInt(MenuTree::getSort))
			.collect(Collectors.toList());
		return new R<>(TreeUtil.buildByLoop(menuTreeList, -1));
	}

	/**
	 * 返回树形菜单集合
	 *
	 * @return 树形菜单
	 */
	@GetMapping(value = "/tree")
	@ApiOperation(value = "返回树形菜单集合", notes = "返回树形菜单集合")
	public R<List<MenuTree>> getTree() {
		return new R<>(TreeUtil.buildTree(sysMenuService.list(Wrappers.emptyWrapper()), -1));
	}

	/**
	 * 返回角色的菜单集合
	 *
	 * @param roleId 角色ID
	 * @return 属性集合
	 */
	@GetMapping("/tree/{roleId}")
	@ApiOperation(value = "返回树形菜单集合", notes = "返回树形菜单集合")
	public List<Integer> getRoleTree(@PathVariable @ApiParam("角色ID") Integer roleId) {
		return sysMenuService.getMenuByRoleId(roleId)
			.stream()
			.map(MenuVO::getMenuId)
			.collect(Collectors.toList());
	}

	/**
	 * 通过ID查询菜单的详细信息
	 *
	 * @param id 菜单ID
	 * @return 菜单详细信息
	 */
	@GetMapping("/{id}")
	@ApiOperation(value = "通过ID查询菜单的详细信息", notes = "通过ID查询菜单的详细信息")
	public R<SysMenu> getById(@PathVariable @ApiParam("菜单ID") Integer id) {
		return new R<>(sysMenuService.getById(id));
	}

	/**
	 * 新增菜单
	 *
	 * @param sysMenu 菜单信息
	 * @return success/false
	 */
	@SysLog("新增菜单")
	@PostMapping
//	@PreAuthorize("@pms.hasPermission('sys_menu_add')")
	@ApiOperation(value = "新增菜单", notes = "新增菜单")
	public R save(@Valid @RequestBody @ApiParam("系统菜单") SysMenu sysMenu) {
		return new R<>(sysMenuService.save(sysMenu));
	}

	/**
	 * 删除菜单
	 *
	 * @param id 菜单ID
	 * @return success/false
	 */
	@SysLog("删除菜单")
	@DeleteMapping("/{id}")
//	@PreAuthorize("@pms.hasPermission('sys_menu_del')")
	@ApiOperation(value = "删除菜单", notes = "删除菜单")
	public R removeById(@PathVariable @ApiParam("菜单ID") Integer id) {
		return sysMenuService.removeMenuById(id);
	}

	/**
	 * 更新菜单
	 *
	 * @param sysMenu
	 * @return
	 */
	@SysLog("更新菜单")
	@PutMapping
//	@PreAuthorize("@pms.hasPermission('sys_menu_edit')")
	public R update(@Valid @RequestBody @ApiParam("系统菜单") SysMenu sysMenu) {
		return new R<>(sysMenuService.updateMenuById(sysMenu));
	}

}
