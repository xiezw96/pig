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

package com.pig4cloud.pig.admin.api.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author lengleng
 * @since 2019/2/1
 */
@ApiModel("内部角色管理实体")
@Data
@EqualsAndHashCode(callSuper = true)
public class SysRole extends Model<SysRole> {

	private static final long serialVersionUID = 1L;

	@TableId(value = "role_id", type = IdType.AUTO)
	@ApiModelProperty("角色ID")
	private Integer roleId;
	@ApiModelProperty("角色名称")
	@NotBlank(message = "角色名称 不能为空")
	private String roleName;
	@ApiModelProperty("角色标识")
	@NotBlank(message = "角色标识 不能为空")
	private String roleCode;
	@ApiModelProperty("角色描述")
	@NotBlank(message = "角色描述 不能为空")
	private String roleDesc;
	@ApiModelProperty("操作人ID")
	private Integer operatorId;
	@TableField(exist = false)
	@ApiModelProperty("操作人名称")
	private String operatorName;
	@ApiModelProperty("创建时间")
	private LocalDateTime createTime;
	@ApiModelProperty("更新时间")
	private LocalDateTime updateTime;
	/**
	 * 删除标识（0-正常,1-删除）
	 */
	@ApiModelProperty("删除标识（0-正常,1-删除）")
	private String delFlag;

	@Override
	protected Serializable pkVal() {
		return this.roleId;
	}

}
