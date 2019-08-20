package com.pig4cloud.pig.auth.config;

import com.pig4cloud.pig.common.core.config.BaseSwagger2Configurer;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@AllArgsConstructor
public class Swagger2Configuration extends BaseSwagger2Configurer {

	@Bean
	public Docket createRestApi() {
		return createRestApi("认证鉴权服务", "认证鉴权服务");
	}

}
