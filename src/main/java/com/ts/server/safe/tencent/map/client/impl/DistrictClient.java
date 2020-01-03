package com.ts.server.safe.tencent.map.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.server.safe.tencent.map.client.request.DistrictRequest;
import com.ts.server.safe.tencent.map.client.response.DistrictResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

class DistrictClient extends TcMapClient<DistrictRequest, DistrictResponse> {

    DistrictClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "district");
    }

    @Override
    protected String buildUri(DistrictRequest request) {
        String uri = "https://apis.map.qq.com" + path(request) + "?key={key}&output={key}";
        if (StringUtils.isNotBlank(request.getId())) {
            uri = uri + "&id={id}";
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            uri = uri + "&keyword={keyword}";
        }
        return uri;
    }

    private String path(DistrictRequest request) {
        if (StringUtils.isNotBlank(request.getKeyword())) {
            return "/ws/district/v1/search";
        }
        return "/ws/district/v1/getchildren";
    }

    @Override
    protected Object[] uriParams(DistrictRequest request) {
        List<Object> params = new ArrayList<>();
        params.add(request.getKey());
        params.add(request.getOutput());
        if (StringUtils.isNotBlank(request.getId())) {
            params.add(request.getId());
        }
        if (StringUtils.isNotBlank(request.getKeyword())) {
            params.add(request.getKeyword());
        }
        return params.toArray();
    }

    @Override
    protected DistrictResponse buildResponse(Map<String, Object> data) {
        return new DistrictResponse(data);
    }
}
