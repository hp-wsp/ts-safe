package com.ts.server.safe.tencent.map.client.response;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SearchResponse extends QqMapResponse {
    private final Integer count;
    private final List<Address> addresses;

    @SuppressWarnings("unchecked")
    public SearchResponse(Map<String, Object> map) {
        super(map);

        this.count = (Integer)map.get("count");
        List<Map<String, Object>> result = (List<Map<String, Object>>)map.get("data");
        this.addresses = result.stream().map(Address::new).collect(Collectors.toList());
    }

    public Integer getCount() {
        return count;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public static class Address {
        private final String id;
        private final String title;
        private final String address;
        private final String tel;
        private final String category;
        private final Integer type;
        private final String[] location;

        @SuppressWarnings("unchecked")
        private Address(Map<String, Object> map){
            this.id = (String)map.get("id");
            this.title = (String)map.get("title");
            this.address = (String)map.get("address");
            this.tel = (String)map.get("tel");
            this.category = (String)map.get("category");
            this.type = (Integer)map.get("type");

            Map<String, Object> locationMap = (Map<String, Object>) map.get("location");
            this.location = new String[]{String.valueOf(locationMap.get("lat")), String.valueOf(locationMap.get("lng"))};
        }

        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getAddress() {
            return address;
        }

        public String getTel() {
            return tel;
        }

        public String getCategory() {
            return category;
        }

        public Integer getType() {
            return type;
        }

        public String[] getLocation() {
            return location;
        }
    }
}
