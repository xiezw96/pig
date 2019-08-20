package com.pig4cloud.pig.admin.controller;

import cn.hutool.core.util.StrUtil;
import com.google.code.kaptcha.Producer;
import com.pig4cloud.pig.common.core.constant.CommonConstants;
import com.pig4cloud.pig.common.core.util.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

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
@RequestMapping("/imagecode")
@Slf4j
@Api(value = "imagecode", tags = {"图片验证码"})
public class ImageCodeController {
	private final Producer producer;
	private final RedisTemplate redisTemplate;

	@GetMapping(produces = MediaType.IMAGE_JPEG_VALUE)
	@ApiOperation(value = "获取验证图形图片", notes = "获取验证图形图片", produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity get(@RequestParam("r") @ApiParam("随机码，用于标识请求，验证时使用") String randomStr) {
		//生成验证码
		String text = producer.createText();
		BufferedImage image = producer.createImage(text);

		//保存验证码信息
		redisTemplate.opsForValue().set(CommonConstants.DEFAULT_CODE_KEY + randomStr, text, 60, TimeUnit.SECONDS);
		FastByteArrayOutputStream os = new FastByteArrayOutputStream();
		try {
			ImageIO.write(image, "jpeg", os);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
		return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(os.toByteArray());
	}

	@PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(value = "验证图形验证码", notes = "验证图形验证码", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public R<Boolean> check(@RequestParam("c") @ApiParam("验证码") String code, @RequestParam("r") @ApiParam("随机码，用于标识请求，验证时使用") String randomStr) {

		String key = CommonConstants.DEFAULT_CODE_KEY + randomStr;
		if (!redisTemplate.hasKey(key)) {
			return new R<>(Boolean.FALSE, "验证码错误");
		}

		Object codeObj = redisTemplate.opsForValue().get(key);

		if (codeObj == null) {
			return new R<>(Boolean.FALSE, "验证码错误");
		}

		String saveCode = codeObj.toString();
		if (StrUtil.isBlank(saveCode)) {
			redisTemplate.delete(key);
			return new R<>(Boolean.FALSE, "验证码错误");
		}

		if (!StrUtil.equals(saveCode, code)) {
			redisTemplate.delete(key);
			return new R<>(Boolean.FALSE, "验证码错误");
		}

		redisTemplate.delete(key);
		return new R<>(Boolean.TRUE);
	}
}
