package com.ts.server.safe.channel.service;


import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.dao.CheckContentDao;
import com.ts.server.safe.channel.domain.CheckContent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务商检查内容服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class CheckContentService {
    private final CheckContentDao dao;

    @Autowired
    public CheckContentService(CheckContentDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CheckContent save(CheckContent t){
        String id = dao.insert(t);

        return dao.findOne(id);
    }

    public CheckContent get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("检查内容不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CheckContent update(CheckContent t){
        if(!dao.update(t)){
            throw new BaseException("修改检查内失败");
        }
        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public Long count(String channelId, String typeName, String itemName, String content, String lawItem){
        return dao.count(channelId, typeName, itemName, content, lawItem);
    }

    public List<CheckContent> query(String channelId, String typeName, String itemName,
                                    String content, String lawItem, int offset, int limit){

        return dao.find(channelId, typeName, itemName, content, lawItem, offset, limit);
    }

    public List<CheckContent> queryOfItem(String channelId, String itemId) {
        return dao.findOfItem(channelId, itemId);
    }
}
