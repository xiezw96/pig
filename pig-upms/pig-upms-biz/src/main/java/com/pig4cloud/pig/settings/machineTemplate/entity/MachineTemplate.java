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
package com.pig4cloud.pig.settings.machineTemplate.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.pig4cloud.pig.settings.machineTemplateDetail.entity.MachineTemplateDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 补货模板
 *
 * @author zhuzubin
 * @date 2019-07-08 15:38:50
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("补货模板实体")
@TableName("fx_machine_template")
public class MachineTemplate extends Model<MachineTemplate> {
private static final long serialVersionUID = 1L;

    /**
   * id
   */
	@TableId(type = IdType.AUTO, value = "id")
	@ApiModelProperty("id")
    private Integer id;
    /**
   * 模板名称
   */
	@ApiModelProperty("模板名称")
    private String name;
    /**
   * 0 私有 1 公共
   */
	@ApiModelProperty("模板类型 0 私有 1 公共")
    private Integer type;
    /**
   * 设备类型
   */
	@ApiModelProperty("设备类型Id")
    private Integer machineType;
    /**
   * 0启用 1停用
   */
	@ApiModelProperty("模板状态 0启用 1停用")
    private Integer status;
    /**
   * 创建人
   */
	@ApiModelProperty("创建人")
    private Integer creatorId;
    /**
   * 创建时间
   */
	@ApiModelProperty("创建日期")
    private LocalDateTime createTime;

	@ApiModelProperty("设备类型名称")
	@TableField(exist = false)
	private String machineTypeName;

	@ApiModelProperty("设备ID")
	@TableField(exist = false)
	private Integer machineId;

	@ApiModelProperty("模板明细")
	@TableField(exist = false)
	private List<MachineTemplateDetail> templateDetails;
}
