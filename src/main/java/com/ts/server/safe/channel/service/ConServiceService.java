package com.ts.server.safe.channel.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.dao.ConServiceDao;
import com.ts.server.safe.channel.domain.ConService;
import com.ts.server.safe.channel.domain.Contract;
import com.ts.server.safe.channel.domain.Member;
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
 * 合同服务业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ConServiceService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ConServiceService.class);

    private final ConServiceDao dao;
    private final ContractService contractService;
    private final MemberService memberService;

    @Autowired
    public ConServiceService(ConServiceDao dao, ContractService contractService, MemberService memberService) {
        this.dao = dao;
        this.contractService = contractService;
        this.memberService = memberService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ConService save(ConService t){
        Contract contract = contractService.get(t.getConId());

        if(StringUtils.isNotBlank(contract.getServiceId())){
            throw new BaseException("合同已经添加服务");
        }

        Member member = memberService.get(t.getLeaId());
        if(!StringUtils.equals(contract.getChannelId(), t.getChannelId()) ||
                !StringUtils.equals(member.getChannelId(), t.getChannelId())){

            throw new BaseException("不能添加服务");
        }

        t.setId(IdGenerators.uuid());
        t.setConName(contract.getName());
        t.setLeaName(member.getName());

        dao.insert(t);
        contractService.updateService(t.getConId(), t.getId());

        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ConService update(ConService t){
        ConService o = get(t.getId());
        if(!StringUtils.equals(o.getConId(), t.getConId())){
            Contract contract = contractService.get(t.getConId());

            if(StringUtils.isNotBlank(contract.getServiceId())){
                throw new BaseException("合同已经添加服务");
            }

            if(!StringUtils.equals(o.getChannelId(), contract.getChannelId())){
                LOGGER.warn("Update contract channel not same channelId={}, newChannel={}",
                        o.getChannelId(), contract.getChannelId());
                throw new BaseException("不能添加服务");
            }
            t.setConName(contract.getName());
            contractService.updateService(o.getConId(), "");
            contractService.updateService(o.getConId(), t.getId());
        }

        if(!StringUtils.equals(o.getLeaId(), t.getLeaId())){
            Member member = memberService.get(t.getLeaId());
            if(!StringUtils.equals(member.getChannelId(), t.getChannelId())){
                throw new BaseException("不能添加服务");
            }
            t.setLeaName(member.getName());
        }

        if(!dao.update(t)){
            throw new BaseException("修复服务失败");
        }

        return get(t.getId());
    }

    public ConService get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("服务不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id, String channelId){
        ConService o = get(id);
        if(!StringUtils.equals(o.getChannelId(), channelId)){
            throw new BaseException("不能删除服务");
        }
        return dao.delete(id);
    }

    public Long count(String channelId, String name, String compName, ConService.Status status){
        return dao.count(channelId, name, compName, status);
    }

    public List<ConService> query(String channelId, String name, String compName, ConService.Status status, int offset, int limit){
        return dao.find(channelId, name, compName, status, offset, limit);
    }
}
