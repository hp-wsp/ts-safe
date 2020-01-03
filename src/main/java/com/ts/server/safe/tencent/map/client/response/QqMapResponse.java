package com.ts.server.safe.tencent.map.client.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Map;

/**
 * QQ地图API输出数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class QqMapResponse {
    private Integer status;
    private String message;

    public QqMapResponse(){}

    public QqMapResponse(Map<String, Object> map){
        status = (Integer)map.get("status");
        message = (String)map.get("message");
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("status", status)
                .append("message", message)
                .toString();
    }
}
