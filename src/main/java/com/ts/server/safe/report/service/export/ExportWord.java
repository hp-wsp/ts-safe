package com.ts.server.safe.report.service.export;

import com.ts.server.safe.report.domain.IniReport;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 导出Word
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public interface ExportWord {

    /**
     * 导出WORD文档
     *
     * @param outputStream {@link OutputStream}
     * @param report {@link IniReport}
     * @throws IOException
     */
    void export(OutputStream outputStream, IniReport report)throws IOException;
}
