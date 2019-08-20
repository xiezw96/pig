package com.pig4cloud.pig.order.order.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Title: GoodsDetailDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月03日
 * @since 1.8
 */
@ApiModel("直接购买商品信息")
@Data
public class GoodsDetailDTO {
	@ApiModelProperty("商品id")
	private Integer goodsId;
	@ApiModelProperty("商品规格id")
	private Integer goodsSpeId;
	@ApiModelProperty("价目key")
	private String spePriceKey;
	@ApiModelProperty("购买数量")
	private Integer num;

	@ApiModelProperty("总重量")
	@JsonIgnore
	private BigDecimal weight;
}
