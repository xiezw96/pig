package com.pig4cloud.pig.pay.weixin.api.service.impl;

import cn.hutool.json.JSONUtil;
import com.pig4cloud.pig.pay.weixin.api.entity.UnifiedOrderRequest;
import com.pig4cloud.pig.pay.weixin.api.entity.UnifiedOrderResult;
import com.pig4cloud.pig.pay.weixin.api.service.WXPayService;
import com.pig4cloud.pig.pay.weixin.sdk.WXPay;
import com.pig4cloud.pig.pay.weixin.sdk.WXPayConfig;
import com.pig4cloud.pig.pay.weixin.sdk.WXPayConstants;
import com.pig4cloud.pig.pay.weixin.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>Title: WXPayServiceImpl</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月14日
 * @since 1.8
 */
@Component
public class WXPayServiceImpl extends AbstractServiceImpl implements WXPayService {

	@Autowired
	private WXPayConfig wxPayConfig;
	private WXPay wxPay;

	@PostConstruct
	private void init() throws Exception {
		this.wxPay = new WXPay(wxPayConfig);
	}

	@Override
	public Map<String, String> unifiedOrder(UnifiedOrderRequest reqData) {
		if (reqData == null) {
			throw new RuntimeException("请求参数不能为空");
		}
		Map<String, String> reqMap = JSONUtil.toBean(JSONUtil.toJsonStr(reqData), Map.class);
		try {
			Map<String, String> result = wxPay.unifiedOrder(reqMap);
			UnifiedOrderResult resultObj = parseResult(UnifiedOrderResult.class, reqMap, result);
			if (!WXPayConstants.SUCCESS.equals(resultObj.getReturn_code())) {
				throw new RuntimeException(resultObj.getReturn_msg());
			}
			if (!WXPayConstants.SUCCESS.equals(resultObj.getResult_code())) {
				throw new RuntimeException(resultObj.getErr_code_des());
			}
			String signTypeStr = reqMap.get("sign_type");
			Map<String, String> signMap = new HashMap<>();
			signMap.put("appId", wxPayConfig.getAppID());
			signMap.put("timeStamp", (System.currentTimeMillis() / 1000) + "");
			signMap.put("nonceStr", resultObj.getNonce_str());
			signMap.put("package", "prepay_id=" + resultObj.getPrepay_id());
			signMap.put("signType", signTypeStr);
			WXPayConstants.SignType signType = WXPayConstants.MD5.equals(signTypeStr) ? WXPayConstants.SignType.MD5 : WXPayConstants.SignType.HMACSHA256;
			signMap.put("paySign", WXPayUtil.generateSignature(signMap, wxPayConfig.getKey(), signType));
			return signMap;
		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
