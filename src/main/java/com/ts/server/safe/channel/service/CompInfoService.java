package com.ts.server.safe.channel.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.channel.dao.CompInfoDao;
import com.ts.server.safe.channel.domain.CompInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 公司信息业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class CompInfoService {
    private final CompInfoDao dao;

    @Autowired
    public CompInfoService(CompInfoDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CompInfo save(CompInfo t){
        if(dao.hasName(t.getChannelId(), t.getName())){
            throw new BaseException("公司已经存在");
        }
        t.setId(IdGenerators.uuid());

        dao.insert(t);

        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CompInfo update(CompInfo t){
        CompInfo o = get(t.getId());
        if(!StringUtils.equals(o.getName(), t.getName()) &&
                dao.hasName(t.getChannelId(), t.getName())){

            throw new BaseException("公司名称已经存在");
        }

        if(!dao.update(t)){
            throw new BaseException("修改公司名称错误");
        }

        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id, String channelId){
        return dao.delete(id, channelId);
    }

    public boolean hasName(String channelId, String name){
        return dao.hasName(channelId, name);
    }

    public CompInfo get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("公司不存在");
        }
    }

    public Long count(String channelId, String name, String province,
                      String city, String country, String indCtgId, String contact, String phone){

        return dao.count(channelId, name, province, city, country, indCtgId, contact, phone);
    }

    public List<CompInfo> query(String channelId, String name, String province, String city, String country,
                                String indCtgId, String contact, String phone, int offset, int limit){

        return dao.find(channelId, name, province, city, country, indCtgId, contact, phone, offset, limit);
    }
}
