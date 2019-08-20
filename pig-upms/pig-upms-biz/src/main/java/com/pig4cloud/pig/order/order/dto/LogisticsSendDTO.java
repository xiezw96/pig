package com.pig4cloud.pig.order.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>Title: LogisticsSendDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年07月03日
 * @since 1.8
 */
@Data
@ApiModel("订单发货实体")
public class LogisticsSendDTO {
	@ApiModelProperty("订单id")
	private Integer id;
	@ApiModelProperty("物流公司")
	private String logisticsCompany;
	@ApiModelProperty("物流单号")
	private String logisticsCode;
}
