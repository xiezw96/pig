package com.pig4cloud.pig.order.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class GoodsOrderDTO {
	@ApiModelProperty("买家留言")
	private String message;
	@ApiModelProperty("购买商品明细")
	private List<GoodsDetailDTO> goods;
	@ApiModelProperty("收货地址")
	private Integer addressId;
	@ApiModelProperty("用户标识")
	private String openid;
	@ApiModelProperty(hidden = true)
	@JsonIgnore
	private Integer adminId;
}
