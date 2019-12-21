package com.ts.server.safe.security.autoconfigure;

import com.ts.server.safe.security.authenticate.AuthenticateProperties;
import com.ts.server.safe.security.authenticate.AuthenticateService;
import com.ts.server.safe.security.kaptcha.KaptchaProperties;
import com.ts.server.safe.security.kaptcha.KaptchaService;
import com.ts.server.safe.security.kaptcha.code.KaptchaCodeService;
import com.ts.server.safe.security.kaptcha.code.MemKaptchaCodeService;
import com.ts.server.safe.security.limit.LoginLimitService;
import com.ts.server.safe.security.limit.MemLimitService;
import com.ts.server.safe.security.token.TokenService;
import com.ts.server.safe.security.token.mem.MemTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 安全配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Configuration
@EnableConfigurationProperties({KaptchaProperties.class, AuthenticateProperties.class})
public class SecurityConfigure {
    private final AuthenticateProperties authProperties;
    private final KaptchaProperties kaptchaProperties;

    @Autowired
    public SecurityConfigure(AuthenticateProperties authProperties, KaptchaProperties kaptchaProperties) {
        this.authProperties = authProperties;
        this.kaptchaProperties = kaptchaProperties;
    }

    @Bean
    @ConditionalOnMissingBean(TokenService.class)
    public TokenService tokenService(){
        return new MemTokenService();
    }

    @Bean
    @ConditionalOnMissingBean(LoginLimitService.class)
    public LoginLimitService loginLimitService(){
        return new MemLimitService();
    }

    @Bean
    @ConditionalOnMissingBean(KaptchaService.class)
    public KaptchaService kaptchaService(){
        KaptchaCodeService codeService = new MemKaptchaCodeService(15 * 60);
        return new KaptchaService(kaptchaProperties, codeService);
    }

    @Bean
    public AuthenticateService authenticateService(){
        return new AuthenticateService(authProperties);
    }
}
