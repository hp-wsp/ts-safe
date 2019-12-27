package com.ts.server.safe.base.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.base.dao.UniCheckTableDao;
import com.ts.server.safe.base.domain.UniCheckTable;
import com.ts.server.safe.common.id.IdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 统一检查表业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(propagation = Propagation.REQUIRED)
public class UniCheckTableService {
    private final UniCheckTableDao dao;

    @Autowired
    public UniCheckTableService(UniCheckTableDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniCheckTable save(UniCheckTable t){
        t.setId(IdGenerators.uuid());
        dao.insert(t);
        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniCheckTable update(UniCheckTable t){
        if(dao.update(t)){
            throw new BaseException("修改检查表失败");
        }
        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public UniCheckTable get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("检查表不存在");
        }
    }

    public Long count(String checkType, String checkItem, String content, String lawItem){
        return dao.count(checkType, checkItem, content, lawItem);
    }

    public List<UniCheckTable> query(String checkType, String checkItem, String content, String lawItem, int offset, int limit){

        return dao.find(checkType, checkItem, content, lawItem, offset, limit);
    }
}
