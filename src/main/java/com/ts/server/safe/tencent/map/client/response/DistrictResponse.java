package com.ts.server.safe.tencent.map.client.response;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class DistrictResponse extends QqMapResponse {
    private List<District> districts;

    public DistrictResponse(){}

    @SuppressWarnings("unchecked")
    public DistrictResponse(Map<String, Object> map){
        super(map);
        if(getStatus() == 0){
            List<List<Map<String, Object>>> result = (List<List<Map<String, Object>>>)map.get("result");
            districts = result.stream().flatMap(List::stream).map(District::new).collect(Collectors.toList());
        }else{
            districts = Collections.emptyList();
        }
    }

    public List<District> getDistricts() {
        return districts;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("districts", districts)
                .toString();
    }

    public static class District {
        private String id;
        private String name;
        private String fullname;
        private String[] location;

        public District(){}

        @SuppressWarnings("unchecked")
        public District(Map<String, Object> map){
            this.id = (String)map.get("id");
            this.name = (String)map.get("name");
            this.fullname = (String)map.get("fullname");

            Map<String, Object> locationMap = (Map<String, Object>) map.get("location");
            this.location = new String[]{String.valueOf(locationMap.get("lat")), String.valueOf(locationMap.get("lng"))};
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String[] getLocation() {
            return location;
        }

        public void setLocation(String[] location) {
            this.location = location;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("id", id)
                    .append("name", name)
                    .append("fullname", fullname)
                    .append("location", location)
                    .toString();
        }
    }

}
