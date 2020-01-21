package com.ts.server.safe.report.service.export;

import com.ts.server.safe.report.domain.CheckReport;
import org.apache.poi.xwpf.usermodel.*;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 导出WORD报表
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ExportWord {
    private final PageBuilder coverBuilder;
    private final PageBuilder bzBuilder;

    /**
     * 构造{@link ExportWord}
     */
    public ExportWord(){
        this.coverBuilder = new CoverPageBuilder();
        this.bzBuilder = new BzPageBuilder();
    }

    /**
     * 导出WORD
     *
     * @param outputStream {@link OutputStream}
     * @param report {@link CheckReport}
     * @throws IOException
     */
    public void export(OutputStream outputStream, CheckReport report)throws IOException {

        try(XWPFDocument doc = new XWPFDocument()){
            coverBuilder.build(doc, report);
            bzBuilder.build(doc, report);
            doc.write(outputStream);
        }
    }
}
