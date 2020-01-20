package com.ts.server.safe.configure;

import com.ts.server.safe.configure.properties.UploadProperties;
import com.ts.server.safe.tencent.map.TencentProperties;
import okhttp3.OkHttpClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableConfigurationProperties({TencentProperties.class, UploadProperties.class})
public class SafeConfigure {

    @Bean
    public RestTemplate restTemplate(){
        OkHttpClient client =  new OkHttpClient
                .Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .writeTimeout(5, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();
        return new RestTemplate(new OkHttp3ClientHttpRequestFactory(client));
    }
}
