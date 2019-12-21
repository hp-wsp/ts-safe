package com.ts.server.safe.security;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Objects;

/**
 * 用户认证凭证
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class Credential {
    private final String id;
    private final String username;
    private final List<String> roles;
    private volatile long lastTime;

    public Credential(String id, String username, List<String> roles) {
        this.id = id;
        this.username = username;
        this.roles = roles;
        this.lastTime = System.currentTimeMillis();
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public List<String> getRoles() {
        return roles;
    }

    public boolean isExpired(){
        return lastTime > System.currentTimeMillis();
    }

    public void updateLastTime(){
        this.lastTime = System.currentTimeMillis();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Credential that = (Credential) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(username, that.username) &&
                Objects.equals(roles, that.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, roles);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("username", username)
                .append("roles", roles)
                .toString();
    }
}
