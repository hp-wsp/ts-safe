package com.ts.server.safe.task.service.export.inspect.wps;

import com.ts.server.safe.task.domain.InsReportContent;
import com.ts.server.safe.task.service.export.ExportWord;
import com.ts.server.safe.task.service.export.PageBuilder;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 导出WPS
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class InspectExportWps implements ExportWord<InsReportContent> {
    private final PageBuilder<InsReportContent> coverBuilder;

    public InspectExportWps(){
        this.coverBuilder = new CoverBuilder();
    }

    @Override
    public void export(OutputStream outputStream, InsReportContent report) throws IOException {


        try(XWPFDocument doc = new XWPFDocument()){

            coverBuilder.build(doc, report);


            doc.write(outputStream);
        }
    }
}
