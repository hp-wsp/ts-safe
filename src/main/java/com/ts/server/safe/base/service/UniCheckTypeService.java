package com.ts.server.safe.base.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.base.dao.UniCheckItemDao;
import com.ts.server.safe.base.dao.UniCheckTypeDao;
import com.ts.server.safe.base.domain.UniCheckType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 检查类别业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class UniCheckTypeService {
    private final UniCheckTypeDao dao;
    private final UniCheckItemDao itemDao;

    @Autowired
    public UniCheckTypeService(UniCheckTypeDao dao, UniCheckItemDao itemDao) {
        this.dao = dao;
        this.itemDao = itemDao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniCheckType save(UniCheckType t){
        String id = dao.insert(t);
        return dao.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniCheckType update(UniCheckType t){
        if(!dao.update(t)){
            throw new BaseException("修改检查类别失败");
        }
        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        if(itemDao.hasType(id)){
            throw new BaseException("有所属检查项目不能删除");
        }
        return dao.delete(id);
    }

    public UniCheckType get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("检查类别不存在");
        }
    }

    public Long count(String name){
        return dao.count(name);
    }

    public List<UniCheckType> query(String name, int offset, int limit){
        return dao.find(name, offset, limit);
    }
}
