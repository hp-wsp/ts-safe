package com.ts.server.safe.task.service.export.initial.ms;

import com.ts.server.safe.task.domain.InitReportContent;
import com.ts.server.safe.task.service.export.PageBuilder;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;

/**
 * 构建文档封面页
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class CoverBuilder implements PageBuilder<InitReportContent> {
    private static final String[] ENT_SCALES = new String[]{"", "大型", "中型", "小型", "微型"};

    @Override
    public void build(XWPFDocument doc, InitReportContent report) {

        renderTitle(doc);

        renderContentTable(doc, report);

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
        MsUtils.setItemRun(paragraph.createRun(), 28, true, "安全生产社会化服务");

        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        MsUtils.setItemRun(paragraph.createRun(), 18, true, "检查报告");

        MsUtils.addEmptyParagraph(doc, 15);
    }

    private void renderContentTable(XWPFDocument doc, InitReportContent report){
        XWPFTable table = doc.createTable(5, 4);
        table.setTableAlignment(TableRowAlign.CENTER);
        setRowHeight(table);

        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setContentTableLabel(cell, "企业名称", 360 * 5);
        cell = row.getCell(1);
        setContentTableValue(cell, report.getCompName());
        cellBottomLine(row, 1, 4);
        MsUtils.mergeCellsH(row, 1, 4);


        row = table.getRow(1);
        cell = row.getCell(0);
        setContentTableLabel(cell, "委托检查单位", 360 * 5);
        cellBottomLine(row, 1, 2);
        cell = row.getCell(1);
        setCellWidth(cell, 360 * 9);
        XWPFParagraph paragraph  = cell.getParagraphArray(0);
        MsUtils.checkBox(paragraph, "主管部门", report.getEntCompType() == 0);
        MsUtils.checkBox(paragraph, "乡镇", report.getEntCompType() == 1);
        MsUtils.checkBox(paragraph, "企业", report.getEntCompType() == 2);
        cell = row.getCell(2);
        setContentTableLabel(cell, "企业规模", 360 * 4);
        cellBottomLine(row, 3, 4);
        cell = row.getCell(3);
        setCellWidth(cell, 360 * 3);
        setContentTableValue(cell, ENT_SCALES[report.getEntScale()]);

        row = table.getRow(2);
        cell = row.getCell(0);
        setContentTableLabel(cell, "所属行业", 360 * 5);
        cell = row.getCell(1);
        setContentTableValue(cell, report.getIndustry());
        cellBottomLine(row, 1, 4);
        MsUtils.mergeCellsH(row, 1, 4);

        row = table.getRow(3);
        cell = row.getCell(0);
        setContentTableLabel(cell, "所属区域", 360 * 5);
        cell = row.getCell(1);
        setContentTableValue(cell, report.getArea());
        cellBottomLine(row, 1, 4);
        MsUtils.mergeCellsH(row, 1, 4);

        row = table.getRow(4);
        cell = row.getCell(0);
        setContentTableLabel(cell, "检查日期", 360 * 5);
        cell = row.getCell(1);
        String checkDate = String.format("%s 至 %s",report.getCheckTimeFrom(), report.getCheckTimeTo());
        setContentTableValue(cell, checkDate);
        cellBottomLine(row, 1, 4);
        MsUtils.mergeCellsH(row, 1, 4);
    }

    private void setRowHeight(XWPFTable table){
        table.getRows().forEach(row -> {
            CTTrPr trPr = row.getCtRow().addNewTrPr();
            CTHeight th = trPr.addNewTrHeight();
            th.setVal(BigInteger.valueOf(450));
        });
    }

    private void setContentTableLabel(XWPFTableCell cell, String title, int widthPix){
        MsUtils.setCellWidthBorder(cell, widthPix, new boolean[]{false, false, false, false});
        XWPFParagraph paragraph  = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun run = paragraph.createRun();
        MsUtils.setItemRun(run, 12, false, title + "：");
    }

    private void setCellWidth(XWPFTableCell cell, int widthPix){
        CTTcPr tcPr = cell.getCTTc().addNewTcPr();
        CTTblWidth width = tcPr.addNewTcW();
        width.setW(BigInteger.valueOf(widthPix));
    }

    private void setContentTableValue(XWPFTableCell cell, String value){
        XWPFParagraph paragraph  = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        MsUtils.setItemRun(paragraph.createRun(), 12, false, value);
    }

    private void cellBottomLine(XWPFTableRow row, int fromCol, int toCol){
        for(int i = fromCol; i < toCol; i++){
            CTTcPr tcPr = row.getCell(i).getCTTc().addNewTcPr();
            CTVerticalJc va = tcPr.addNewVAlign();
            va.setVal(STVerticalJc.CENTER);
            CTTcBorders borders =  tcPr.addNewTcBorders();
            borders.addNewTop().setVal(STBorder.NIL);
            borders.addNewBottom().setVal(STBorder.NIL);
            borders.addNewLeft().setVal(STBorder.NIL);
            borders.addNewRight().setVal(STBorder.NIL);
            CTBorder bottomBorder = borders.addNewBottom();
            bottomBorder.setVal(STBorder.SINGLE);
            bottomBorder.setSz(BigInteger.valueOf(5));
        }
    }

    /**
     * 页脚
     *
     * @param doc {@link XWPFDocument}
     */
    private void renderFooter(XWPFDocument doc, InitReportContent report){
        MsUtils.addEmptyParagraph(doc, 15);

        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setText(report.getChannelName()+"编制");
        run.setFontSize(12);
        run.setBold(true);
        run.setFontFamily("宋体");

        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run = paragraph.createRun();
        run.setText("（盖章有效）");
        run.setFontSize(10);
        run.setFontFamily("宋体");
        run.addBreak(BreakType.PAGE);
    }
}
