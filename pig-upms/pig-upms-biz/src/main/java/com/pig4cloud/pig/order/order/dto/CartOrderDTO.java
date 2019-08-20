package com.pig4cloud.pig.order.order.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>Title: AgentCartOrderDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月16日
 * @since 1.8
 */
@Data
public class CartOrderDTO {
	@ApiModelProperty("买家留言")
	private String message;
	@ApiModelProperty(value = "购物车id", notes = "选择确认结算的商品对应的购物车id，为空表示购物车全部商品")
	private List<Integer> cartIds;
	@ApiModelProperty("收货地址")
	private Integer addressId;
	@ApiModelProperty("用户标识")
	private String openid;
}
