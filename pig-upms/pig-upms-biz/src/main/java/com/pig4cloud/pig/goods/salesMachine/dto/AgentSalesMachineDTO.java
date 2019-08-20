package com.pig4cloud.pig.goods.salesMachine.dto;

import com.pig4cloud.pig.goods.salesMachine.entity.SalesMachine;
import com.pig4cloud.pig.goods.salesMachineAisle.entity.SalesMachineAisleDetail;
import com.pig4cloud.pig.goods.salesMachineAisleGoods.entity.SalesMachineAisleGoodsDetail;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

/**
 * <p>Title: AgentSalesMachineDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月18日
 * @since 1.8
 */
@Data
@EqualsAndHashCode(callSuper=false)
@ApiModel("代理商柜子实体")
public class AgentSalesMachineDTO extends SalesMachine {
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = -5590663493054327573L;
	@ApiModelProperty("货道明细")
	private List<SalesMachineAisleDetail> aisleDetails;
}
