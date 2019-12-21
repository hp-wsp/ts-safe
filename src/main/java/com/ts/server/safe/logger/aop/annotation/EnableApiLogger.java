package com.ts.server.safe.logger.aop.annotation;

import java.lang.annotation.*;

/**
 * 日志注解
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface EnableApiLogger {

    /**
     * 日志名称
     *
     * @return 日志名称
     */
    String name();

    /**
     * 日志类型
     *
     * @return 日志类型
     */
    String type() default "common";

    /**
     * 生成日志描述
     *
     * @return {@link ApiLogDetailBuilder}
     */
    Class<? extends ApiLogDetailBuilder> buildDetail() default NoneLogDetailBuilder.class;

    /**
     * 获取用户名
     *
     * @return {@link ObtainUsername}
     */
    Class<? extends  ObtainUsername> obtainUsername() default CredentialObtainUsername.class;
}
