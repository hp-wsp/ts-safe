package com.ts.server.safe.report.service.export.ms;

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

        XWPFRun run = paragraph.createRun();
        run.setText("安全生产社会化服务");
        run.setFontSize(28);
        run.setBold(true);
        run.setFontFamily("宋体");
        run.setVerticalAlignment("baseline");

        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run = paragraph.createRun();
        run.setText("检查报告");
        run.setFontSize(18);
        run.setBold(true);
        run.setFontFamily("宋体");
        run.setTextPosition(400);
    }

    private void renderContentTable(XWPFDocument doc, CheckReport report){
        XWPFTable table = doc.createTable(5, 4);
        table.setTableAlignment(TableRowAlign.CENTER);
        setRowHeight(table);


        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setContentTableLabel(cell, "企业名称", 360 * 4);
        cell = row.getCell(1);
        setContentTableValue(cell, report.getCompName());
        cellBottomLine(row, 1, 4);
        MsUtils.mergeCellsH(row, 1, 4);


        row = table.getRow(1);
        cell = row.getCell(0);
        setContentTableLabel(cell, "委托检查单位", 360 * 4);
        cellBottomLine(row, 1, 2);
        cell = row.getCell(1);
        setCellWidth(cell, 360 * 8);
        XWPFParagraph paragraph  = cell.getParagraphArray(0);
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT / 4);
        MsUtils.checkBox(paragraph, "主管部门", report.getEntCompType() == 0);
        MsUtils.checkBox(paragraph, "乡镇", report.getEntCompType() == 1);
        MsUtils.checkBox(paragraph, "企业", report.getEntCompType() == 2);
        cell = row.getCell(2);
        setContentTableLabel(cell, "企业规模", 360 * 3);
        cellBottomLine(row, 3, 4);
        cell = row.getCell(3);
        setCellWidth(cell, 360 * 3 + 120);
        setContentTableValue(cell, ENT_SCALES[report.getEntScale()]);

        row = table.getRow(2);
        cell = row.getCell(0);
        setContentTableLabel(cell, "所属行业", 360 * 4);
        cell = row.getCell(1);
        setContentTableValue(cell, report.getIndustry());
        cellBottomLine(row, 1, 4);
        MsUtils.mergeCellsH(row, 1, 4);

        row = table.getRow(3);
        cell = row.getCell(0);
        setContentTableLabel(cell, "所属区域", 360 * 4);
        cell = row.getCell(1);
        setContentTableValue(cell, report.getArea());
        cellBottomLine(row, 1, 4);
        MsUtils.mergeCellsH(row, 1, 4);

        row = table.getRow(4);
        cell = row.getCell(0);
        setContentTableLabel(cell, "检查日期", 360 * 4);
        cell = row.getCell(1);
        setContentTableValue(cell, report.getCheckDate());
        cellBottomLine(row, 1, 4);
        MsUtils.mergeCellsH(row, 1, 4);
    }

    private void setRowHeight(XWPFTable table){
        table.getRows().forEach(row -> {
            CTTrPr trPr = row.getCtRow().addNewTrPr();
            CTHeight th = trPr.addNewTrHeight();
            th.setVal(BigInteger.valueOf(400));
        });
    }

    private void setContentTableLabel(XWPFTableCell cell, String title, int widthPix){
        CTTcPr tcPr = cell.getCTTc().addNewTcPr();
        CTTblWidth width = tcPr.addNewTcW();
        width.setW(BigInteger.valueOf(widthPix));
        CTVerticalJc va = tcPr.addNewVAlign();
        va.setVal(STVerticalJc.CENTER);
        CTTcBorders borders =  tcPr.addNewTcBorders();
        borders.addNewTop().setVal(STBorder.NIL);
        borders.addNewBottom().setVal(STBorder.NIL);
        borders.addNewLeft().setVal(STBorder.NIL);
        borders.addNewRight().setVal(STBorder.NIL);
        XWPFParagraph paragraph  = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun run = paragraph.createRun();
        run.setText(title + ": ");
        run.setBold(true);
        run.setFontSize(9);
        run.setFontFamily("宋体");
    }

    private void setCellWidth(XWPFTableCell cell, int widthPix){
        CTTcPr tcPr = cell.getCTTc().addNewTcPr();
        CTTblWidth width = tcPr.addNewTcW();
        width.setW(BigInteger.valueOf(widthPix));
    }

    private void setContentTableValue(XWPFTableCell cell, String value){
        XWPFParagraph paragraph  = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT / 4);
        XWPFRun run = paragraph.createRun();
        run.setBold(false);
        run.setFontSize(9);
        run.setText(value);
        run.setFontFamily("宋体");
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
    private void renderFooter(XWPFDocument doc, CheckReport report){
        for(int i = 0; i < 15; i++){
            XWPFParagraph paragraph = doc.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            XWPFRun run = paragraph.createRun();
            run.setText(" ");
            run.setFontSize(10);
        }

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
