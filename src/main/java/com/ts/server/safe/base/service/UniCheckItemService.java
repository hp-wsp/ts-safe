package com.ts.server.safe.base.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.base.dao.UniCheckItemDao;
import com.ts.server.safe.base.dao.UniCheckContentDao;
import com.ts.server.safe.base.domain.UniCheckItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 检查项目业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class UniCheckItemService {
    private final UniCheckItemDao dao;
    private final UniCheckContentDao tableDao;

    @Autowired
    public UniCheckItemService(UniCheckItemDao dao, UniCheckContentDao tableDao) {
        this.dao = dao;
        this.tableDao = tableDao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniCheckItem save(UniCheckItem t){
        String id = dao.insert(t);

        return dao.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniCheckItem update(UniCheckItem t){
        if(!dao.update(t)){
            throw new BaseException("修改检查项目失败");
        }

        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        if(tableDao.hasOfItem(id)){
            throw new BaseException("有关联检查表");
        }
        return dao.delete(id);
    }

    public UniCheckItem get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("检查项目不存在");
        }
    }

    public Long count(String typeName, String name){
        return dao.count(typeName, name);
    }

    public List<UniCheckItem> query(String typeName, String name, int offset, int limit){
        return dao.find(typeName, name, offset, limit);
    }

    public List<UniCheckItem> queryOfType(String typeId){
        return dao.findOfType(typeId);
    }
}
