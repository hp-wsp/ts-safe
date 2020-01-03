package com.ts.server.safe.tencent.map;

import com.ts.server.safe.tencent.map.client.impl.TcMapClients;
import com.ts.server.safe.tencent.map.client.request.DistrictRequest;
import com.ts.server.safe.tencent.map.client.request.IpRequest;
import com.ts.server.safe.tencent.map.client.request.SearchRequest;
import com.ts.server.safe.tencent.map.client.response.DistrictResponse;
import com.ts.server.safe.tencent.map.client.response.IpResponse;
import com.ts.server.safe.tencent.map.client.response.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.function.Function;

/**
 * QQ地图调用服务
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
@Service
public class QqMapService {
    private final TcMapClients clients;
    private final TencentProperties properties;

    @Autowired
    public QqMapService(TencentProperties properties, RestTemplate restTemplate){
        this.clients = new TcMapClients(restTemplate);
        this.properties = properties;
    }

    public DistrictResponse district(Function<DistrictRequest.Builder, DistrictRequest> function){

        DistrictRequest request = function.apply(
                new DistrictRequest.Builder(properties.getMapKey()));

        return  clients.getDistrictClient().request(request);
    }

    public SearchResponse search(Function<SearchRequest.Builder, SearchRequest> function){

        SearchRequest request = function.apply(new SearchRequest.Builder(properties.getMapKey()));
        return clients.getSearchClient().request(request);
    }

    public IpResponse ip(Function<IpRequest.Builder, IpRequest> function){

        IpRequest request = function.apply(new IpRequest.Builder(properties.getMapKey()));
        return clients.getIpClient().request(request);
    }

}
