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

import java.util.ArrayList;
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
            throw new BaseException("上级行业分类不存在");
        }
        if(isRoot(t.getParentId())){
            t.setFullName(t.getName());
        }else{
            t.setFullName(buildFullName(t.getParentId(), t.getName()));
        }
        t.setLevel(getLevel(t.getParentId()));
        String id = dao.insert(t);

        return dao.findOne(id);
    }

    private boolean isRoot(String parentId){
        return StringUtils.isBlank(parentId) || StringUtils.equalsIgnoreCase(parentId, "root");
    }

    private String buildFullName(String parentId, String name){
        List<String> names = new ArrayList<>(3);
        names.add(name);
        for (int i = 0 ; i < 5; i++){
            UniIndCtg p = get(parentId);
            names.add(0, p.getName());
            if(isRoot(p.getParentId())){
                break;
            }
        }
        return StringUtils.join(names, "/");
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
        UniIndCtg o = get(t.getId());

        if(!StringUtils.equals(o.getName(), t.getName())){
            if(isRoot(t.getParentId())){
                t.setFullName(t.getName());
            }else{
                t.setFullName(buildFullName(t.getParentId(), t.getName()));
            }
        }

        if(!dao.update(t)){
            throw new BaseException("修改行业分类失败");
        }
        return dao.findOne(t.getId());
    }

    public UniIndCtg get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("行业分类不存在");
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
