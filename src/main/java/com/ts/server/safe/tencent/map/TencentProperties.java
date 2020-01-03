package com.ts.server.safe.tencent.map;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 腾讯配置属性
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@ConfigurationProperties(prefix = "tencent")
public class  TencentProperties {
    private String mapKey;

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }
}
