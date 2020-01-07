package com.ts.server.safe.configure;

import com.ts.server.safe.configure.properties.UploadProperties;
import com.ts.server.safe.tencent.map.TencentProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
@EnableConfigurationProperties({TencentProperties.class, UploadProperties.class})
public class SafeConfigure {

    @Bean
    public RestTemplate restTemplate(){
        ClientHttpRequestFactory clientHttpRequestFactory =
                new OkHttp3ClientHttpRequestFactory();
        return new RestTemplate(clientHttpRequestFactory);
    }
}
