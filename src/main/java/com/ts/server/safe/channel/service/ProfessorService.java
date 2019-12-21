package com.ts.server.safe.channel.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.dao.ProfessorDao;
import com.ts.server.safe.channel.domain.Professor;
import com.ts.server.safe.common.id.IdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 专业业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ProfessorService {
    private final ProfessorDao dao;

    @Autowired
    public ProfessorService(ProfessorDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Professor save(Professor t){
        t.setId(IdGenerators.uuid());

        dao.insert(t);
        return  dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Professor update(Professor t){
        if(!dao.update(t)){
            throw new BaseException("修改专家失败");
        }
        return dao.findOne(t.getId());
    }

    public Professor get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("专家不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id, String channelId){
        return dao.delete(id, channelId);
    }

    public Long count(String channelId, String name, String phone, String profession){
        return dao.count(channelId, name, phone, profession);
    }

    public List<Professor> query(String channelId, String name, String phone, String profession, int offset, int limit){
        return dao.find(channelId, name, phone, profession, offset, limit);
    }
}
