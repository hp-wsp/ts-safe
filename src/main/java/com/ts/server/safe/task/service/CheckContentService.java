package com.ts.server.safe.task.service;

import com.ts.server.safe.base.domain.UniCheckTable;
import com.ts.server.safe.base.service.UniCheckTableService;
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
    private final UniCheckTableService tableService;

    @Autowired
    public CheckContentService(CheckContentDao dao, UniCheckTableService tableService) {
        this.dao = dao;
        this.tableService = tableService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public CheckContent save(String taskId, String tableId){
        return dao.has(taskId, tableId)?
                update(taskId, tableId) : insert(taskId, tableId);
    }

    private CheckContent insert(String taskId, String tableId){
        CheckContent t = new CheckContent();

        UniCheckTable table = tableService.get(tableId);

        t.setId(IdGenerators.uuid());
        t.setTaskId(taskId);
        t.setTableId(table.getId());
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

    private CheckContent update(String taskId, String tableId){
        CheckContent t = dao.findOne(taskId, tableId);

        UniCheckTable table = tableService.get(tableId);
        t.setTableId(table.getId());
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

    public List<CheckContent> query(String taskId){
        return dao.find(taskId);
    }
}
