package com.pig4cloud.pig.gateway.config;

import com.pig4cloud.pig.gateway.swagger2.Swagger2Provider;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.gateway.config.GatewayProperties;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@Configuration
@AllArgsConstructor
public class Swagger2ProviderConfiguration {

	@Bean
	@ConditionalOnMissingBean(SwaggerResourcesProvider.class)
	public SwaggerResourcesProvider swagger2Provider(RouteLocator routeLocator, GatewayProperties gatewayProperties) {
		return new Swagger2Provider(routeLocator, gatewayProperties);
	}

}
