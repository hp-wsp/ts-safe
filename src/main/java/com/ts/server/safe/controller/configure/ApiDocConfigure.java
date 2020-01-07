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

import static springfox.documentation.builders.PathSelectors.*;
import static com.google.common.base.Predicates.*;

/**
 * api doc配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Configuration
@EnableSwagger2
public class ApiDocConfigure {

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
                .description("sys:系统端API接口；man:服务商端API接口；comm:通用API接口。")
                .termsOfServiceUrl("http://safe.tuoshecx.com/api/v2/api-docs")
                .contact(new Contact("WangWei", "", "hhywangwei@gmail.com"))
                .version("1.0")
                .build();
    }

    @Bean
    Docket createSysRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(buildHeadParameters())
                .apiInfo(apiInfo())
                .groupName("sys")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(regex("/sys/.*"))
                .build();
    }

    @Bean
    Docket createManRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(buildHeadParameters())
                .apiInfo(apiInfo())
                .groupName("man")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(regex("/man/.*"))
                .build();
    }

    @Bean
    Docket createCommRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .globalOperationParameters(buildHeadParameters())
                .apiInfo(apiInfo())
                .groupName("comm")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(regex("/comm/.*"))
                .build();
    }
}