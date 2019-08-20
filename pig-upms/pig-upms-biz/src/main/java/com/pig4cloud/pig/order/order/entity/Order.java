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
package com.pig4cloud.pig.order.order.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.pig4cloud.pig.order.orderDetail.entity.OrderDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单表
 *
 * @author yuxinyin
 * @date 2019-04-17 18:56:09
 */
@Data
@Builder
@EqualsAndHashCode(callSuper = true)
@TableName("fx_order")
@ApiModel("订单实体")
@NoArgsConstructor
@AllArgsConstructor
public class Order extends Model<Order> {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Integer id;
	@ApiModelProperty("订单编号")
	private String code;
	@ApiModelProperty("订单类型 0 代理商 1 消费者")
	private Integer type;
	@ApiModelProperty("订单金额")
	private BigDecimal totalMoney;
	@ApiModelProperty("柜子id")
	private Integer machineId;
	@ApiModelProperty("货道id")
	private Integer aisleId;
	@ApiModelProperty("下单用户")
	private Integer creatorId;
	@ApiModelProperty("收货地址")
	private Integer shoppingAddress;
	@ApiModelProperty("下单时间")
	private LocalDateTime createTime;
	@ApiModelProperty("下单开始时间，查询条件")
	@TableField(exist = false)
	private String createStartTime;
	@ApiModelProperty("下单结束时间，查询条件")
	@TableField(exist = false)
	private String createEndTime;

	@ApiModelProperty("发货时间")
	private LocalDateTime sendTime;
	@ApiModelProperty("物流公司")
	private String logisticsCompany;
	@ApiModelProperty("物流单号")
	private String logisticsCode;
	@ApiModelProperty("物流操作人")
	private Integer logisticsOperatorId;
	@ApiModelProperty("订单状态 0 未付款  1 已付款未发货 2 已付款已发货 3 已退货未退款 4 已退货已退款")
	private Integer status;
	@ApiModelProperty("支付状态")
	private Integer payStatus;
	@ApiModelProperty("支付时间")
	private LocalDateTime payTime;
	@ApiModelProperty("买家留言")
	private String remark;
	@ApiModelProperty("是否结算")
	private Integer isSettlement;
	@ApiModelProperty("姓名")
	private String name;
	@ApiModelProperty("手机号")
	private String phone;
	@ApiModelProperty("省")
	private String privince;
	@ApiModelProperty("市")
	private String city;
	@ApiModelProperty("区")
	private String area;
	@ApiModelProperty("地址")
	private String address;
	@ApiModelProperty("创建人")
	private Integer operatorId;
	@ApiModelProperty("创建人名称")
	@TableField(exist = false)
	private String operatorName;
	@ApiModelProperty("创建时间")
	private LocalDateTime operationTime;
	@ApiModelProperty("创建开始时间，查询条件")
	@TableField(exist = false)
	private String operationStartTime;
	@ApiModelProperty("创建结束时间，查询条件")
	@TableField(exist = false)
	private String operationEndTime;


	@ApiModelProperty("订单明细")
	@TableField(exist = false)
	private List<OrderDetail> orderDetails;

}
