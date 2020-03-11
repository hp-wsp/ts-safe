package com.ts.server.safe.task.service.export;

import org.apache.poi.xwpf.usermodel.XWPFDocument;

/**
 * 构建Word页
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public interface PageBuilder<T> {

    /**
     * 构建WORD页接口
     *
     * @param doc {@link XWPFDocument}
     * @param report 报表对象
     */
    void build(XWPFDocument doc, T report);
}
