package com.pig4cloud.pig.gateway.swagger2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;

import java.util.Optional;

/**
 * <p>Title: Swagger2Controller</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月01日
 * @since 1.8
 */
@RestController
@RequestMapping("/swagger-resources")
public class Swagger2Controller {
	@Autowired(required = false)
	private SecurityConfiguration securityConfiguration;
	@Autowired(required = false)
	private UiConfiguration uiConfiguration;
	private final SwaggerResourcesProvider swaggerResources;

	@Autowired
	public Swagger2Controller(SwaggerResourcesProvider swaggerResources) {
		this.swaggerResources = swaggerResources;
	}


	@GetMapping("/configuration/security")
	public Mono<ResponseEntity<SecurityConfiguration>> securityConfiguration() {
		return Mono.just(new ResponseEntity<>(
			Optional.ofNullable(securityConfiguration).orElse(SecurityConfigurationBuilder.builder().build()), HttpStatus.OK));
	}

	@GetMapping("/configuration/ui")
	public Mono<ResponseEntity<UiConfiguration>> uiConfiguration() {
		return Mono.just(new ResponseEntity<>(
			Optional.ofNullable(uiConfiguration).orElse(UiConfigurationBuilder.builder().build()), HttpStatus.OK));
	}

	@GetMapping("")
	public Mono<ResponseEntity> swaggerResources() {
		return Mono.just((new ResponseEntity<>(swaggerResources.get(), HttpStatus.OK)));
	}
}
