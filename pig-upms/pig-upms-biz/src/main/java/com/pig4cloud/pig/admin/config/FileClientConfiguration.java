package com.pig4cloud.pig.admin.config;

import com.pig4cloud.pig.admin.service.FileClient;
import com.pig4cloud.pig.admin.service.impl.DiskFileClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>Title: FileClientConfiguration</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月03日
 * @since 1.8
 */
@Configuration
public class FileClientConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public FileClient diskFileClient() {
		return new DiskFileClient();
	}

}
