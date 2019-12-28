package com.ts.server.safe.base.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.base.dao.UniCheckItemDao;
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
    private final UniCheckItemDao itemDao;

    @Autowired
    public UniCheckTableService(UniCheckTableDao dao, UniCheckItemDao itemDao) {
        this.dao = dao;
        this.itemDao = itemDao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniCheckTable save(UniCheckTable t){
        if(!itemDao.has(t.getId())){
            throw new BaseException("关联检查项目不存在");
        }
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

    public Long count(String typeName, String itemName, String content, String lawItem){
        return dao.count(typeName, itemName, content, lawItem);
    }

    public List<UniCheckTable> query(String typeName, String itemName, String content, String lawItem, int offset, int limit){
        return dao.find(typeName, itemName, content, lawItem, offset, limit);
    }

    public List<UniCheckTable> queryOfItem(String itemId) {
        return dao.findOfItem(itemId);
    }
}
