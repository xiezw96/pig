package com.pig4cloud.pig.admin.controller;

import com.pig4cloud.pig.admin.service.SMSCodeService;
import com.pig4cloud.pig.common.core.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>Title: ImageCodeController</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月08日
 * @since 1.8
 */
@RestController
@AllArgsConstructor
@RequestMapping("/smscode")
@Slf4j
@Api(value = "smscode", tags = {"短信验证码"})
public class SMSCodeController {

	private final SMSCodeService smsCodeService;

	@GetMapping
	@ApiOperation(value = "获取短信验证码", notes = "获取短信验证码")
	public R get(@RequestParam
				 @ApiParam(name = "phone", value = "需要发送的手机号码") String phone,
				 @RequestParam
				 @ApiParam(name = "type", value = "业务类型") String type) {
		return new R(smsCodeService.get(phone, type));
	}

	@GetMapping("/token")
	@ApiOperation(value = "验证并创建token", notes = "验证并创建token，token看业务服务要求")
	public R getToken(@RequestParam
					  @ApiParam(name = "phone", value = "需要发送的手机号码") String phone,
					  @RequestParam
					  @ApiParam(name = "type", value = "业务类型") String type,
					  @RequestParam
					  @ApiParam(name = "code", value = "短信验证码") String code) {
		return new R(smsCodeService.getToken(phone, type, code));
	}

	@GetMapping("/verify")
	@ApiOperation(value = "验证", notes = "验证，不返回token")
	public R verify(String phone, String type, String code) {
		return new R(smsCodeService.verify(phone, type, code));
	}

}
