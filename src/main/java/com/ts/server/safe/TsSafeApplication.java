package com.ts.server.safe;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 程序入口程序
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@SpringBootApplication
@EnableScheduling
public class TsSafeApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(TsSafeApplication.class);

    public static void main(String[] args){
        ApplicationContext context = new SpringApplicationBuilder(TsSafeApplication.class)
                .web(WebApplicationType.SERVLET)
                .run(args);

        for(String name: context.getBeanDefinitionNames()){
            LOGGER.trace("Instance bean name={}", name);
        }
    }
}
