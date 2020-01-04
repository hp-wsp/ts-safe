package com.ts.server.safe.base.service;

import com.ts.server.safe.base.dao.DistrictDao;
import com.ts.server.safe.base.domain.District;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 行政区划业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class DistrictService {
    private final DistrictDao dao;

    @Autowired
    public DistrictService(DistrictDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(District t){
        if(dao.has(t.getId())){
            dao.update(t);
        }else{
            dao.insert(t);
        }
    }

    public List<District> query(String parentId){
        return dao.find(parentId);
    }
}
