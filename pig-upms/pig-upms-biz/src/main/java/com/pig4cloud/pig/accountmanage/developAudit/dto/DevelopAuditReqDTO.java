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
@ApiModel("发展奖励申请单请求实体")
@Data
public class DevelopAuditReqDTO {
	@ApiModelProperty("交易密码")
	private String transactionPwd;
}
