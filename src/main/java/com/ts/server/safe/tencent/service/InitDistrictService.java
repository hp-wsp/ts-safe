package com.ts.server.safe.tencent.service;

import com.ts.server.safe.base.domain.District;
import com.ts.server.safe.base.service.DistrictService;
import com.ts.server.safe.tencent.map.QqMapService;
import com.ts.server.safe.tencent.map.client.response.DistrictResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 行政区划业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class InitDistrictService {
    private final DistrictService service;
    private final QqMapService mapService;

    @Autowired
    public InitDistrictService(DistrictService service, QqMapService mapService) {
        this.service = service;
        this.mapService = mapService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void initDistrict(){
        saveDistrict("", 1);
    }

    private void saveDistrict(String parentId, int level){
        if(level > 3){
            return ;
        }

        DistrictResponse response = mapService.district(e -> e.setId(parentId).build());
        response.getDistricts().forEach(e -> {
            District t = new District();
            t.setId(e.getId());
            String name = StringUtils.isBlank(e.getName())? e.getFullname(): e.getName();
            t.setName(name);
            t.setFullName(e.getFullname());
            t.setLevel(level);
            t.setParentId(StringUtils.isBlank(parentId)? "root": parentId);
            t.setLocation(e.getLocation());
            service.save(t);

            int nextLevel = level + 1;
            saveDistrict(t.getId(), nextLevel);
        });
    }
}
