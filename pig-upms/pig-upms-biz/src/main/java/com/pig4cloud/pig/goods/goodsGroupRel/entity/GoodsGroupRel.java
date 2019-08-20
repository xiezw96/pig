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
package com.pig4cloud.pig.goods.goodsGroupRel.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 商品分组关联表
 *
 * @author yuxinyin
 * @date 2019-04-09 21:42:14
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_goods_group_rel")
public class GoodsGroupRel extends Model<GoodsGroupRel> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(hidden=true)
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 商品ID
	 */
	@ApiModelProperty(hidden=true)
	private Integer goodsId;
	/**
	 * 分组ID
	 */
	private Integer goodsGroupId;

	/**
     * 商品名称
     */
	@ApiModelProperty(hidden=true)
    @TableField(exist=false)
    private String goodsName;
    /**
     * 商品编号
     */
	@ApiModelProperty(hidden=true)
    @TableField(exist=false)
    private String goodsCode;
    /**
     * 分组名称
     */
	@ApiModelProperty(hidden=true)
    @TableField(exist=false)
    private String groupName;
}
