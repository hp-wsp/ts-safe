package com.ts.server.safe.report.service;

import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.common.utils.DaoUtils;
import com.ts.server.safe.report.dao.ReportItemDao;
import com.ts.server.safe.report.domain.ReportItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 报表检查项目业务服务
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class ReportItemService {
    private final ReportItemDao dao;

    @Autowired
    public ReportItemService(ReportItemDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(String reportId, ReportItem.ReportType reportType,
                     String compId, List<ReportItem> items){

        for(ReportItem item: items){
            item.setId(IdGenerators.uuid());
            item.setCompId(compId);
            item.setReportId(reportId);
            item.setReportType(reportType);
        }

        dao.delete(reportId);
        dao.insertBatch(items);
    }

    public List<ReportItem> query(String reportId){
        return dao.find(reportId);
    }

}
