package com.ts.server.safe.tencent.map.client.response;

import java.util.Map;

public class IpResponse extends QqMapResponse {
    private final String nation;
    private final String province;
    private final String city;
    private final String adcode;
    private final String[] location;

    @SuppressWarnings("unchecked")
    public IpResponse(Map<String, Object> map) {
        super(map);
        Map<String, Object> adInfoMap = (Map<String, Object>)map.get("ad_info");
        this.nation = (String)adInfoMap.get("nation");
        this.province = (String)adInfoMap.get("province");
        this.city = (String)adInfoMap.get("city");
        this.adcode = (String)adInfoMap.get("adcode");

        Map<String, Object> locationMap = (Map<String, Object>) map.get("location");
        this.location = new String[]{String.valueOf(locationMap.get("lat")), String.valueOf(locationMap.get("lng"))};
    }

    public String getNation() {
        return nation;
    }

    public String getProvince() {
        return province;
    }

    public String getCity() {
        return city;
    }

    public String getAdcode() {
        return adcode;
    }

    public String[] getLocation() {
        return location;
    }
}
