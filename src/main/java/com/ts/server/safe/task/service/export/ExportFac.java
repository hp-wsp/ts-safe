package com.ts.server.safe.task.service.export;

import com.ts.server.safe.task.service.export.initial.ms.InitMsExportWord;
import com.ts.server.safe.task.service.export.initial.wps.InitWpsExportWord;
import org.apache.commons.lang3.StringUtils;

/**
 * 导出WORD报表
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ExportFac {

    /**
     * 导出初检报表
     *
     * @param format WPS
     * @return {@link ExportWord}
     */
    public static ExportWord initExport(String format){
        return StringUtils.equals(format, "WPS")? new InitWpsExportWord(): new InitMsExportWord();
    }
}
