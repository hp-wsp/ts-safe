package com.ts.server.safe.channel.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.dao.ChannelDao;
import com.ts.server.safe.channel.domain.Channel;
import com.ts.server.safe.common.id.IdGenerators;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 服务商业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ChannelService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelService.class);

    private final ChannelDao dao;
    private final MemberService memberService;

    @Autowired
    public ChannelService(ChannelDao dao, MemberService memberService) {

        this.dao = dao;
        this.memberService = memberService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Channel save(Channel t){
        t.setId(IdGenerators.uuid());

        dao.insert(t);
        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Channel update(Channel t){
        if(!dao.update(t)){
            throw new BaseException("修改服务商失败");
        }
        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Channel open(String id){
        if(dao.updateStatus(id, Channel.Status.OPEN)){
            throw new BaseException("开放服务商失败");
        }
        memberService.activeMember(id, true);
        return get(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Channel close(String id){
        if(dao.updateStatus(id, Channel.Status.CLOSED)){
            throw new BaseException("关闭服务商失败");
        }
        memberService.activeMember(id, false);
        return get(id);
    }

    public Channel get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("服务商不存在");
        }
    }

    public boolean has(String id){
        return dao.has(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        boolean ok = dao.delete(id);
        if(ok){
            memberService.deleteMembers(id);
        }
        return ok;
    }

    public Long count(String name, Channel.Status status){
        return dao.count(name, status);
    }

    public List<Channel> query(String name, Channel.Status status, int offset, int limit){
        return dao.find(name, status, offset, limit);
    }
}
