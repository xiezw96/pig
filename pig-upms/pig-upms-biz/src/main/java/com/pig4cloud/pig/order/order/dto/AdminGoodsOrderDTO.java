package com.pig4cloud.pig.order.order.dto;

import io.swagger.annotations.ApiModel;
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
@ApiModel("总部添加采购单实体")
public class AdminGoodsOrderDTO {
	@ApiModelProperty("购买商品明细")
	private List<GoodsDetailDTO> goods;
	@ApiModelProperty("收货地址")
	private Integer addressId;
	@ApiModelProperty("代理商id")
	private Integer agentId;
}
