package com.pig4cloud.pig.pay.weixin.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

/**
 * <p>Title: UnifiedOrderResult</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月14日
 * @since 1.8
 */
@Data
@Builder
@ApiModel("统一下单请求实体")
public class UnifiedOrderRequest extends Result {
	@ApiModelProperty("商品描述")
	private String body;
	@ApiModelProperty("附加数据")
	private String attach;
	@ApiModelProperty("商户订单号")
	private String out_trade_no;
	@ApiModelProperty("标价币种")
	private String fee_type;
	@ApiModelProperty("标价金额")
	private String total_fee;
	@ApiModelProperty("终端IP")
	private String spbill_create_ip;
	@ApiModelProperty("交易起始时间")
	private String time_start;
	@ApiModelProperty("交易结束时间")
	private String time_expire;
	@ApiModelProperty("交易类型")
	private String trade_type;
	@ApiModelProperty("用户标识")
	private String openid;
	@ApiModelProperty("通知地址")
	private String notify_url;
}
