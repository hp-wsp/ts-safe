package com.ts.server.safe.security.authenticate;

import com.ts.server.safe.security.authenticate.matcher.AntMatcher;
import com.ts.server.safe.security.authenticate.matcher.AuthenticateMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * 认证业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class AuthenticateService {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateService.class);
	
	private final List<AuthenticateMatcher> _matchers = new CopyOnWriteArrayList<>();
	private final boolean enable;

	public AuthenticateService(AuthenticateProperties properties){
		LOGGER.info("Local security auth size is {}, matcher is {}",
                properties.getAuthentications().size(), properties.getMatcher());

		this.enable = properties.isEnable();
		List<AuthenticateMatcher> matchers= properties.getAuthentications().stream()
				.flatMap(e -> e.getPatterns().stream().map(p -> new AntMatcher(p.getUri(), p.getMethods(), new HashSet<>(e.getRoles()))))
				.collect(Collectors.toList());

		this._matchers.addAll(matchers);
	}
	
	public boolean authorization(String uri, String httpMethod, List<String> roles){
		return !enable || _matchers.stream().anyMatch(e -> e.authorization(uri, httpMethod, roles));
	}

	/**
	 * 添加权限匹配集合
	 *
	 * @param matchers 匹配集合
	 */
	public void addMatchers(List<AuthenticateMatcher> matchers){
		_matchers.addAll(matchers);
	}

	/**
	 * 添加权限匹配集合
	 *
	 * @param matcher 匹配
	 */
	public void addMatcher(AuthenticateMatcher matcher){
		_matchers.add(matcher);
	}
}
