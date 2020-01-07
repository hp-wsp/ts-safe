package com.ts.server.safe.configure.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 上传属性配置
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ConfigurationProperties(prefix = "upload")
public class UploadProperties {
    private String resource;

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }
}
