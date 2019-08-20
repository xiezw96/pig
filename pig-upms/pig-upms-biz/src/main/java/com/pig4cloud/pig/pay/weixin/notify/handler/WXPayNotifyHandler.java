/*
 *  Copyright (c) 2019-2020, 冷冷 (wangiegie@gmail.com).
 *  <p>
 *  Licensed under the GNU Lesser General Public License 3.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *  <p>
 * https://www.gnu.org/licenses/lgpl.html
 *  <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.pig4cloud.pig.pay.weixin.notify.handler;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.pig4cloud.pig.pay.weixin.notify.entity.WXPayNotify;
import com.pig4cloud.pig.pay.weixin.sdk.WXPayConfig;
import com.pig4cloud.pig.pay.weixin.sdk.WXPayConstants;
import com.pig4cloud.pig.pay.weixin.sdk.WXPayUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@AllArgsConstructor
@Component
public class WXPayNotifyHandler {

	private final WXPayConfig payConfig;
	private final RedisTemplate redisTemplate;
	private final String NOTIFY_CACHE_KEY = "WX_PAY_NOTIFY_CACHE";

	public ResponseEntity<String> notify(String xml, Function<WXPayNotify, Boolean> callback) {
		if (callback == null)
			throw new RuntimeException("回调不能为空");
		try {
			if (StringUtils.isEmpty(xml)) {
				return ResponseEntity.badRequest().build();
			}
			WXPayUtil.getLogger().info("微信支付通知：{}", xml);
			Map<String, String> notifyMap = WXPayUtil.xmlToMap(xml);
			JSONObject notifyJson = JSONUtil.parseFromMap(notifyMap);
			WXPayNotify notifyEntity = JSONUtil.toBean(notifyJson, WXPayNotify.class);
			if (WXPayConstants.SUCCESS.equals(notifyEntity.getResult_code()) && !redisTemplate.opsForHash().hasKey(NOTIFY_CACHE_KEY, notifyEntity.getTransaction_id())) {
				if (WXPayUtil.isSignatureValid(notifyMap, payConfig.getKey(), WXPayConstants.SignType.HMACSHA256)) {
					try {
						boolean flag = callback.apply(notifyEntity);
						if (flag) {
							// 以微信支付订单号作为交易流水
							redisTemplate.opsForHash().put(NOTIFY_CACHE_KEY, notifyEntity.getTransaction_id(), notifyEntity.getTransaction_id());
							return getSuccessResult();
						}
					} catch (Exception e) {
						WXPayUtil.getLogger().error("微信支付通知业务处理失败：{}", e.getMessage(), e);
						return getFailResult(e.getMessage());
					}
				} else {
					return getFailResult("签名验证错误");
				}
			}
			return getSuccessResult();
		} catch (Exception e) {
			WXPayUtil.getLogger().error("微信支付通知解析失败：{}", e.getMessage(), e);
			return ResponseEntity.ok().build();
		}
	}

	private ResponseEntity getSuccessResult() {
		return getResult(WXPayConstants.SUCCESS, "OK");
	}

	private ResponseEntity getFailResult(String return_msg) {
		WXPayUtil.getLogger().error("微信支付通知：{}", return_msg);
		return getResult(WXPayConstants.FAIL, return_msg);
	}

	private ResponseEntity getResult(String return_code, String return_msg) {
		try {
			Map<String, String> returnMap = new HashMap<>();
			returnMap.put("return_code", return_code);
			returnMap.put("return_msg", return_msg);
			return ResponseEntity.ok(WXPayUtil.mapToXml(returnMap));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}


}
