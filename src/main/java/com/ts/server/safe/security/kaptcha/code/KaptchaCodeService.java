package com.ts.server.safe.security.kaptcha.code;

import java.util.Optional;

/**
 * 验证码服务接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public interface KaptchaCodeService {

    /**
     * 保存验证码
     *
     * @param codeKey 验证码key
     * @param code 验证码
     */
    void save(String codeKey, String code);

    /**
     * 得到验证码
     *
     * @param codeKey 验证码key
     * @return 验证码
     */
    Optional<String> get(String codeKey);

    /**
     * 删除验证码
     *
     * @param codeKey 验证码key
     */
    void delete(String codeKey);

    /**
     * 清理过期验证码
     */
    void clearExpired();
}
