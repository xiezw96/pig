package com.pig4cloud.pig.accountmanage.developAudit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * <p>Title: DevelopAwardDataDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月28日
 * @since 1.8
 */
@ApiModel(value = "发展奖励数据实体", description = "用于代理端展示个人发展收益相关数据")
@Data
@Builder
public class DevelopAwardDataDTO {
	@ApiModelProperty("待结算个数")
	private Integer settlementCount;
	@ApiModelProperty("累计发展奖励")
	private BigDecimal sumMonty;
	@ApiModelProperty("待结算到账奖励")
	private BigDecimal settlementMonty;
	@ApiModelProperty("待结算奖励")
	private BigDecimal originalSettlementMonty;
	@ApiModelProperty("通道费")
	private BigDecimal commission;
}
