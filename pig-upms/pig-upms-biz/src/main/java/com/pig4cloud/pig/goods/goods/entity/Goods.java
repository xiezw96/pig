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
package com.pig4cloud.pig.goods.goods.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.pig4cloud.pig.goods.goodsGroupRel.entity.GoodsGroupRel;
import com.pig4cloud.pig.goods.goodsPic.entity.GoodsPic;
import com.pig4cloud.pig.goods.goodsSpePrice.entity.GoodsSpePrice;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商品表
 *
 * @author yuxinyin
 * @date 2019-04-09 21:37:58
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_goods")
public class Goods extends Model<Goods> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@ApiModelProperty(hidden = true)
	@TableId(value = "id", type = IdType.AUTO)
	private Integer id;
	/**
	 * 商品类目
	 */
	private Integer category;
	/**
	 * 商品类目名称
	 */
	@ApiModelProperty(hidden = true)
	@TableField(exist = false)
	private String categoryName;

	/**
	 * 商品编码
	 */
	private String code;
	/**
	 * 商品名称
	 */
	private String name;
	/**
	 * 分享标题
	 */
	private String title;
	/**
	 * 商品卖点
	 */
	private String sellingPoints;
	/**
	 * 0:待上架 1:已上架  2:已下架
	 */
	private Integer status;
	@ApiModelProperty("商品主图")
	private String attId;
	/**
	 * 是否参与分润 0:否 1:是
	 */
	private Integer isShareProfit;
	/**
	 * 操作人
	 */
	@ApiModelProperty(hidden = true)
	private Integer creatorId;
	/**
	 * 操作时间
	 */
	@ApiModelProperty(hidden = true)
	private LocalDateTime createDate;
	/**
	 * 修改人
	 */
	@ApiModelProperty(hidden = true)
	private Integer modifierId;
	/**
	 * 修改时间
	 */
	@ApiModelProperty(hidden = true)
	private LocalDateTime updateTime;
	/**
	 * 商品图片列表
	 */
	@TableField(exist = false)
	private List<GoodsPic> picList;

	/**
	 * 商品价格列表
	 */
	@TableField(exist = false)
	private List<GoodsSpePrice> sepPriceList;

	@ApiModelProperty("商品分组查询条件")
	@TableField(exist = false)
	private Integer groupRelId;

	/**
	 * 商品分组列表
	 */
	@TableField(exist = false)
	private List<GoodsGroupRel> groupRelList;

	@ApiModelProperty("物流类型 0 包邮 1 按物流模板")
	private Integer logisticsType;
}
