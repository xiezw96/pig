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
@ApiModel("代理商新增设备故障提交实体")
public class SalesMachineFaultAgentAddDTO {
	@ApiModelProperty("销售机器id")
	private Integer machineId;
	@ApiModelProperty("故障类型 0 整机故障 1 货道故障 2 其他故障")
	private Integer faultType;
	@ApiModelProperty("货道id")
	private Integer aisleId;
	@ApiModelProperty("故障描述")
	private String remark;
	@ApiModelProperty("图片信息，多个逗号隔开")
	private String pics;
}
