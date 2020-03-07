package com.ts.server.safe.task.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.base.domain.UniCheckContent;
import com.ts.server.safe.base.service.UniCheckContentService;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.task.dao.TaskItemDao;
import com.ts.server.safe.task.domain.TaskItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 检查内容业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class TaskItemService {
    private final TaskItemDao dao;
    private final UniCheckContentService tableService;

    @Autowired
    public TaskItemService(TaskItemDao dao, UniCheckContentService tableService) {
        this.dao = dao;
        this.tableService = tableService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TaskItem save(String taskId, String contentId){
        return dao.has(taskId, contentId)?
                update(taskId, contentId) : insert(taskId, contentId);
    }

    private TaskItem insert(String taskId, String contentId){
        TaskItem t = new TaskItem();

        UniCheckContent table = tableService.get(contentId);

        t.setId(IdGenerators.uuid());
        t.setTaskId(taskId);
        t.setContentId(table.getId());
        t.setTypeId(table.getTypeId());
        t.setTypeName(table.getTypeName());
        t.setItemId(table.getItemId());
        t.setItemName(table.getItemName());
        t.setContent(table.getContent());
        t.setConDetail(table.getConDetail());
        t.setLawItem(table.getLawItem());

        dao.insert(t);

        return dao.findOne(t.getId());
    }

    private TaskItem update(String taskId, String contentId){
        TaskItem t = dao.findOne(taskId, contentId);

        UniCheckContent table = tableService.get(contentId);
        t.setContentId(table.getId());
        t.setTypeId(table.getTypeId());
        t.setTypeName(table.getTypeName());
        t.setItemId(table.getItemId());
        t.setItemName(table.getItemName());
        t.setContent(table.getContent());
        t.setConDetail(table.getConDetail());
        t.setLawItem(table.getLawItem());

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