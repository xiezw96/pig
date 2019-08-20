package com.pig4cloud.pig.pay.weixin.api.service.impl;

import cn.hutool.json.JSONUtil;
import com.pig4cloud.pig.pay.weixin.api.entity.Result;
import com.pig4cloud.pig.pay.weixin.sdk.WXPayConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * <p>Title: AbstractServiceImpl</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月14日
 * @since 1.8
 */
@Slf4j
public class AbstractServiceImpl {

	protected <T extends Result> T parseResult(Class<T> resultCls, Map<String, String> reqData, Map<String, String> resultMap) {
		T orderResult = (T) JSONUtil.toBean(JSONUtil.parseFromMap(resultMap), resultCls);
		if (!WXPayConstants.SUCCESS.equals(orderResult.getReturn_code())) {
			String msg = orderResult.getReturn_msg() == null ? orderResult.getRetmsg() : orderResult.getReturn_msg();
			log.error("微信接口调用返回错误结果：{} - {}，请求参数：{}", orderResult.getReturn_code(), msg, JSONUtil.parseFromMap(reqData));
			throw new RuntimeException(msg);
		}
		return orderResult;
	}
}
