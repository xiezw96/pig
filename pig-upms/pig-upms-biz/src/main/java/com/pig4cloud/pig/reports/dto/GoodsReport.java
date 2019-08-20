package com.pig4cloud.pig.reports.dto;

import com.pig4cloud.pig.goods.goods.entity.Goods;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * <p>Title: GoodsReport</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月15日
 * @since 1.8
 */
@ApiModel("商品排行数据")
public class GoodsReport {
	@ApiModelProperty("商品")
	private Goods goods;
	@ApiModelProperty("指标总数（销量、浏览量等）")
	private int sum;
}
