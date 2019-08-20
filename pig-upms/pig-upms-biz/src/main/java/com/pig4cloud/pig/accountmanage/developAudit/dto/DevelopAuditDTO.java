package com.pig4cloud.pig.accountmanage.developAudit.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>Title: DevelopAuditReqDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月28日
 * @since 1.8
 */
@ApiModel("发展奖励审核保存实体")
@Data
public class DevelopAuditDTO {
	@ApiModelProperty("申请单ID")
	private Integer id;
	@ApiModelProperty("审核状态，0 待审核 1 通过 2 拒绝")
	private Integer status;
	@ApiModelProperty("备注")
	private String remark;
}
