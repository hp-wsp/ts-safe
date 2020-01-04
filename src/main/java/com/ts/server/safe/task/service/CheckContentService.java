package com.ts.server.safe.task.service;

import com.ts.server.safe.base.domain.UniCheckContent;
import com.ts.server.safe.base.service.UniCheckContentService;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.task.dao.CheckContentDao;
import com.ts.server.safe.task.domain.CheckContent;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CheckContentService {
    private final CheckContentDao dao;
    private final UniCheckContentService tableService;

    @Autowired
    public CheckContentService(CheckContentDao dao, UniCheckContentService tableService) {
        this.dao = dao;
        this.tableService = tableService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CheckContent save(String taskId, String contentId){
        return dao.has(taskId, contentId)?
                update(taskId, contentId) : insert(taskId, contentId);
    }

    private CheckContent insert(String taskId, String contentId){
        CheckContent t = new CheckContent();

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

    private CheckContent update(String taskId, String contentId){
        CheckContent t = dao.findOne(taskId, contentId);

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
    public boolean delete(String id){
        return dao.delete(id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteTask(String taskId){
        dao.deleteTask(taskId);
    }

    public List<CheckContent> query(String taskId){
        return dao.find(taskId);
    }
}
