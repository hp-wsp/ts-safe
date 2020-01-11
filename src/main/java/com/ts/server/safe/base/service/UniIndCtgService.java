package com.ts.server.safe.base.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.base.dao.UniIndCtgDao;
import com.ts.server.safe.base.domain.UniIndCtg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 统一监管分类业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class UniIndCtgService {
    private final UniIndCtgDao dao;

    @Autowired
    public UniIndCtgService(UniIndCtgDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniIndCtg save(UniIndCtg t){
        if(!hasParentId(t.getParentId())){
            throw new BaseException("上级监管分类不存在");
        }

        t.setLevel(getLevel(t.getParentId()));
        String id = dao.insert(t);

        return dao.findOne(id);
    }

    private boolean hasParentId(String parentId){
        return StringUtils.equals(parentId, "root") || dao.has(parentId);
    }

    private int getLevel(String parentId){
        if(StringUtils.equals(parentId, "root")){
            return 1;
        }
        UniIndCtg t = dao.findOne(parentId);
        return t.getLevel() + 1;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public UniIndCtg update(UniIndCtg t){
        if(!dao.update(t)){
            throw new BaseException("修改监管分类失败");
        }
        return dao.findOne(t.getId());
    }

    public UniIndCtg get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("监管分类不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public List<UniIndCtg> queryChildren(String parentId){
        return dao.findChildren(parentId);
    }

    public Long count(String parentId, String name, String num){
        return dao.count(parentId, name, num);
    }

    public List<UniIndCtg> query(String parentId, String name, String num, int offset, int limit){
        return dao.find(parentId, name, num, offset, limit);
    }
}
