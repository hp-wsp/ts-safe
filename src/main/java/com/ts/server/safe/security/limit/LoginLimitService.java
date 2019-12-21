package com.ts.server.safe.security.limit;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 登录限制服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public interface LoginLimitService {

    /**
     * 得到失败次数
     *
     * @param username 用户名
     * @return 失败次数
     */
    int getFail(String username);

    /**
     * 增加失败次数
     *
     * @param username 用户名
     * @return 返回失败次数
     */
    int incFail(String username);

    /**
     * 重置失败次数
     *
     * @param username 用户名
     */
    void resetFail(String username);

    /**
     * 清理过期数据
     */
    void clearExpired();

    /**
     * 得到天
     *
     * @return 天
     */
    default String getDay(){
        return LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
    }

    /**
     * 格式化天
     *
     * @return 天
     */
    default String formatDay(LocalDate localDate){
        return localDate.format(DateTimeFormatter.ofPattern("yyMMdd"));
    }
}
