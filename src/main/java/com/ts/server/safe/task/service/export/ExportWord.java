package com.ts.server.safe.task.service.export;

import com.ts.server.safe.task.domain.InitReportContent;

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
     * @param report {@link InitReportContent}
     * @throws IOException
     */
    void export(OutputStream outputStream, InitReportContent report)throws IOException;
}
