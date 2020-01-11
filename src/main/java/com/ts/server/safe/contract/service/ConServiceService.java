package com.ts.server.safe.contract.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.service.MemberService;
import com.ts.server.safe.contract.dao.ConServiceDao;
import com.ts.server.safe.contract.domain.ConService;
import com.ts.server.safe.contract.domain.ConServiceItem;
import com.ts.server.safe.contract.domain.Contract;
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
    private final ConServiceItemService itemService;

    @Autowired
    public ConServiceService(ConServiceDao dao, ContractService contractService,
                             MemberService memberService, ConServiceItemService itemService) {
        this.dao = dao;
        this.contractService = contractService;
        this.memberService = memberService;
        this.itemService = itemService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ConService save(String channelId, String name, String conId, String leadId, List<ConServiceItem> items){
        Contract contract = contractService.get(conId);
        if(!StringUtils.equals(contract.getChannelId(), channelId)){
            throw new BaseException("不能新增服务");
        }
        if(StringUtils.isNotBlank(contract.getServiceId())){
            throw new BaseException("合同已经添加服务");
        }

        ConService t = new ConService();
        t.setId(IdGenerators.uuid());
        t.setChannelId(channelId);
        t.setName(name);
        t.setConId(contract.getId());
        t.setConName(contract.getName());
        t.setCompId(contract.getSerCompId());
        t.setCompName(contract.getSerCompName());
        Member member = memberService.get(leadId);
        if(!StringUtils.equals(member.getChannelId(), channelId)){
            throw new BaseException("不能新增服务");
        }
        t.setLeaId(member.getId());
        t.setLeaName(member.getName());
        t.setStatus(ConService.Status.WAIT);

        dao.insert(t);
        items.forEach(e -> e.setServiceId(t.getId()));
        itemService.save(t.getId(), items);
        contractService.updateService(t.getConId(), t.getId());

        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public ConService update(String id, String name, String conId, String leadId, List<ConServiceItem> items){
        ConService o = get(id);
        if(!StringUtils.equals(o.getConId(), conId)){
            Contract contract = contractService.get(conId);

            if(StringUtils.isNotBlank(contract.getServiceId())){
                throw new BaseException("合同已经添加服务");
            }

            if(!StringUtils.equals(o.getChannelId(), contract.getChannelId())){
                LOGGER.warn("Update contract channel not same channelId={}, newChannel={}",
                        o.getChannelId(), contract.getChannelId());
                throw new BaseException("不能修改服务");
            }

            contractService.updateService(o.getConId(), "");
            contractService.updateService(conId, id);

            o.setConId(conId);
            o.setConName(contract.getName());
        }

        if(!StringUtils.equals(o.getLeaId(), leadId)){
            Member member = memberService.get(leadId);
            if(!StringUtils.equals(member.getChannelId(), o.getChannelId())){
                throw new BaseException("不能修改服务");
            }
            o.setLeaId(member.getId());
            o.setLeaName(member.getName());
        }

        o.setName(name);
        if(!dao.update(o)){
            throw new BaseException("修改服务失败");
        }
        items.forEach(e -> e.setServiceId(o.getId()));
        itemService.save(o.getId(), items);

        return get(o.getId());
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

    public List<ConServiceItem> queryItems(String id){
        return itemService.query(id);
    }

    public Long count(String channelId, String name, String compName, ConService.Status status){
        return dao.count(channelId, name, compName, status);
    }

    public List<ConService> query(String channelId, String name, String compName, ConService.Status status, int offset, int limit){
        return dao.find(channelId, name, compName, status, offset, limit);
    }
}
