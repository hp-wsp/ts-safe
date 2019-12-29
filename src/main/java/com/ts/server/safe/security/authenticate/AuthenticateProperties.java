package com.ts.server.safe.security.authenticate;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.List;

/**
 * 安全配置属性
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ConfigurationProperties(prefix="security.auth")
public class AuthenticateProperties {
    private boolean enable = false;
    private String matcher = "ant";
    private List<Authentication> authentications = Collections.emptyList();

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public String getMatcher() {
        return matcher;
    }

    public void setMatcher(String matcher) {
        this.matcher = matcher;
    }

    public List<Authentication> getAuthentications() {
        return authentications;
    }

    public void setAuthentications(List<Authentication> authentications) {
        this.authentications = authentications;
    }

    public static class Authentication {
        private List<Pattern> patterns;
        private List<String> roles;

        public List<Pattern> getPatterns() {
            return patterns;
        }

        public void setPatterns(List<Pattern> patterns) {
            this.patterns = patterns;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }
    }

    public static class Pattern {
        private String uri;
        private List<String> methods;

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public List<String> getMethods() {
            return methods;
        }

        public void setMethods(List<String> methods) {
            this.methods = methods;
        }
    }
}
