package com.ts.server.safe.tencent.map.client.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.server.safe.tencent.map.client.request.SearchRequest;
import com.ts.server.safe.tencent.map.client.response.SearchResponse;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.util.Map;

class SearchClient extends TcMapClient<SearchRequest, SearchResponse> {

    SearchClient(RestTemplate restTemplate, ObjectMapper objectMapper) {
        super(restTemplate, objectMapper, "Search");
    }

    @Override
    protected String buildUri(SearchRequest searchRequest) {
        return "https://apis.map.qq.com//ws/place/v1/search?key={key}&output={output}" +
                "&keyword={keyword}&boundary={boundary}&page_size={pageSize}&page_index={pageIndex}";
    }

    @Override
    protected Object[] uriParams(SearchRequest request) {
        return new Object[]{request.getKey(), request.getOutput(), request.getKeyword(),
                String.format("region(%s,0)", encoderURL(request.getDistrict())), request.getPageSize(), request.getPageIndex()};
    }

    private String encoderURL(String v){
        try{
            return URLEncoder.encode(v, "UTF-8");
        }catch (Exception e){
            //none instance
        }
        return "";
    }

    @Override
    protected SearchResponse buildResponse(Map<String, Object> data) {
        return new SearchResponse(data);
    }
}
