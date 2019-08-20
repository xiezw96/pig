package com.pig4cloud.pig.common.core.config;

import com.google.common.base.Predicate;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.AntPathMatcher;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>Title: BaseSwagger2Configurer</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年04月10日
 * @since 1.8
 */
public abstract class BaseSwagger2Configurer {

	@Autowired
	protected FilterIgnorePropertiesConfig ignorePropertiesConfig;
	protected final String MATCH_ALL = "/**";

	protected Docket createRestApi(String title, String description) {
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo(title, description))
			.securitySchemes(createSecurityScheme())
			.securityContexts(createSecurityContexts())
			.select()
			.apis(apiSelector())
			.paths(apiPathsSelector())
			.build();
	}

	protected Predicate<RequestHandler> apiSelector() {
		return RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class);
	}

	protected Predicate<String> apiPathsSelector() {
		return PathSelectors.any();
	}

	protected ApiInfo apiInfo(String title, String description) {
		return new ApiInfoBuilder()
			.title(title)
			.description(description)
			.version("1.0")
			.build();
	}

	protected List<SecurityScheme> createSecurityScheme() {
		return Arrays.asList(new ApiKey("Authorization", "Authorization", "header"));
	}

	protected List<SecurityContext> createSecurityContexts() {
		List<SecurityContext> securityContexts = new ArrayList<>();
		List<Matcher> matchers = createMatcher();
		if (matchers != null && !matchers.isEmpty())
			securityContexts.add(createSecurityContext(matchers));
		return securityContexts;
	}

	protected SecurityContext createSecurityContext(List<Matcher> matchers) {
		return SecurityContext.builder()
			.securityReferences(Arrays.asList(new SecurityReference("Authorization", new AuthorizationScope[]{new AuthorizationScope("", "")})))
			.forPaths(input -> matchers.stream().noneMatch(m -> m.matches(input)))
			.build();
	}

	protected List<Matcher> createMatcher() {
		List<String> urls = ignorePropertiesConfig.getUrls();
		if (urls == null || urls.isEmpty())
			return null;
		return ignorePropertiesConfig.getUrls().stream().map(s -> {
			if (s.equals("/**") || s.equals("**")) {
				return null;
			} else {
				// If the pattern ends with {@code /**} and has no other wildcards or path
				// variables, then optimize to a sub-path match
				if (s.endsWith(MATCH_ALL)
					&& (s.indexOf('?') == -1 && s.indexOf('{') == -1
					&& s.indexOf('}') == -1)
					&& s.indexOf("*") == s.length() - 2) {
					return new SubpathMatcher(
						s.substring(0, s.length() - 3), true);
				} else {
					return new SpringAntMatcher(s, true);
				}
			}
		}).collect(Collectors.toList());
	}

	protected interface Matcher {
		boolean matches(String path);
	}

	protected class SpringAntMatcher implements Matcher {
		private final AntPathMatcher antMatcher;

		private final String pattern;

		private SpringAntMatcher(String pattern, boolean caseSensitive) {
			this.pattern = pattern;
			this.antMatcher = createMatcher(caseSensitive);
		}

		@Override
		public boolean matches(String path) {
			return this.antMatcher.match(this.pattern, path);
		}

		private AntPathMatcher createMatcher(boolean caseSensitive) {
			AntPathMatcher matcher = new AntPathMatcher();
			matcher.setTrimTokens(false);
			matcher.setCaseSensitive(caseSensitive);
			return matcher;
		}
	}

	/**
	 * Optimized matcher for trailing wildcards
	 */
	protected class SubpathMatcher implements Matcher {
		private final String subpath;
		private final int length;
		private final boolean caseSensitive;

		private SubpathMatcher(String subpath, boolean caseSensitive) {
			assert !subpath.contains("*");
			this.subpath = caseSensitive ? subpath : subpath.toLowerCase();
			this.length = subpath.length();
			this.caseSensitive = caseSensitive;
		}

		@Override
		public boolean matches(String path) {
			if (!this.caseSensitive) {
				path = path.toLowerCase();
			}
			return path.startsWith(this.subpath)
				&& (path.length() == this.length || path.charAt(this.length) == '/');
		}
	}
}
