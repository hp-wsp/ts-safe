package com.ts.server.safe.base.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.base.dao.UniRiskDao;
import com.ts.server.safe.base.domain.UniRisk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 危险识别业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class UniRiskService {
    private final UniRiskDao dao;

    @Autowired
    public UniRiskService(UniRiskDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniRisk save(UniRisk t){
        String id = dao.insert(t);
        return dao.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniRisk update(UniRisk t){
        if(!dao.update(t)){
            throw new BaseException("修改危险识别失败");
        }
        return get(t.getId());
    }

    public UniRisk get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public Long count(String name){
        return dao.count(name);
    }

    public List<UniRisk> query(String name, int offset, int limit){
        return dao.find(name, offset, limit);
    }
}
