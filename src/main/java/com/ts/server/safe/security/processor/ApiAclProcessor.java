package com.ts.server.safe.security.processor;

import com.ts.server.safe.security.annotation.ApiACL;
import com.ts.server.safe.security.authenticate.AuthenticateService;
import com.ts.server.safe.security.authenticate.GlobalRole;
import com.ts.server.safe.security.authenticate.matcher.AntMatcher;
import com.ts.server.safe.security.authenticate.matcher.AuthenticateMatcher;
import com.ts.server.safe.security.authenticate.matcher.FullMatcher;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriTemplateHandler;

import java.net.URI;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * API访问控制处理
 *
 * @author <a href="mail:hhywangwei@gmail.com">WangWei</a>
 */
@Component
public class ApiAclProcessor implements BeanPostProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApiAclProcessor.class);
    private static final Object[] VAR_PATHS = new String[]{"*", "*", "*", "*", "*", "*", "*"};
    private static final List<String> DEFAULT_HTTP_METHODS = initHttpMethod();

    private final UriTemplateHandler uriTemplateHandler;
    private final AuthenticateService authenticateService;

    @Autowired
    public ApiAclProcessor(AuthenticateService authenticateService) {
        this.uriTemplateHandler = new DefaultUriBuilderFactory();
        this.authenticateService = authenticateService;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof RequestMappingInfoHandlerMapping){
            LOGGER.debug("Bean is requestMappingInfoHandlerMapping class={}", bean.getClass().getName());
            loadACL((RequestMappingInfoHandlerMapping) bean);
        }

        return bean;
    }

    private void loadACL(RequestMappingInfoHandlerMapping handlerMapping){
        handlerMapping.getHandlerMethods().forEach(this::addACL);
    }

    private void addACL(RequestMappingInfo info, HandlerMethod method){
        Set<String> roles = getRoles(method);
        if(roles.isEmpty()){
            return ;
        }

        PatternsRequestCondition patternsRequestCondition = info.getPatternsCondition();
        List<String> httpMethods =getHttpMethod(info.getMethodsCondition());

        for(String patter: patternsRequestCondition.getPatterns()){
            URI uri = uriTemplateHandler.expand(patter, VAR_PATHS);
            LOGGER.debug("Add acl matcher uri={}, httpMethods=[{}], roles=[{}]",
                    uri.getPath(), StringUtils.join(httpMethods, ","), StringUtils.join(roles, ","));
            AuthenticateMatcher matcher = isAntUri(uri)?
                    new AntMatcher(uri.getPath(), httpMethods, roles):
                    new FullMatcher(uri.getPath(), httpMethods, roles);
            authenticateService.addMatcher(matcher);
        }
    }

    private Set<String> getRoles(HandlerMethod handlerMethod){
        Set<String> roles = new HashSet<>(5);
        ApiACL acl = AnnotationUtils.findAnnotation(handlerMethod.getBeanType(), ApiACL.class);
        if(acl != null){
            roles.addAll(Arrays.asList(acl.value()));
        }
        acl = AnnotationUtils.findAnnotation(handlerMethod.getMethod(), ApiACL.class);
        if(acl != null){
            roles.addAll(Arrays.asList(acl.value()));
        }

        if(roles.isEmpty()){
            return roles;
        }

        for(GlobalRole globalRole: GlobalRole.values()){
            roles.add(globalRole.name());
        }

        return roles;
    }

    private boolean isAntUri(URI uri){
        return StringUtils.contains(uri.getPath(), "*");
    }

    private List<String> getHttpMethod(RequestMethodsRequestCondition requestCondition){
        Set<RequestMethod> methods = requestCondition.getMethods();
        if(methods.isEmpty()){
            return DEFAULT_HTTP_METHODS;
        }

        return methods.stream().map(RequestMethod::name).collect(Collectors.toList());
    }

    private static List<String> initHttpMethod(){
        return Arrays.stream(HttpMethod.values()).map(HttpMethod::name).collect(Collectors.toList());
    }

}
