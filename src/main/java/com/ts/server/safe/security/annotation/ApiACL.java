package com.ts.server.safe.security.annotation;

import java.lang.annotation.*;

/**
 * API访问权限
 *
 * @author  <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ApiACL {

    /**
     * 允许访问角色数组
     *
     * @return 允许访问角色数组
     */
    String[] value() default {"ROLE_AUTHENTICATION"};
}
