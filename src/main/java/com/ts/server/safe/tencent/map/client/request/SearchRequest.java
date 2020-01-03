package com.ts.server.safe.tencent.map.client.request;

public class SearchRequest extends QqMapRequest {

    public static class Builder {
        private final String key;
        private String keyword;
        private String district;
        private String output = "json";
        private Integer pageIndex = 1;
        private Integer pageSize = 20;

        public Builder(String key){
            this.key = key;
        }

        public Builder setDistrict(String district){
            this.district = district;
            return this;
        }

        public Builder setKeyword(String keyword){
            this.keyword = keyword;
            return this;
        }

        public Builder setOutput(String output){
            this.output = output;
            return this;
        }

        public Builder setPageIndex(Integer pageIndex){
            this.pageIndex = pageIndex;
            return this;
        }

        public Builder setPageSize(Integer pageSize){
            this.pageSize = pageSize;
            return this;
        }

        public SearchRequest build(){
            return new SearchRequest(key, keyword, district, output, pageIndex, pageSize);
        }
    }

    private final String keyword;
    private final String district;
    private final String output;
    private final Integer pageIndex;
    private final Integer pageSize;

    private SearchRequest(String key, String keyword, String district, String output, Integer pageIndex, Integer pageSize) {
        super(key);
        this.keyword = keyword;
        this.district = district;
        this.output = output;
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getDistrict() {
        return district;
    }

    public String getOutput() {
        return output;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }
}
