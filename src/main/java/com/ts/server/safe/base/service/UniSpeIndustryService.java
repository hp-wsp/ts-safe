package com.ts.server.safe.base.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.base.dao.UniSpeIndustryDao;
import com.ts.server.safe.base.domain.UniSpeIndustry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 统一特种行业业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class UniSpeIndustryService {
    private final UniSpeIndustryDao dao;

    @Autowired
    public UniSpeIndustryService(UniSpeIndustryDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniSpeIndustry save(UniSpeIndustry t){
        String id = dao.insert(t);

        return dao.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniSpeIndustry update(UniSpeIndustry t){
        if(!dao.update(t)){
            throw new BaseException("修改特行业失败");
        }
        return dao.findOne(t.getId());
    }

    public UniSpeIndustry get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("特种行业不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public Long count(String name){
        return dao.count(name);
    }

    public List<UniSpeIndustry> query(String name, int offset, int limit){
        return dao.find(name, offset, limit);
    }
}
