package com.pig4cloud.pig.order.order.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>Title: GoodsOrder</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月27日
 * @since 1.8
 */
@Data
@ApiModel("商品订单实体")
public class GoodsOrder {
	@ApiModelProperty("订单编号")
	private String orderCode;
	@ApiModelProperty("订单类型 0 代理商 1 消费者")
	private Integer orderType;
	@ApiModelProperty("订单时间")
	private LocalDateTime orderCreateTime;
	@ApiModelProperty("订单金额")
	private BigDecimal orderTotalMoney;
	@ApiModelProperty("柜子id")
	private Integer machineId;
	@ApiModelProperty("支付方式 0 微信 1 支付宝 2 网银")
	private Integer payWay;
	@ApiModelProperty("下单人")
	private String creatorName;
	@ApiModelProperty("柜子名称")
	private String machineName;
	@ApiModelProperty("柜子归属")
	private String belongsUser;
	@ApiModelProperty("商品名称")
	private String goodsName;
	@ApiModelProperty("商品图片")
	private String goodsAttId;
	@ApiModelProperty("商品规格1")
	private String goodsSpe1;
	@ApiModelProperty("商品规格2")
	private String goodsSpe2;
	@ApiModelProperty("零售价")
	private BigDecimal salePrice;
	@ApiModelProperty("统批价")
	private BigDecimal tradePrice;
	@ApiModelProperty("购买数量")
	private Integer payNum;
	@ApiModelProperty("实付金额")
	private BigDecimal payPrice;


	@ApiModelProperty("商品分组查询条件")
	private Integer groupRelId;
	@ApiModelProperty("商品类目查询条件")
	private Integer category;
	@ApiModelProperty("订单开始时间")
	private String orderCreateStartTime;
	@ApiModelProperty("订单结束时间")
	private String orderCreateEndTime;

}
