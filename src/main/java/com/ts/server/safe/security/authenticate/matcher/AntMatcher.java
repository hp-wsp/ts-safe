package com.ts.server.safe.security.authenticate.matcher;

import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * URL配置对象
 * 
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class AntMatcher implements AuthenticateMatcher{
	private final String pattern;
	private final Set<String> httpMethods;
	private final Set<String> roles;
	private final PathMatcher pathMatcher;
	private final boolean isAllMethod;
	
	public AntMatcher(String pattern, List<String> httpMethods, Set<String> roles){
		this.pattern = pattern;
		this.httpMethods = httpMethods.stream().map(String::toUpperCase).collect(Collectors.toSet());
		this.roles = roles.stream().map(String::toUpperCase).collect(Collectors.toSet());
		this.pathMatcher = new AntPathMatcher();
		this.isAllMethod = httpMethods.stream().anyMatch(e -> StringUtils.equals(e, "*"));
	}

	@Override
	public boolean authorization(String url, String httpMethod, List<String> uRoles) {
		return  pathMatcher.match(pattern, url) &&
				hasHttpMethod(httpMethod) &&
				hasRole(roles, uRoles) ;
	}

	private boolean hasHttpMethod(String httMethod){
		return httpMethods.contains(httMethod) || isAllMethod;
	}

	private boolean hasRole(Set<String> roles, List<String> uRoles){
		return uRoles.stream().anyMatch(roles::contains);
	}
}