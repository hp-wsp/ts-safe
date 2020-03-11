package com.ts.server.safe.task.service.export;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 导出Word
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public interface ExportWord<T> {

    /**
     * 导出WORD文档
     *
     * @param outputStream {@link OutputStream}
     * @param report 报表对象
     * @throws IOException
     */
    void export(OutputStream outputStream, T report)throws IOException;
}
