package com.ts.server.safe.tencent.map.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.server.safe.tencent.map.client.request.IpRequest;
import com.ts.server.safe.tencent.map.client.response.IpResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class IpClient extends TcMapClient<IpRequest, IpResponse> {

    IpClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "ip");
    }

    @Override
    protected String buildUri(IpRequest request) {
        String uri =  "https://apis.map.qq.com/ws/location/v1/ip?key={key}&output={output}";
        if(StringUtils.isNotBlank(request.getIp())){
            uri = uri + "&ip={ip}";
        }
        return uri;
    }

    @Override
    protected Object[] uriParams(IpRequest request) {
        List<Object> params = new ArrayList<>();
        params.add(request.getKey());
        params.add(request.getOutput());
        if(StringUtils.isNotBlank(request.getIp())){
            params.add(request.getIp());
        }
        return params.toArray();
    }

    @Override
    protected IpResponse buildResponse(Map<String, Object> data) {
        return new IpResponse(data);
    }
}
