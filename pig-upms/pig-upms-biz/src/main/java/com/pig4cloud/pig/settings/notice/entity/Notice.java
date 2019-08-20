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
package com.pig4cloud.pig.settings.notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

/**
 * 公告表
 *
 * @author yuxinyin
 * @date 2019-04-09 21:49:34
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("fx_notice")
@ApiModel("公告实体")
public class Notice extends Model<Notice> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(type=IdType.AUTO, value="id")
	private Integer id;
	@ApiModelProperty("标题")
	private String title;
	@ApiModelProperty("状态 0 启用 1 禁用")
	private Integer status;
	
	@ApiModelProperty("弹窗周期")
	private Integer period;
	@ApiModelProperty("弹窗开始时间")
	private LocalDateTime startTime;
	@ApiModelProperty("弹窗结束时间")
	private LocalDateTime endTime;
	@ApiModelProperty("公告内容")
	private String content;
	/**
	 * 操作人
	 */
	@ApiModelProperty(hidden=true)
	private Integer creatorId;
	@ApiModelProperty("操作时间")
	private LocalDateTime createDate;

}
