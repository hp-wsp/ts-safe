package com.ts.server.safe.logger.service;

import com.ts.server.safe.logger.dao.OptLogDao;
import com.ts.server.safe.logger.domain.OptLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * 日志操作业务服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class OptLogService {
    private final OptLogDao dao;

    @Autowired
    public OptLogService(OptLogDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(String type, String name, String username, String detail){
        OptLog t = new OptLog();

        t.setType(type);
        t.setName(name);
        t.setDetail(detail);
        t.setUsername(username);

        dao.insert(t);
    }

    public Long count(String type, String name, String detail, String username, Date fromDate, Date toDate){
        return dao.count(type, name, detail, username, fromDate, toDate);
    }

    public List<OptLog> query(String type, String name, String detail, String username, Date fromDate, Date toDate, int offset, int limit){
        return dao.find(type, name, detail, username, fromDate, toDate, offset, limit);
    }
}
