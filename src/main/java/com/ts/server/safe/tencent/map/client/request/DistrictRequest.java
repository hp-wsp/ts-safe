package com.ts.server.safe.tencent.map.client.request;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class DistrictRequest extends QqMapRequest {

    public static class Builder {
        private final String key;
        private String id;
        private String keyword;
        private String output = "json";

        public Builder(String key) {
            this.key = key;
        }

        public Builder setId(String id){
            this.id = id;
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

        public DistrictRequest build(){
            return new DistrictRequest(key, id, keyword, output);
        }
    }

    private final String id;
    private final String keyword;
    private final String output;

    private DistrictRequest(
            String key, String id, String keyword, String output) {

        super(key);
        this.id = id;
        this.keyword = keyword;
        this.output = output;
    }

    public String getId() {
        return id;
    }

    public String getKeyword() {
        return keyword;
    }

    public String getOutput() {
        return output;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("keyword", keyword)
                .append("output", output)
                .toString();
    }
}
