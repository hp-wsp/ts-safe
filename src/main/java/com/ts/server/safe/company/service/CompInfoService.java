package com.ts.server.safe.company.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.company.dao.CompInfoDao;
import com.ts.server.safe.company.domain.CompInfo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
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
    private final CompProductService productService;

    @Autowired
    public CompInfoService(CompInfoDao dao, CompProductService productService) {
        this.dao = dao;
        this.productService = productService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CompInfo save(CompInfo t){
        if(dao.hasName(t.getChannelId(), t.getName())){
            throw new BaseException("公司已经存在");
        }
        t.setId(IdGenerators.uuid());

        dao.insert(t);
        CompInfo n = dao.findOne(t.getId());
        productService.save(n, Collections.emptyList());

        return n;
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
        if(dao.delete(id, channelId)){
            productService.deleteOfCompany(id);
            return true;
        }

        return false;
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
