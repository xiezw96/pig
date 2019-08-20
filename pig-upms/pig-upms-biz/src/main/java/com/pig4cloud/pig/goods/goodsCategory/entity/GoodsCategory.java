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
package com.pig4cloud.pig.goods.goodsCategory.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 商品类目表
 *
 * @author yuxinyin
 * @date 2019-04-09 21:42:42
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_goods_category")
public class GoodsCategory extends Model<GoodsCategory> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(hidden=true)
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 商品数量
	 */
	@ApiModelProperty(hidden=true)
	@TableField(exist=false)
	private String goodsNum;
	@ApiModelProperty(hidden=true)
	@TableField(exist=false)
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 排序值
	 */
	private Integer sort;
	/**
	 * 操作人
	 */
	@ApiModelProperty(hidden=true)
	private Integer creatorId;
	/**
	 * 操作时间
	 */
	@ApiModelProperty(hidden=true)
	private LocalDateTime createDate;

}
