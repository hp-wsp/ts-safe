package com.ts.server.safe.task.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.domain.CompInfo;
import com.ts.server.safe.channel.domain.ConService;
import com.ts.server.safe.channel.domain.Member;
import com.ts.server.safe.channel.service.CompInfoService;
import com.ts.server.safe.channel.service.ConServiceService;
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
    public CheckTaskService(CheckTaskDao dao, ConServiceService conService,
                            CompInfoService infoService, CheckContentService contentService,
                            MemberService memberService) {
        this.dao = dao;
        this.conService = conService;
        this.infoService = infoService;
        this.contentService = contentService;
        this.memberService = memberService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CheckTask save(CheckTask t, String[] contentIds){

        t.setId(IdGenerators.uuid());
        setService(t);
        setSups(t);
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

    private void setSups(CheckTask t){
        CompInfo info = infoService.get(t.getCompId());
        String[] supNames = new String[t.getCheckSupIds().length];
        for(int i = 0; i < t.getCheckSupIds().length; i++){
            String supId = t.getCheckSupIds()[i];
            if(!info.getIndCtgIds().contains(supId)){
                throw new BaseException("检查表不存在");
            }
            int index = info.getIndCtgIds().indexOf(supId);
            String name = info.getIndCtgNames().get(index);
            supNames[i] = name;
        }
        t.setCheckSupNames(supNames);
    }

    private void setCheckUser(CheckTask t){
        int len = t.getCheckUserIds().length;
        String[] userNames = new String[len];
        for(int i = 0; i < len; i++){
            String userId = t.getCheckUserIds()[i];
            Member m = memberService.get(userId);
            if(!StringUtils.equals(m.getChannelId(), t.getChannelId())){
                throw new BaseException("不能添加检查员");
            }
            userNames[i] = StringUtils.isNotBlank(m.getName())? m.getName(): m.getUsername();
        }
        t.setCheckUserNames(userNames);
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
        setSups(t);
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

    public Long count(String channelId, String compName, String checkUserName,
                      CheckTask.Status status, Date checkTimeFrom, Date checkTimeTo){

        return dao.count(channelId, compName, checkUserName, status, checkTimeFrom, checkTimeTo);
    }

    public List<CheckTask> query(String channelId, String compName, String checkUserName,
                                 CheckTask.Status status, Date checkTimeFrom, Date checkTimeTo, int offset, int limit){

        return dao.find(channelId, compName, checkUserName, status, checkTimeFrom, checkTimeTo, offset, limit);
    }
}
