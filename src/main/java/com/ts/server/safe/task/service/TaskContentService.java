package com.ts.server.safe.task.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.base.domain.UniCheckContent;
import com.ts.server.safe.base.service.UniCheckContentService;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.task.dao.TaskContentDao;
import com.ts.server.safe.task.domain.TaskContent;
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
public class TaskContentService {
    private final TaskContentDao dao;
    private final UniCheckContentService tableService;

    @Autowired
    public TaskContentService(TaskContentDao dao, UniCheckContentService tableService) {
        this.dao = dao;
        this.tableService = tableService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public TaskContent save(String taskId, String contentId){
        return dao.has(taskId, contentId)?
                update(taskId, contentId) : insert(taskId, contentId);
    }

    private TaskContent insert(String taskId, String contentId){
        TaskContent t = new TaskContent();

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

    private TaskContent update(String taskId, String contentId){
        TaskContent t = dao.findOne(taskId, contentId);

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
    public TaskContent saveResult(String id, TaskContent.CheckResult result,
                                  TaskContent.DangerLevel level, String danDesc,
                                  String danSuggest, String[] images){

        if(!dao.updateResult(id, result, level, danDesc, danSuggest, images)){
            throw new BaseException("保存检查结果失败");
        }

        return get(id);
    }

    public TaskContent get(String id){
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

    public List<TaskContent> query(String taskId){
        return dao.find(taskId);
    }
}
