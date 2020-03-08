package com.ts.server.safe.task.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.service.CheckContentService;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.task.dao.TaskReportDao;
import com.ts.server.safe.task.domain.InitReportContent;
import com.ts.server.safe.task.domain.TaskCheck;
import com.ts.server.safe.task.domain.TaskReport;
import com.ts.server.safe.task.service.export.ExportFac;
import com.ts.server.safe.task.service.export.ExportWord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.OutputStream;

/**
 * 检查报表服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckContentService.class);

    private final TaskReportDao<InitReportContent> initDao;
    private final TaskCheckService checkService;

    @Autowired
    public ReportService(DataSource dataSource, ObjectMapper objectMapper, TaskCheckService checkService) {
        this.initDao = new TaskReportDao<>(dataSource, objectMapper, InitReportContent.class);
        this.checkService = checkService;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @SuppressWarnings("unchecked")
    public TaskReport<?> save(TaskReport<?> t){
        TaskCheck check = checkService.get(t.getTaskId());
        if(check.isInitial()){
            return saveInitial(check, (TaskReport<InitReportContent>)t);
        }else{
            //复查报告
            return null;
        }
    }

    private TaskReport<InitReportContent> saveInitial(TaskCheck check, TaskReport<InitReportContent> t){
        if(initDao.has(t.getTaskId())){
            if(initDao.updateContent(t.getTaskId(), t.getContent())){
                return initDao.findOne(t.getTaskId());
            }else {
                throw new BaseException("修改报表失败");
            }
        }

        t.setChannelId(check.getChannelId());
        t.setCompId(check.getCompId());
        t.setCompName(check.getCompName());
        t.setServiceId(check.getServiceId());
        t.setServiceName(check.getServiceName());
        t.setInitial(check.isInitial());

        t.setId(IdGenerators.uuid());
        initDao.insert(t);

        return initDao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return initDao.delete(id);
    }

    public boolean has(String taskId){
        return initDao.has(taskId);
    }

    public TaskReport<?> get(String taskId){
        TaskCheck check = checkService.get(taskId);
        try{
            //TODO 核查报表
            return check.isInitial()? initDao.findOne(taskId): null;
        }catch (DataAccessException e){
            throw new BaseException("检查报表不存在");
        }

    }

    public void exportReport(OutputStream outputStream, String format, String taskId)throws IOException{
        if(!initDao.has(taskId)){
            throw new BaseException("报表还未生成");
        }
        TaskCheck check = checkService.get(taskId);
        if(check.isInitial()){
            TaskReport<InitReportContent> r = initDao.findOne(taskId);
            ExportWord export = ExportFac.initExport(format);
            export.export(outputStream, r.getContent());
        }
    }
}
