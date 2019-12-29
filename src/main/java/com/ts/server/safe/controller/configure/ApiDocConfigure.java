package com.ts.server.safe.controller.configure;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;
import java.util.List;

/**
 * Api文档配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Configuration
@EnableSwagger2
public class ApiDocConfigure {

    @Bean
    public Docket createSysRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(buildHeadParameters())
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.ts.server.safe"))
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(PathSelectors.any())
                .build();
    }

    private List<Parameter> buildHeadParameters(){
        Parameter tokenParam = new ParameterBuilder().
                name("Authorization")
                .description("认证令牌")
                .modelRef(new ModelRef("string"))
                .parameterType("header")
                .required(false)
                .build();
        return Collections.singletonList(tokenParam);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Ts-Safe API文档")
                .termsOfServiceUrl("http://api.tuoshecx.com/safe")
                .contact(new Contact("WangWei", "", "hhywangwei@gmail.com"))
                .version("1.0")
                .build();
    }
}