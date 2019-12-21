package com.ts.server.safe.logger.aop.annotation;

import com.ts.server.safe.security.Credential;
import com.ts.server.safe.security.CredentialContextUtils;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Optional;

/**
 * 不获取用户名
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CredentialObtainUsername implements ObtainUsername {

    @Override
    public String obtain(JoinPoint joinPoint, ServletRequestAttributes attributes) {
        Optional<Credential> optional = CredentialContextUtils.getCredentialOpt();
        return optional.map(Credential::getUsername).orElse("");
    }
}
