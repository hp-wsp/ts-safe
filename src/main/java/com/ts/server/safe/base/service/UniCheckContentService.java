package com.ts.server.safe.base.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.base.dao.UniCheckContentDao;
import com.ts.server.safe.base.domain.UniCheckContent;
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
public class UniCheckContentService {
    private final UniCheckContentDao dao;

    @Autowired
    public UniCheckContentService(UniCheckContentDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniCheckContent save(UniCheckContent t){
        String id = dao.insert(t);
        return dao.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniCheckContent update(UniCheckContent t){
        if(!dao.update(t)){
            throw new BaseException("修改检查表失败");
        }
        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public UniCheckContent get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("检查表不存在");
        }
    }

    public Long count(String typeName, String itemName, String content, String lawItem){
        return dao.count(typeName, itemName, content, lawItem);
    }

    public List<UniCheckContent> query(String typeName, String itemName, String content, String lawItem, int offset, int limit){
        return dao.find(typeName, itemName, content, lawItem, offset, limit);
    }

    public List<UniCheckContent> queryOfItem(String itemId) {
        return dao.findOfItem(itemId);
    }
}
