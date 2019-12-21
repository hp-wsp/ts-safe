package com.ts.server.safe.controller.credential;

import com.ts.server.safe.security.Credential;

import java.util.List;

/**
 * 服务商认证凭证
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WnagWei</a>
 */
public class ManCredential extends Credential {
    private final String channelId;

    /**
     * 构造{@link ManCredential}
     *
     * @param id 用户编号
     * @param username 用户名
     * @param roles 角色
     * @param channelId 所属服务商编号
     */
    public ManCredential(String id, String username, List<String> roles, String channelId) {
        super(id, username, roles);
        this.channelId = channelId;
    }

    public String getChannelId() {
        return channelId;
    }
}
