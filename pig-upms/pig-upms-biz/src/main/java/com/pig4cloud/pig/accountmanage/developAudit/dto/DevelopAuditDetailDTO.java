package com.pig4cloud.pig.accountmanage.developAudit.dto;

import com.pig4cloud.pig.accountmanage.developAuditDetail.entity.DevelopAuditDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>Title: DevelopAuditDetailDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月28日
 * @since 1.8
 */
@ApiModel("发展奖励申请单明细实体")
@Data
@Builder
public class DevelopAuditDetailDTO {
	@ApiModelProperty("主键")
	private Integer id;
	@ApiModelProperty("代理商id")
	private Integer agentId;
	@ApiModelProperty("发展数量")
	private Integer developNum;
	@ApiModelProperty("奖励总金额")
	private BigDecimal totalMoney;
	@ApiModelProperty("0:待审核 1:通过 1:拒绝")
	private Integer auditStatus;
	@ApiModelProperty("操作人")
	private Integer creatorId;
	@ApiModelProperty("操作时间")
	private LocalDateTime createDate;
	@ApiModelProperty("发展代理商列表")
	private List<DevelopAuditDetail> developAuditDetails;
}
