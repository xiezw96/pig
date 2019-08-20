package com.pig4cloud.pig.common.security.extension.filter;

import com.pig4cloud.pig.common.security.extension.AuthType;
import com.pig4cloud.pig.common.security.extension.AuthenticationExt;
import com.pig4cloud.pig.common.security.extension.Authenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * <p>Title: AuthenticationFilter</p
 * <p>Description: </p>
 *
 * @author 余新引
 * @date 2019年06月14日
 * @since 1.8
 */
@Service
public class AuthenticationFilter extends GenericFilterBean {
	private final String AUTH_TYPE_PARM_NAME = "auth_type";
	private final String OAUTH_TOKEN_URL = "/oauth/token";

	@Autowired
	private List<Authenticator> authenticators;

	private final RequestMatcher requestMatcher = new OrRequestMatcher(
		new AntPathRequestMatcher(OAUTH_TOKEN_URL, HttpMethod.GET.name()),
		new AntPathRequestMatcher(OAUTH_TOKEN_URL, HttpMethod.POST.name())
	);

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String authType = request.getParameter(AUTH_TYPE_PARM_NAME);
		if (!StringUtils.isEmpty(authType) && authenticators != null && !authenticators.isEmpty() && requestMatcher.matches(request)) {
			List<Authenticator> supportAuthenticators = authenticators.stream().filter(Authenticator::support).collect(Collectors.toList());
			if (!supportAuthenticators.isEmpty()) {
				try {
					Map<String, String> paramsMap = new HashMap<>();
					Enumeration<String> names = request.getParameterNames();
					while (names.hasMoreElements()) {
						String name = names.nextElement();
						paramsMap.put(name, request.getParameter(name));
					}
					AuthenticationExt.setExt(AuthenticationExt.builder()
						.authType(AuthType.valueOf(authType))
						.paramsMap(paramsMap)
						.build());
					// 前置逻辑
					supportAuthenticators.forEach(Authenticator::prepare);
					chain.doFilter(request, response);
					// 后置逻辑
					supportAuthenticators.forEach(Authenticator::complete);
				} finally {
					AuthenticationExt.removeExt();
				}
			} else
				chain.doFilter(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}
}
