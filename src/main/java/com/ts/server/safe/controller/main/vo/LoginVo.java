package com.ts.server.safe.controller.main.vo;

/**
 * 登陆输出数据
 *
 * @param <T>
 */
public class LoginVo<T> {
    private final String token;
    private final T user;

    public LoginVo(String token, T user) {
        this.token = token;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public T getUser() {
        return user;
    }
}
