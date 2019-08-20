package com.pig4cloud.pig.order.order.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>Title: AgentCartOrderDTO</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月16日
 * @since 1.8
 */
@Data
@ApiModel("消费者购物车结算保存实体")
public class UserMachineOrderDTO {
	@ApiModelProperty("用户标识")
	private String openid;
	@ApiModelProperty(value = "二维码内容", notes = "如果是在线商城，则为空")
	private String qrStr;
}
