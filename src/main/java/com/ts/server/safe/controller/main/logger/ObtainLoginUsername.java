package com.ts.server.safe.controller.main.logger;

import com.ts.server.safe.controller.main.form.LoginForm;
import com.ts.server.safe.logger.aop.annotation.ObtainUsername;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 获取登录用户
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ObtainLoginUsername implements ObtainUsername {

    @Override
    public String obtain(JoinPoint joinPoint, ServletRequestAttributes attributes) {
        LoginForm form = (LoginForm)joinPoint.getArgs()[0];
        return form.getUsername();
    }
}
