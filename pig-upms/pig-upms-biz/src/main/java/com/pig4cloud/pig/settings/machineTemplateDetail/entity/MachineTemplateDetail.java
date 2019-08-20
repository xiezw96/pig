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
package com.pig4cloud.pig.settings.machineTemplateDetail.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 补货模板明细
 *
 * @author zhuzubin
 * @date 2019-07-08 15:39:06
 */
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@ApiModel("补货模板明细实体")
@TableName("fx_machine_template_detail")
	public class MachineTemplateDetail extends Model<MachineTemplateDetail> {
		private static final long serialVersionUID = 1L;

		/**
		 * id
		 */
		@TableId(type = IdType.AUTO, value = "id")
		@ApiModelProperty("id")
		private Integer id;
		/**
		 * 模板id
		 */
		@ApiModelProperty("模板ID")
		private Integer templateId;
		/**
		 * 货道编号
		 */
		@ApiModelProperty("货道编号")
		private String aisleCode;
		/**
		 * 商品编号
		 */
		@ApiModelProperty("商品编号")
		private Integer goodsId;
		/**
		 * 商品规格key
		 */
		@ApiModelProperty("商品规格ID")
		private Integer spePriceId;
		/**
		 * 商品规格key
		 */
		@ApiModelProperty("商品规格key")
		private String specPriceKey;
		/**
		 * 商品数量
		 */
		@ApiModelProperty("商品数量")
		private Integer num;

		@ApiModelProperty("商品名称")
		@TableField(exist=false)
		private String goodsName;

		@ApiModelProperty("商品编号")
		@TableField(exist=false)
		private String goodsCode;

		@ApiModelProperty("规格值1")
		@TableField(exist=false)
		private String speValue1;

		@ApiModelProperty("规格值2")
		@TableField(exist=false)
		private String speValue2;
}
