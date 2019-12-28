package com.ts.server.safe.base.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.base.dao.UniRiskChemicalDao;
import com.ts.server.safe.base.domain.UniRiskChemical;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 统一危险化学品业务服务
 *
 * @author <a href="mailto:hhywangwei@mgail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class UniRiskChemicalService {
    private final UniRiskChemicalDao dao;

    @Autowired
    public UniRiskChemicalService(UniRiskChemicalDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniRiskChemical save(UniRiskChemical t){
        String id = dao.insert(t);
        return dao.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniRiskChemical update(UniRiskChemical t){
        if(!dao.update(t)){
            throw new BaseException("修改危险化学品失败");
        }

        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public UniRiskChemical get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("危险化学品不存在");
        }
    }

    public Long count(String name, String alias, String cas){
        return dao.count(name, alias, cas);
    }

    public List<UniRiskChemical> query(String name, String alias, String cas, int offset, int limit){
        return dao.find(name, alias, cas, offset, limit);
    }
}
