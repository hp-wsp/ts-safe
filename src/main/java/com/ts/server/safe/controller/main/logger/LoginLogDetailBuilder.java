package com.ts.server.safe.controller.main.logger;

import com.ts.server.safe.common.utils.HttpUtils;
import com.ts.server.safe.logger.aop.annotation.ApiLogDetailBuilder;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 构建登录日志
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class LoginLogDetailBuilder implements ApiLogDetailBuilder {

    @Override
    public String build(JoinPoint joinPoint, ServletRequestAttributes attributes, Object returnObj) {
        return String.format("IP:%s", HttpUtils.getHostname(attributes.getRequest()));
    }
}
