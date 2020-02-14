package com.ts.server.safe.report.service.export.wps;

import com.ts.server.safe.report.domain.CheckReport;
import com.ts.server.safe.report.service.export.PageBuilder;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;

/**
 * 构建文档封面页
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class CoverBuilder implements PageBuilder {
    private static final String[] ENT_SCALES = new String[]{"", "大型", "中型", "小型", "微型"};

    @Override
    public void build(XWPFDocument doc, CheckReport report) {

        renderTitle(doc);

        renderContent(doc, report);

        renderFooter(doc, report);

        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.createRun().addBreak(BreakType.PAGE);
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
        WpsUtils.setItemRun(paragraph.createRun(), 28, true, "安全生产社会化服务");

        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        WpsUtils.setItemRun(paragraph.createRun(), 18, true, "检查报告");

        WpsUtils.addEmptyParagraph(doc, 15);
    }

    private void renderContent(XWPFDocument doc, CheckReport report){

        renderContentRow1(doc, report);
        renderContentRow2(doc, report);
        renderContentRow3(doc, report);
        renderContentRow4(doc, report);
        renderContentRow5(doc, report);
    }

    private void renderContentRow1(XWPFDocument doc, CheckReport report){
        XWPFTableRow row = createContentRow(doc,2);
        XWPFTableCell cell = row.getCell(0);
        setContentLabel(cell, "企业名称", 360 * 5);
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

    private void renderContentRow2(XWPFDocument doc, CheckReport report){
        XWPFTableRow row = createContentRow(doc,4);
        XWPFTableCell cell = row.getCell(0);
        setContentLabel(cell, "委托检查单位", 360 * 5);
        cell = row.getCell(1);
        setBottomLine(cell,360 * 9);
        XWPFParagraph paragraph  = cell.getParagraphArray(0);
        WpsUtils.checkBox(paragraph, "主管部门", report.getEntCompType() == 0);
        WpsUtils.checkBox(paragraph, "乡镇", report.getEntCompType() == 1);
        WpsUtils.checkBox(paragraph, "企业", report.getEntCompType() == 2);

        cell = row.getCell(2);
        setContentLabel(cell, "企业规模", 360 * 4);
        cell = row.getCell(3);
        setContentValue(cell, ENT_SCALES[report.getEntScale()], 360 * 3);
    }

    private void renderContentRow3(XWPFDocument doc, CheckReport report){
        XWPFTableRow row = createContentRow(doc,2);
        XWPFTableCell cell = row.getCell(0);
        setContentLabel(cell, "所属行业", 360 * 5);
        cell = row.getCell(1);
        setContentValue(cell, report.getIndustry(), 360 * 16);
    }

    private void renderContentRow4(XWPFDocument doc, CheckReport report){
        XWPFTableRow row = createContentRow(doc,2);
        XWPFTableCell cell = row.getCell(0);
        setContentLabel(cell, "所属区域", 360 * 5);
        cell = row.getCell(1);
        setContentValue(cell, report.getArea(), 360 * 16);
    }

    private void renderContentRow5(XWPFDocument doc, CheckReport report){
        XWPFTableRow row = createContentRow(doc,2);
        XWPFTableCell cell = row.getCell(0);
        setContentLabel(cell, "检查日期", 360 * 5);
        cell = row.getCell(1);
        String content = String.format("%s 至 %s", report.getCheckTimeFrom(), report.getCheckTimeTo());
        setContentValue(cell, content, 360 * 16);
    }

    /**
     * 页脚
     *
     * @param doc {@link XWPFDocument}
     */
    private void renderFooter(XWPFDocument doc, CheckReport report){
        WpsUtils.addEmptyParagraph(doc, 18);

        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        WpsUtils.setItemRun(paragraph.createRun(), 12, true, report.getChannelName());

        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        WpsUtils.setItemRun(paragraph.createRun(), 10, false, "（盖章有效）");
    }
}
