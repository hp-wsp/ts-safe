package com.ts.server.safe.report.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.report.dao.CheckReportDao;
import com.ts.server.safe.report.domain.CheckReport;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 检查报表服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class CheckReportService {
    private final CheckReportDao dao;

    @Autowired
    public CheckReportService(CheckReportDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CheckReport save(CheckReport t){

        boolean has = dao.hasCycleName(t.getChannelId(), t.getCycleName());
        if(has){
            throw new BaseException("检查周期名称已经存在");
        }

        t.setId(IdGenerators.uuid());
        dao.insert(t);

        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CheckReport update(CheckReport t){
        CheckReport o  = get(t.getId());
        if(!StringUtils.equals(o.getCycleName(), t.getCycleName()) &&
                dao.hasCycleName(o.getChannelId(), t.getCycleName())){
            throw new BaseException("检查周期名称已经存在");
        }

        if(!dao.update(t)){
            throw new BaseException("修改检查报表失败");
        }

        return get(t.getId());
    }

    public CheckReport get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("检查报表不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public boolean hasCycleName(String channelId, String cycleName){
        return dao.hasCycleName(channelId, cycleName);
    }

    public Long count(String channelId, String compName, String cycleName){
        return dao.count(channelId, compName, cycleName);
    }

    public List<CheckReport> query(String channelId, String compName, String cycleName, int offset, int limit){
        return dao.find(channelId, compName, cycleName, offset, limit);
    }
}
