package com.pig4cloud.pig.settings.agentCombo.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>Title: SelectComboDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年05月16日
 * @since 1.8
 */
@Data
@ApiModel("选择激活套餐实体")
public class SelectComboDTO {
	@ApiModelProperty("套餐id")
	private Integer comboId;

	@ApiModelProperty("买家留言")
	private String message;
	@ApiModelProperty("收货地址")
	private Integer addressId;
}
