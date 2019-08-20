package com.pig4cloud.pig.pay.weixin.api.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
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
@ApiModel("统一下单返回结果实体")
public class UnifiedOrderResult extends Result {
	@ApiModelProperty("公众账号ID")
	private String appid;
	@ApiModelProperty("设备号")
	private String device_info;
	@ApiModelProperty("时间戳")
	private String timeStamp;
	@ApiModelProperty("随机字符串")
	private String nonce_str;
	@ApiModelProperty(" 签名")
	private String sign;
	@ApiModelProperty("签名类型")
	private String signType;
	@ApiModelProperty("业务结果")
	private String result_code;
	@ApiModelProperty("交易类型")
	private String trade_type;
	@ApiModelProperty("预支付交易会话标识")
	private String prepay_id;
	@ApiModelProperty("二维码链接")
	private String code_url;
}
