package com.ts.server.safe.report.service.export;

import com.ts.server.safe.report.domain.CheckReport;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * 构建Word页
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public interface PageBuilder {

    /**
     * 构建WORD页接口
     *
     * @param doc {@link XWPFDocument}
     * @param report {@link CheckReport}
     */
    void build(XWPFDocument doc, CheckReport report);
}
