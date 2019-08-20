package com.pig4cloud.pig.admin.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>Title: RoleMenuDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月23日
 * @since 1.8
 */
@Data
@ApiModel("角色菜单关联实体")
public class RoleMenuDTO {
	@ApiModelProperty("角色ID")
	private Integer roleId;
	@ApiModelProperty("菜单ID集合")
	private List<Integer> menuIds;
}
