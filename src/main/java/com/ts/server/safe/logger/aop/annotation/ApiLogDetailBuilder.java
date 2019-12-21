package com.ts.server.safe.logger.aop.annotation;

import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 日志描述接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public interface ApiLogDetailBuilder {

    /**
     * 构建日志描述
     *
     * @param joinPoint {@link JoinPoint}
     * @param attributes {@link ServletRequestAttributes}
     * @param returnObj 方法返回结果
     * @return 日志描述
     */
    String build(JoinPoint joinPoint, ServletRequestAttributes attributes, Object returnObj);
}
