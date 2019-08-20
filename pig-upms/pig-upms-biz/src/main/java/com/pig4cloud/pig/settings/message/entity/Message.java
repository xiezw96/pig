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
package com.pig4cloud.pig.settings.message.entity;

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
@TableName("fx_message")
@ApiModel("用户消息实体")
public class Message extends Model<Message> {
	private static final long serialVersionUID = 1L;

	@TableId(type = IdType.AUTO, value = "id")
	@ApiModelProperty("id")
	private Integer id;
	@ApiModelProperty("用户id")
	private Integer userId;
	@ApiModelProperty("标题")
	private String title;
	@ApiModelProperty("阅读状态")
	private Integer readStatus;
	@ApiModelProperty("消息内容")
	private String content;
	@ApiModelProperty("操作人")
	private Integer creatorId;
	@ApiModelProperty("操作时间")
	private LocalDateTime createDate;

}
