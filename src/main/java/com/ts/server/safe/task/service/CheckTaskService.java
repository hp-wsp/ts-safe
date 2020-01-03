package com.ts.server.safe.task.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.domain.ConService;
import com.ts.server.safe.channel.service.ConServiceService;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.task.dao.CheckTaskDao;
import com.ts.server.safe.task.domain.CheckTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * 检查任务业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class CheckTaskService {
    private final CheckTaskDao dao;
    private final ConServiceService conService;

    @Autowired
    public CheckTaskService(CheckTaskDao dao, ConServiceService conService) {
        this.dao = dao;
        this.conService = conService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CheckTask save(CheckTask t){

        t.setId(IdGenerators.uuid());
        setService(t);

        dao.insert(t);

        return dao.findOne(t.getId());
    }

    private void setService(CheckTask t){
        ConService conSer = conService.get(t.getServiceId());
        if(!StringUtils.equals(t.getChannelId(), conSer.getChannelId())){
            throw new BaseException("不能添加检查任务");
        }
        t.setServiceName(conSer.getName());
        t.setCompId(conSer.getCompId());
        t.setCompName(conSer.getCompName());
    }

    public CheckTask get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("检查任务不存在");
        }
    }


    @Transactional(propagation = Propagation.REQUIRED)
    public CheckTask update(CheckTask t){
        CheckTask o = get(t.getId());

        if(!StringUtils.equals(t.getChannelId(), o.getChannelId())){
            throw new BaseException("不能修改检查任务");
        }
        setService(t);

        if(!dao.update(t)){
            throw new BaseException("修改检查任务失败");
        }

        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }


}
