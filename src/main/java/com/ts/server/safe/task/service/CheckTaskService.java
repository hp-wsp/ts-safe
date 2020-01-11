package com.ts.server.safe.task.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.company.domain.CompInfo;
import com.ts.server.safe.contract.domain.ConService;
import com.ts.server.safe.channel.domain.Member;
import com.ts.server.safe.company.service.CompInfoService;
import com.ts.server.safe.contract.service.ConServiceService;
import com.ts.server.safe.channel.service.MemberService;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.task.dao.CheckTaskDao;
import com.ts.server.safe.task.domain.CheckContent;
import com.ts.server.safe.task.domain.CheckTask;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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
    private final CompInfoService infoService;
    private final CheckContentService contentService;
    private final MemberService memberService;

    @Autowired
    public CheckTaskService(CheckTaskDao dao, ConServiceService conService, CompInfoService infoService,
                            CheckContentService contentService, MemberService memberService) {
        this.dao = dao;
        this.conService = conService;
        this.infoService = infoService;
        this.contentService = contentService;
        this.memberService = memberService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CheckTask save(CheckTask t, String[] contentIds){

        t.setId(IdGenerators.uuid());
        t.setStatus(CheckTask.Status.WAIT);
        setService(t);
        setIndCtgs(t);
        setCheckUser(t);
        dao.insert(t);

        for(String contentId: contentIds){
            contentService.save(t.getId(), contentId);
        }

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

    private void setIndCtgs(CheckTask t){
        CompInfo info = infoService.get(t.getCompId());
        for(CheckTask.CheckIndCtg checkIndCtg: t.getCheckIndCtgs()){
            CompInfo.IndCtg ctg = getIndCtgOpt(info.getIndCtgs(), checkIndCtg.getId())
                    .orElseThrow(() ->new BaseException("行业分类不存在"));
            checkIndCtg.setName(ctg.getName());
        }
    }

    private Optional<CompInfo.IndCtg> getIndCtgOpt(List<CompInfo.IndCtg> indCtgs, String ctgId){
        return indCtgs.stream().filter(e -> StringUtils.equals(e.getId(), ctgId)).findFirst();
    }

    private void setCheckUser(CheckTask t){
        for(CheckTask.CheckUser u: t.getCheckUsers()){
            Member m = memberService.get(u.getId());
            if(!StringUtils.equals(m.getChannelId(), t.getChannelId())){
                throw new BaseException("不能添加检查员");
            }
            u.setName(StringUtils.isBlank(m.getName())? m.getUsername(): m.getName());
        }
    }

    public CheckTask get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("检查任务不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CheckTask update(CheckTask t, String[] contentIds){
        CheckTask o = get(t.getId());

        if(!StringUtils.equals(t.getChannelId(), o.getChannelId())){
            throw new BaseException("不能修改检查任务");
        }

        setService(t);
        setIndCtgs(t);
        setCheckUser(t);

        if(!dao.update(t)){
            throw new BaseException("修改检查任务失败");
        }

        List<CheckContent> contents = contentService.query(t.getId());
        List<String> contentIdLst = Arrays.asList(contentIds);

        contents.stream()
                .filter(e -> !contentIdLst.contains(e.getContentId()))
                .forEach(e -> dao.delete(e.getId()));

        for(String contentId: contentIds){
            contentService.save(t.getId(), contentId);
        }

        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        boolean success = dao.delete(id);
        if(success){
            contentService.deleteTask(id);
        }
        return success;
    }

    public Long count(String channelId, String compName, CheckTask.Status status, Date checkTimeFrom, Date checkTimeTo){

        return dao.count(channelId, compName, status, checkTimeFrom, checkTimeTo);
    }

    public List<CheckTask> query(String channelId, String compName, CheckTask.Status status,
                                 Date checkTimeFrom, Date checkTimeTo, int offset, int limit){

        return dao.find(channelId, compName, status, checkTimeFrom, checkTimeTo, offset, limit);
    }
}
