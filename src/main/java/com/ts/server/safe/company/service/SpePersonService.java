package com.ts.server.safe.company.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.company.dao.SpePersonDao;
import com.ts.server.safe.company.domain.SpePerson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 特殊作业人员和证书业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class SpePersonService {
    private final SpePersonDao dao;

    @Autowired
    public SpePersonService(SpePersonDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SpePerson save(SpePerson t){
        String id = dao.insert(t);

        return dao.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SpePerson update(SpePerson t){
        if(!dao.update(t)){
            throw new BaseException("修改特殊作业人员和证书失败");
        }

        return get(t.getId());
    }

    public SpePerson get(String id) {
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("得到特殊作业人员和证书失败");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public Long count(String compId, String name, String type){
        return dao.count(compId, name, type);
    }

    public List<SpePerson> query(String compId, String name, String type, int offset, int limit){
        return dao.find(compId, name, type, offset, limit);
    }
}
