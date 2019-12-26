package com.ts.server.safe.channel.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.dao.ContractDao;
import com.ts.server.safe.channel.domain.CompInfo;
import com.ts.server.safe.channel.domain.Contract;
import com.ts.server.safe.common.id.IdGenerators;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 合同业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ContractService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ContractService.class);

    private final ContractDao dao;
    private final CompInfoService compInfoService;

    @Autowired
    public ContractService(ContractDao dao, CompInfoService compInfoService) {
        this.dao = dao;
        this.compInfoService = compInfoService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Contract save(Contract t){
        t.setId(IdGenerators.uuid());

        CompInfo info = compInfoService.get(t.getChannelId());
        t.setSerCompName(info.getName());

        dao.insert(t);

        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Contract update(Contract t){
        CompInfo info = compInfoService.get(t.getChannelId());
        t.setSerCompName(info.getName());

        if(!dao.update(t)){
            throw new BaseException("修改合同失败");
        }

        return dao.findOne(t.getId());
    }

    public Contract get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("合同不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        Contract t = get(id);
        if(StringUtils.isNotBlank(t.getServiceId())){
            throw new BaseException("合同已经分配服务");
        }
        return dao.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean updateService(String id, String serviceId){
        return dao.updateService(id, serviceId);
    }

    public Long count(String channelId, String name, String num, String delCompanyName, String proAddress,
                      Integer delCompanyType){
        return dao.count(channelId, name, num, delCompanyName, proAddress, delCompanyType);
    }

    public List<Contract> query(String channelId, String name, String num, String delCompanyName, String proAddress,
                                Integer delCompanyType, int offset, int limit){

        return dao.find(channelId, name, num, delCompanyName, proAddress, delCompanyType, offset, limit);
    }

    public List<Contract> queryNoneAlloc(String channelId){
        return dao.findNoneAlloc(channelId);
    }
}