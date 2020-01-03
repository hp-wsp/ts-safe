package com.ts.server.safe.tencent.map.client.request;

public class IpRequest extends QqMapRequest {

    public static class Builder {
        private final String key;
        private String ip;
        private String output = "json";

        public Builder(String key){
            this.key = key;
        }

        public Builder setIp(String ip){
            this.ip = ip;
            return this;
        }

        public Builder setOutput(String output){
            this.output = output;
            return this;
        }

        public IpRequest build(){
            return new IpRequest(key, ip, output);
        }
    }

    private final String ip;
    private final String output;

    private IpRequest(String key, String ip, String output) {
        super(key);
        this.ip = ip;
        this.output = output;
    }

    public String getIp() {
        return ip;
    }

    public String getOutput() {
        return output;
    }
}
