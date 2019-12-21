package com.ts.server.safe.security.token;

import com.ts.server.safe.common.utils.SecurityUtils;
import com.ts.server.safe.security.Credential;
import org.apache.commons.lang3.StringUtils;

import java.util.UUID;

/**
 * 用户Token业务服务
 *
 * @author WangWei
 */
public interface TokenService {

    /**
     * 生成新token
     *
     * @param t 生成token内容
     * @return token值
     */
    String generate(Credential t);

    /**
     * 刷新token内容
     *
     * @param token token
     * @param t     token内容
     * @return true:更新成功
     */
    boolean update(String token, Credential t);

    /**
     * 废弃token
     *
     * @param token token值
     */
    void destroy(String token);

    /**
     * 验证并得到生成Token内容
     *
     * @param toke token值
     * @return 生成Token值
     */
    Credential validateAndGet(String token);

    /**
     * 生成token
     *
     * @return token
     */
    default String genToken(){
        String uuid = StringUtils.remove(UUID.randomUUID().toString(), "-");
        String before = SecurityUtils.randomStr(8);
        String after = SecurityUtils.randomStr(8);
        return String.format("%s%s%s", before, uuid, after);
    }
}
