package com.ts.server.safe.company.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.company.dao.OccDiseaseJobDao;
import com.ts.server.safe.company.domain.OccDiseaseJob;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 职业病岗位业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class OccDiseaseJobService {
    private final OccDiseaseJobDao dao;

    @Autowired
    public OccDiseaseJobService(OccDiseaseJobDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OccDiseaseJob save(OccDiseaseJob t){
        if(dao.hasJob(t.getJob())){
            throw new BaseException("作业岗位已经存在");
        }
        String id = dao.insert(t);
        return dao.findOne(id);
    }

    public OccDiseaseJob get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("职业病岗位不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public OccDiseaseJob update(OccDiseaseJob t){
        OccDiseaseJob o = get(t.getId());
        if(!StringUtils.equals(t.getJob(), o.getJob()) && dao.hasJob(t.getJob())){
            throw new BaseException("作业岗位已经存在");
        }

        if(!dao.update(t)){
            throw new BaseException("修改职业病岗位失败");
        }

        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public Long count(String compId, String job){
        return dao.count(compId, job);
    }

    public List<OccDiseaseJob> queryCompany(String compId){
        return dao.find(compId, "", 0, 100);
    }

    public List<OccDiseaseJob> query(String compId, String job, int offset, int limit){
        return dao.find(compId, job, offset, limit);
    }
}

