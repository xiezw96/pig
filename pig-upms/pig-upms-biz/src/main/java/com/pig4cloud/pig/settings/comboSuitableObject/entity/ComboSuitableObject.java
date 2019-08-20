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
package com.pig4cloud.pig.settings.comboSuitableObject.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 套餐适用对象
 *
 * @author zhuzubin
 * @date 2019-04-13 17:42:56
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_combo_suitable_object")
public class ComboSuitableObject extends Model<ComboSuitableObject> {
	private static final long serialVersionUID = 1L;

	/**
	* id
	*/
	@ApiModelProperty(hidden=true)
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	* 套餐id
	*/
	@ApiModelProperty(hidden=true)
	private Integer comboId;
	/**
	* 适用对象类型
	*/
	private Integer objectType;
	/**
	* 适用对象名称
	*/
	private Integer objectName;

}
