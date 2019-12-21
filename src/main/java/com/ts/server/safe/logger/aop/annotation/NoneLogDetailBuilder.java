package com.ts.server.safe.logger.aop.annotation;

import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 空的日志描述
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class NoneLogDetailBuilder implements ApiLogDetailBuilder {

    @Override
    public String build(JoinPoint joinPoint, ServletRequestAttributes attributes, Object returnObj) {
        return  "";
    }
}
