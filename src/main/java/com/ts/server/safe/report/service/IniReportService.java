package com.ts.server.safe.report.service;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.service.CheckContentService;
import com.ts.server.safe.common.id.IdGenerators;
import com.ts.server.safe.report.dao.IniReportDao;
import com.ts.server.safe.report.domain.IniReport;
import com.ts.server.safe.report.service.export.ExportWord;
import com.ts.server.safe.report.service.export.ms.MsExportWord;
import com.ts.server.safe.report.service.export.wps.WpsExportWord;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * 初检报表服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@Service
@Transactional(readOnly = true)
public class IniReportService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckContentService.class);

    private final IniReportDao dao;

    @Autowired
    public IniReportService(IniReportDao dao) {
        this.dao = dao;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public IniReport save(IniReport t){

        boolean has = dao.hasCycleName(t.getChannelId(), t.getCycleName());
        if(has){
            throw new BaseException("检查周期名称已经存在");
        }

        t.setId(IdGenerators.uuid());
        dao.insert(t);

        return dao.findOne(t.getId());
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public IniReport update(IniReport t){
        IniReport o  = get(t.getId());
        if(!StringUtils.equals(o.getCycleName(), t.getCycleName()) &&
                dao.hasCycleName(o.getChannelId(), t.getCycleName())){
            throw new BaseException("检查周期名称已经存在");
        }

        if(!dao.update(t)){
            throw new BaseException("修改检查报表失败");
        }

        return get(t.getId());
    }

    public IniReport get(String id){
        try{
            return dao.findOne(id);
        }catch (DataAccessException e){
            throw new BaseException("检查报表不存在");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public boolean delete(String id){
        return dao.delete(id);
    }

    public boolean hasCycleName(String channelId, String cycleName){
        return dao.hasCycleName(channelId, cycleName);
    }

    public Long count(String channelId, String compName, String cycleName){
        return dao.count(channelId, compName, cycleName);
    }

    public List<IniReport> query(String channelId, String compName, String cycleName, int offset, int limit){
        return dao.find(channelId, compName, cycleName, offset, limit);
    }

    public void exportReport(OutputStream outputStream, String format, String id)throws IOException{
        ExportWord export = StringUtils.equals(format, "WPS")? new WpsExportWord(): new MsExportWord();
        export.export(outputStream, get(id));
    }
}
