package com.ts.server.safe.logger.aop.annotation;

import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 获取用户名
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public interface ObtainUsername {

    /**
     * 获取用户名
     *
     * @param joinPoint {@link JoinPoint}
     * @param attributes {@link ServletRequestAttributes}
     * @return 获取用户名
     */
    String obtain(JoinPoint joinPoint, ServletRequestAttributes attributes);
}
