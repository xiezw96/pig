package com.pig4cloud.pig.tools.sms;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.pig4cloud.pig.admin.service.SMSSender;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AlibabaSmsSender implements SMSSender {

	@Value("${alibaba.sms.region:cn-hangzhou}")
	String region;

	@Value("${alibaba.ackid:LTAIGRItbD2wce2M}")
	String accessKeyId;

	@Value("${alibaba.secret:93QkYi3qV1syX00xZEFjm9bcPPfeiz}")
	String secret;

	@Value("${alibaba.sms.domain:dysmsapi.aliyuncs.com}")
	String domain;

	@Value("${alibaba.sms.version:2017-05-25}")
	String version;

	@Value("${alibaba.sms.signame:疯象乐园}")
	String signName;

	static final String ACTION_SEND_SMS = "SendSms";

	static final String KEY_REGION_ID = "RegionId";
	static final String KEY_PHONE_NUMBER = "PhoneNumbers";
	static final String KEY_SIGN_NAME = "SignName";
	static final String KEY_TEMPLATE_CODE = "TemplateCode";
	static final String KEY_TEMPLATE_PARAM = "TemplateParam";

	static final String KEY_TEMPLATE_PARAM_CONTENT = "{\"code\":%s,\"time\":%s}";

	public boolean send(String templateId, String phone, String code, String time) {
		return sendSms(templateId, phone, code, time);
	}

	boolean sendSms(String templateId, String phone, String code, String time) {
		DefaultProfile profile = DefaultProfile.getProfile(region, accessKeyId, secret);
		IAcsClient client = new DefaultAcsClient(profile);
		CommonRequest request = new CommonRequest();
		request.setMethod(MethodType.POST);
		request.setDomain(domain);
		request.setVersion(version);
		request.setAction(ACTION_SEND_SMS);
		request.putQueryParameter(KEY_REGION_ID, region);
		request.putQueryParameter(KEY_PHONE_NUMBER, phone);
		request.putQueryParameter(KEY_SIGN_NAME, signName);
		request.putQueryParameter(KEY_TEMPLATE_CODE, templateId);
		request.putQueryParameter(KEY_TEMPLATE_PARAM, String.format(KEY_TEMPLATE_PARAM_CONTENT, code, time));
		CommonResponse response = null;
		try {
			response = client.getCommonResponse(request);
			JSONObject obj = JSONUtil.parseObj(response.getData());
			log.debug("alibaba msg:" + response.getData());
			return obj != null && "OK".equalsIgnoreCase(obj.getStr("Code"));
		} catch (Exception e) {
			if (response != null)
				log.warn("alibaba msg error:" + response);
			e.printStackTrace();
		}
		return false;
	}

}
