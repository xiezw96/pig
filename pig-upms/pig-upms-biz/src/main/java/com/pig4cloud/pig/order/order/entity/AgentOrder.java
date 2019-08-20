package com.pig4cloud.pig.order.order.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Title: AgentOrder</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月27日
 * @since 1.8
 */
@Data
@ApiModel("代理商采购单实体")
public class AgentOrder extends Order {
	@ApiModelProperty("商品种类数量")
	private Integer goodsTypeCount;
	@ApiModelProperty("商品数量")
	private Integer goodsCount;
	@ApiModelProperty("代理商名称")
	private String agentName;
	@ApiModelProperty("实付金额")
	private BigDecimal payPrice;
	@ApiModelProperty("类型 0 采购 1 激活")
	private Integer sourceType;
}
