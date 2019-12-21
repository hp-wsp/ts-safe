package com.ts.server.safe.security.authenticate.matcher;

import java.util.List;

/**
 * 访问匹配接口
 * 
 * @author WangWei
 */
public interface AuthenticateMatcher {

	/**
	 * 判断访问是否合法
	 * 
	 * @param uri 访问URI
	 * @param httpMethod: 访问方法
	 * @param roles 用户角色
	 * @return true:认证成功，false:认证失败
	 */
	boolean authorization(String uri, String httpMethod, List<String> roles);
}
