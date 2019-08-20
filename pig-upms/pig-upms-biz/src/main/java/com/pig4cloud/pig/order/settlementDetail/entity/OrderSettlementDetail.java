package com.pig4cloud.pig.order.settlementDetail.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>Title: OrderSettlementDetail</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月18日
 * @since 1.8
 */
@Data
public class OrderSettlementDetail {
	/**
	 * 订单id
	 */
	private Integer orderId;
	/**
	 * 订单编号
	 */
	private String orderCode;
	/**
	 * 订单明细id
	 */
	private Integer orderDetailId;
	/**
	 * 商品id
	 */
	private Integer goodsId;
	/**
	 * 商品名称
	 */
	private String goodsName;
	/**
	 * 商品图片
	 */
	private String goodsAttId;
	/**
	 * 商品规格1
	 */
	private String goodsSpe1;
	/**
	 * 商品规格2
	 */
	private String goodsSpe2;
	/**
	 * 零售价
	 */
	private BigDecimal salePrice;
	/**
	 * 统批价
	 */
	private BigDecimal tradePrice;
	/**
	 * 购买数量
	 */
	private Integer payNum;
	/**
	 * 实付金额
	 */
	private BigDecimal payPrice;
	/**
	 * 柜子id
	 */
	private Integer machineId;
	/**
	 * 消费者id
	 */
	private Integer userId;
	/**
	 * 消费者所属代理商
	 */
	private Integer userAgentId;
	/**
	 * 柜子所属代理商
	 */
	private Integer machineAgentId;
	/**
	 * 支付时间
	 */
	private LocalDateTime payTime;
	/**
	 * 订单下单时间
	 */
	private LocalDateTime orderCreateTime;
}
