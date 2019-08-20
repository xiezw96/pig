package com.pig4cloud.pig.order.orderDetail.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>Title: UserOrderDetail</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月28日
 * @since 1.8
 */
@Data
@ApiModel("用户订单明细实体")
public class UserOrderDetail {

	@ApiModelProperty("明细ID")
	private Integer id;
	@ApiModelProperty("订单id")
	private Integer orderId;
	@ApiModelProperty("商品id")
	private Integer goodsId;
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
	@ApiModelProperty("优惠券")
	private Integer voucherId;
	@ApiModelProperty("购买数量")
	private Integer payNum;
	@ApiModelProperty("实付金额")
	private BigDecimal payPrice;
	@ApiModelProperty(hidden = true)
	private Integer userAgentId;
	@ApiModelProperty(hidden = true)
	private List<Integer> machineAgentIds;
	@ApiModelProperty("归属")
	private String source;
	@ApiModelProperty("商品编码")
	private String goodsCode;
	@ApiModelProperty("柜子归属代理商ID")
	private Integer sourceAgentId;
	@ApiModelProperty("柜子归属代理商名称")
	private String sourceAgentName;
	@ApiModelProperty("订单编号")
	private String orderCode;
	@ApiModelProperty("订单创建时间")
	private LocalDateTime orderCreateTime;
	@ApiModelProperty("柜子编码")
	private String machineCode;
	@ApiModelProperty("柜子当前地址")
	private String machineCurrAddress;
	@ApiModelProperty("买家账号")
	private String userAccount;
	@ApiModelProperty("订单创建开始时间")
	private String orderCreateStartTime;
	@ApiModelProperty("订单创建结束时间")
	private String orderCreateEndTime;
	@ApiModelProperty("订单金额")
	private BigDecimal totalMoney;
}
