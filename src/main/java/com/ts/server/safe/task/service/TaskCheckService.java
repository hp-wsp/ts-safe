package com.ts.server.safe.task.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.contract.domain.ConService;
import com.ts.server.safe.channel.domain.Member;
import com.ts.server.safe.contract.service.ConServiceService;
import com.ts.server.safe.channel.service.MemberService;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.task.dao.TaskCheckDao;
import com.ts.server.safe.task.domain.TaskItem;
import com.ts.server.safe.task.domain.TaskCheck;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 检查任务业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class TaskCheckService {
    private final TaskCheckDao dao;
    private final ConServiceService conService;
    private final TaskItemService itemService;
    private final MemberService memberService;

    @Autowired
    public TaskCheckService(TaskCheckDao dao, ConServiceService conService,
                            TaskItemService itemService, MemberService memberService) {
        this.dao = dao;
        this.conService = conService;
        this.itemService = itemService;
        this.memberService = memberService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TaskCheck save(TaskCheck t, List<TaskItem> items, String userId){
        if(dao.hasService(t.getServiceId())){
            throw new BaseException("任务已经创建");
        }

        t.setId(IdGenerators.uuid());
        t.setNum(buildNum());
        t.setStatus(TaskCheck.Status.WAIT);
        setService(t, userId);
        setCheckUser(t);
        dao.insert(t);

        for(TaskItem item: items){
            item.setConId(t.getConId());
            item.setCompId(t.getCompId());
            item.setTaskId(t.getId());
        }
        List<TaskItem> all = new ArrayList<>(items);
        if(t.isReview()){
            all.addAll(getLastNotPassItems(t));
        }
        itemService.save(t, all);

        return dao.findOne(t.getId());
    }

    private String buildNum(){
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String num = dao.genNum();
        String random = String.format("%02d", RandomUtils.nextInt(0, 100));
        return "JCRW" + date + num + random;
    }

    private void setService(TaskCheck t, String userId){
        ConService conSer = conService.get(t.getServiceId());
        //TODO 判断服务状态
        if(!StringUtils.equals(t.getChannelId(), conSer.getChannelId())){
            throw new BaseException("不能添加检查任务");
        }
        if(!StringUtils.equals(conSer.getLeaId(), userId)){
            throw new BaseException("不能创建任务");
        }
        t.setConId(conSer.getConId());
        t.setServiceName(conSer.getName());
        t.setCompId(conSer.getCompId());
        t.setCompName(conSer.getCompName());
        t.setInitial(conSer.isInitial());
        t.setLeaId(conSer.getLeaId());
        t.setLeaName(conSer.getLeaName());
    }

    private void setCheckUser(TaskCheck t){
        for(TaskCheck.CheckUser u: t.getCheckUsers()){
            Member m = memberService.get(u.getId());
            if(!StringUtils.equals(m.getChannelId(), t.getChannelId())){
                throw new BaseException("不能添加检查员");
            }
            u.setName(StringUtils.isBlank(m.getName())? m.getUsername(): m.getName());
        }
    }

    private List<TaskItem> getLastNotPassItems(TaskCheck task){

        return dao.findLast(task.getConId(), task.getCompId()).
                map(e -> filterNotPassItems(e.getId())).
                orElseGet(Collections::emptyList);
    }

    private List<TaskItem> filterNotPassItems(String taskId){
        return itemService.query(taskId).stream().
                filter(c -> c.getCheckResult() == TaskItem.CheckResult.NOT_PASS).
                peek(i -> i.setId(null)).
                collect(Collectors.toList());
    }

    public TaskCheck get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("检查任务不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TaskCheck update(TaskCheck t, List<TaskItem> items, String userId){
        TaskCheck o = get(t.getId());
        t.setStatus(o.getStatus());
        if(!StringUtils.equals(t.getChannelId(), o.getChannelId())){
            throw new BaseException("不能修改检查任务");
        }

        setService(t, userId);
        setCheckUser(t);

        if(!dao.update(t)){
            throw new BaseException("修改检查任务失败");
        }

        for(TaskItem item: items){
            item.setConId(t.getConId());
            item.setCompId(t.getCompId());
            item.setTaskId(t.getId());
        }
        itemService.save(t, items);

        return get(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        boolean success = dao.delete(id);
        if(success){
            itemService.deleteTask(id);
        }
        return success;
    }

    public Long count(String channelId, String compName, TaskCheck.Status status, Date checkTimeFrom, Date checkTimeTo){
        return dao.count(channelId, compName, status, checkTimeFrom, checkTimeTo);
    }

    public List<TaskCheck> query(String channelId, String compName, TaskCheck.Status status,
                                 Date checkTimeFrom, Date checkTimeTo, int offset, int limit){

        return dao.find(channelId, compName, status, checkTimeFrom, checkTimeTo, offset, limit);
    }

    public Long count(String leaId, String compName){
        return dao.count(leaId, compName);
    }

    public List<TaskCheck> query(String leaId, String compName, int offset, int limit){
        return dao.find(leaId, compName, offset, limit);
    }

    public List<TaskCheck> queryByServiceId(String serviceId){
        return dao.findByServiceId(serviceId);
    }
}
