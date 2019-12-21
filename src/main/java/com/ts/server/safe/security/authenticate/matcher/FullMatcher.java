package com.ts.server.safe.security.authenticate.matcher;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Set;

/**
 * 完全匹配
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class FullMatcher implements AuthenticateMatcher {
    private final String _uri;
    private final List<String> httpMethods;
    private final Set<String> _roles;

    /**
     * 构造{@link FullMatcher}
     *
     * @param uri 资源uri
     * @param httpMethods 允许访问http method集合
     * @param roles 允许访问角色集合
     */
    public FullMatcher(String uri, List<String> httpMethods, Set<String> roles) {
        this._uri = uri;
        this.httpMethods = httpMethods;
        this._roles = roles;
    }

    @Override
    public boolean authorization(String uri, String httpMethod, List<String> roles) {
        return StringUtils.equals(_uri, uri)
                && httpMethods.contains(httpMethod)
                && hasRole(roles);
    }

    private boolean hasRole(List<String> roles){
        return roles.stream().anyMatch(_roles::contains);
    }
}
