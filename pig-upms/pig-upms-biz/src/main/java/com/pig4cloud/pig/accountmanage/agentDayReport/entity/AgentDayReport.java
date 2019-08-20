package com.pig4cloud.pig.accountmanage.agentDayReport.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>Title: AgentDayReport</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年07月06日
 * @since 1.8
 */
@Data
@ApiModel("代理商日报表实体")
@EqualsAndHashCode(callSuper = true)
@TableName("fx_agent_day_report")
public class AgentDayReport extends Model<AgentDayReport> {
	@TableId(type = IdType.INPUT)
	@ApiModelProperty("id")
	private Integer id;
	@ApiModelProperty("代理商id")
	private Integer agentId;
	@ApiModelProperty("代理商名称")
	@TableField(exist = false)
	private String agentName;
	@ApiModelProperty("新用户数")
	private Integer newUserCount;
	@ApiModelProperty("流水")
	private BigDecimal total;
	@ApiModelProperty("线上流水")
	private BigDecimal onlineTotal;
	@ApiModelProperty("线下流水")
	private BigDecimal offlineTotal;
	@ApiModelProperty("发展数")
	private Integer developCount;
	@ApiModelProperty("已提现金额")
	private BigDecimal withdrawalApplyTotal;
	@ApiModelProperty("可提现金额")
	private BigDecimal withdrawalPrice;
	@ApiModelProperty("创建日期")
	private LocalDateTime createDate;

	@ApiModelProperty("创建开始日期")
	@TableField(exist = false)
	private String createStartDate;
	@ApiModelProperty("创建结束日期")
	@TableField(exist = false)
	private String createEndDate;
}
