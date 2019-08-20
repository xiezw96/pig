package com.pig4cloud.pig.order.orderDetail.dto;

import com.pig4cloud.pig.order.orderDetail.entity.OrderDetail;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>Title: AgentOrderDetailDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月30日
 * @since 1.8
 */
@Data
@ApiModel("代理商")
public class AgentOrderDetailDTO {
	@ApiModelProperty("代理商id")
	private String agentId;
	@ApiModelProperty("代理商名称")
	private String agentName;


	@ApiModelProperty("姓名")
	private String name;
	@ApiModelProperty("手机号")
	private String phone;
	@ApiModelProperty("省")
	private String privince;
	@ApiModelProperty("市")
	private String city;
	@ApiModelProperty("区")
	private String area;
	@ApiModelProperty("地址")
	private String address;

	private List<OrderDetail> detailList;
}
