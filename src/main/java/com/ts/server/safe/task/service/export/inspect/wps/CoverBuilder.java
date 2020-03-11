package com.ts.server.safe.task.service.export.inspect.wps;

import com.ts.server.safe.task.domain.InsReportContent;
import com.ts.server.safe.task.service.export.PageBuilder;
import com.ts.server.safe.task.service.export.WpsUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTHeight;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTrPr;

import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 核查报告封面
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class CoverBuilder implements PageBuilder<InsReportContent> {

    @Override
    public void build(XWPFDocument doc, InsReportContent report) {

        renderTitle(doc);

        renderContent(doc, report);

        renderFooter(doc, report);
    }

    /**
     * 标题
     *
     * @param doc {@link XWPFDocument}
     */
    private void renderTitle(XWPFDocument doc){
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setVerticalAlignment(TextAlignment.CENTER);
        WpsUtils.setItemRun(paragraph.createRun(), 28, true, "安全生产社会化服务核查报告");

        WpsUtils.addEmptyParagraph(doc, 15);
    }

    private void renderContent(XWPFDocument doc, InsReportContent report){

        renderContentRow1(doc, report);
        renderContentRow2(doc, report);
        renderContentRow3(doc, report);
        renderContentRow4(doc, report);
        renderContentRow5(doc, report);
    }

    private void renderContentRow1(XWPFDocument doc, InsReportContent report){
        XWPFTableRow row = createContentRow(doc,2);
        XWPFTableCell cell = row.getCell(0);
        setContentLabel(cell, "单位全称", 360 * 5);
        cell = row.getCell(1);
        setContentValue(cell, report.getCompName(), 360 * 16);
    }

    private XWPFTableRow createContentRow(XWPFDocument doc, int cols){
        XWPFTable table = doc.createTable(1, cols);
        table.setTableAlignment(TableRowAlign.CENTER);
        XWPFTableRow row = table.getRow(0);
        CTTrPr trPr = row.getCtRow().addNewTrPr();
        CTHeight th = trPr.addNewTrHeight();
        th.setVal(BigInteger.valueOf(450));
        return row;
    }

    private void setContentLabel(XWPFTableCell cell, String title, int widthPix){
        WpsUtils.setCellWidthBorder(cell, widthPix, new boolean[]{false, false, false, false});
        XWPFParagraph paragraph  = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        WpsUtils.setItemRun(paragraph.createRun(), 12, true, title + "：");
    }

    private void setContentValue(XWPFTableCell cell, String value, int widthPix){
        setBottomLine(cell, widthPix);
        XWPFParagraph paragraph  = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        WpsUtils.setItemRun(paragraph.createRun(), 12, false, value);
    }

    private void setBottomLine(XWPFTableCell cell, int widthPix){
        WpsUtils.setCellWidthBorder(cell, widthPix, new boolean[]{false, false, true, false});
    }

    private void renderContentRow2(XWPFDocument doc, InsReportContent report){
        XWPFTableRow row = createContentRow(doc,4);
        XWPFTableCell cell = row.getCell(0);
        setContentLabel(cell, "所在区域", 360 * 5);
        cell = row.getCell(1);
        setContentValue(cell, report.getArea(), 360 * 9);

        cell = row.getCell(2);
        setContentLabel(cell, "行业", 360 * 3);
        cell = row.getCell(3);
        setContentValue(cell, report.getIndustry(), 360 * 4);
    }

    private void renderContentRow3(XWPFDocument doc, InsReportContent report){
        XWPFTableRow row = createContentRow(doc,2);
        XWPFTableCell cell = row.getCell(0);
        setContentLabel(cell, "服务类型", 360 * 5);
        cell = row.getCell(1);
        setBottomLine(cell,360 * 16);
        XWPFParagraph paragraph  = cell.getParagraphArray(0);
        WpsUtils.checkBox(paragraph, "政府购买", report.getEntCompType() == 0);
        WpsUtils.checkBox(paragraph, "企业购买 ", report.getEntCompType() == 1);
    }

    private void renderContentRow4(XWPFDocument doc, InsReportContent report){
        XWPFTableRow row = createContentRow(doc,2);
        XWPFTableCell cell = row.getCell(0);
        setContentLabel(cell, "上次检查日期", 360 * 5);
        cell = row.getCell(1);
        setContentValue(cell, report.getLastTime(), 360 * 16);
    }

    private void renderContentRow5(XWPFDocument doc, InsReportContent report){
        XWPFTableRow row = createContentRow(doc,2);
        XWPFTableCell cell = row.getCell(0);
        setContentLabel(cell, "本次检查日期", 360 * 5);
        cell = row.getCell(1);
        setContentValue(cell, report.getCurrentTime(), 360 * 16);
    }

    /**
     * 页脚
     *
     * @param doc {@link XWPFDocument}
     */
    private void renderFooter(XWPFDocument doc, InsReportContent report){
        WpsUtils.addEmptyParagraph(doc, 15);

        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        WpsUtils.setItemRun(paragraph.createRun(), 12, true, report.getChannelName());

        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        WpsUtils.setItemRun(paragraph.createRun(), 10, false, "（盖章有效）");

        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        DateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日", Locale.CHINA);
        WpsUtils.setItemRun(paragraph.createRun(), 12, false, dateFormat.format(new Date()));
    }
}
