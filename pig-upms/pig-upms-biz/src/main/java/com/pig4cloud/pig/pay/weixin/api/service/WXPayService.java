package com.pig4cloud.pig.pay.weixin.api.service;

import com.pig4cloud.pig.pay.weixin.api.entity.UnifiedOrderRequest;

import java.util.Map;

/**
 * <p>Title: WXPayService</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月14日
 * @since 1.8
 */
public interface WXPayService {
	/**
	 * 统一下单
	 * <p>Title: unifiedOrder</p>
	 * <p>Description: </p>
	 *
	 * @return com.pig4cloud.pig.pay.weixin.api.entity.UnifiedOrderResult
	 * @date 2019年06月14日
	 * @author 余新引
	 */
	Map<String, String> unifiedOrder(UnifiedOrderRequest reqData);
}
