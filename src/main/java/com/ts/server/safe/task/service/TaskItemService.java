package com.ts.server.safe.task.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.task.dao.TaskItemDao;
import com.ts.server.safe.task.domain.TaskCheck;
import com.ts.server.safe.task.domain.TaskItem;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 检查内容业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class TaskItemService {
    private final TaskItemDao dao;

    @Autowired
    public TaskItemService(TaskItemDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public List<TaskItem> save(TaskCheck check, List<TaskItem> items){
        Assert.isTrue(!items.isEmpty(), "item is empty");

        removeItems(check.getId(), items);

        return items.stream().
                map(e -> StringUtils.isBlank(e.getId())? insert(check, e): update(e)).
                collect(Collectors.toList());
    }

    private void removeItems(String taskId, List<TaskItem> items){
        List<String> hasIds = dao.find(taskId).stream().map(TaskItem::getId).collect(Collectors.toList());
        items.stream().filter(e -> !hasIds.contains(e.getId())).forEach(e -> dao.delete(e.getId()));
    }

    private TaskItem insert(TaskCheck check, TaskItem t){

        t.setId(IdGenerators.uuid());
        t.setTaskId(check.getId());
        t.setId(IdGenerators.uuid());
        t.setConId(check.getConId());
        t.setCompId(check.getCompId());
        t.setInitial(check.isInitial());

        dao.insert(t);

        return dao.findOne(t.getId());
    }

    private TaskItem update(TaskItem item){
        TaskItem t = dao.findOne(item.getTaskId());

        t.setTypeId(item.getTypeId());
        t.setTypeName(item.getTypeName());
        t.setContent(item.getContent());
        t.setConDetail(item.getConDetail());
        t.setLawItem(item.getLawItem());

        dao.update(t);

        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TaskItem saveResult(String id, TaskItem.CheckResult result,
                               TaskItem.DangerLevel level, String danDesc,
                               String danSuggest, String[] images){

        if(!dao.updateResult(id, result, level, danDesc, danSuggest, images)){
            throw new BaseException("保存检查结果失败");
        }

        return get(id);
    }

    public TaskItem get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("任务内容不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteTask(String taskId){
        dao.deleteTask(taskId);
    }

    public List<TaskItem> query(String taskId){
        return dao.find(taskId);
    }
}
