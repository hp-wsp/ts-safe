package com.ts.server.safe.company.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.company.dao.RiskChemicalDao;
import com.ts.server.safe.company.domain.RiskChemical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 危化品存储业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class RiskChemicalService {
    private final RiskChemicalDao dao;

    @Autowired
    public RiskChemicalService(RiskChemicalDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RiskChemical save(RiskChemical t){
        String id = dao.insert(t);
        return dao.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public RiskChemical update(RiskChemical t){
        if(!dao.update(t)){
            throw new BaseException("修改危化品存储失败");
        }

        return get(t.getId());
    }

    public RiskChemical get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("获取危化品存储失败");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public Long count(String compId, String name, String cas){
        return dao.count(compId, name, cas);
    }

    public List<RiskChemical> query(String compId, String name, String cas, int offset, int limit){
        return dao.find(compId, name, cas, offset, limit);
    }
}
