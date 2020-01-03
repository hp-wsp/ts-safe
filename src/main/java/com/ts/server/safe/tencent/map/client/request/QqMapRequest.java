package com.ts.server.safe.tencent.map.client.request;

/**
 * QQ地图API请求
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class QqMapRequest {
    /**
     * QQ调用KEY
     */
    private final String key;

    public QqMapRequest(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
