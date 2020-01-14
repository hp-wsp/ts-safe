package com.ts.server.safe.controller.configure;

import com.ts.server.safe.controller.interceptor.AuthorizationInterceptor;
import com.ts.server.safe.security.authenticate.AuthenticateService;
import com.ts.server.safe.security.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;

/**
 * Api配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Configuration
public class ControllerConfigure implements WebMvcConfigurer {
    private final TokenService tokenService;
    private final AuthenticateService authenticateService;

    @Autowired
    public ControllerConfigure(TokenService tokenService, AuthenticateService authenticateService) {
        this.tokenService = tokenService;
        this.authenticateService = authenticateService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthorizationInterceptor(tokenService, authenticateService))
                .addPathPatterns("/**")
                .excludePathPatterns("/man/main/*", "/sys/main/*", "/code/*", "/error*");
    }

    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration());
        return new CorsFilter(source);
    }

    private CorsConfiguration corsConfiguration(){
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(false);
        config.setAllowedOrigins(Collections.singletonList("*"));
        config.setAllowedHeaders(Collections.singletonList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        config.setMaxAge(60 * 60L);
        return config;
    }
}