package com.pig4cloud.pig.goods.salesMachineFault.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>Title: SalesMachineFaultDealDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年05月28日
 * @since 1.8
 */
@Data
@ApiModel("设备故障处理提交实体")
public class SalesMachineFaultDealDTO {
	@ApiModelProperty("id")
	private Integer id;
	@ApiModelProperty("状态 0 待处理 1 成功 2 失败")
	private Integer status;
	@ApiModelProperty("处理描述")
	private String dealRemark;
}
