package com.ts.server.safe.company.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.company.dao.SpeEquipmentDao;
import com.ts.server.safe.company.domain.SpeEquipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 特种设备服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class SpeEquipmentService {
    private final SpeEquipmentDao dao;

    @Autowired
    public SpeEquipmentService(SpeEquipmentDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SpeEquipment save(SpeEquipment t){
        String id = dao.insert(t);

        return dao.findOne(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public SpeEquipment update(SpeEquipment t){
        if(!dao.update(t)){
            throw new BaseException("修改特种设备失败");
        }
        return get(t.getId());
    }

    public SpeEquipment get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("得到特种设备失败");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public Long count(String compId, String name){
        return dao.count(compId, name);
    }

    public List<SpeEquipment> query(String compId, String name, int offset, int limit){
        return dao.find(compId, name, offset, limit);
    }
}
