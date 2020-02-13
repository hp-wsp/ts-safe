package com.ts.server.safe.report.service.export.wps;

import com.ts.server.safe.report.domain.CheckReport;
import com.ts.server.safe.report.service.export.PageBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;

/**
 * 构建文档封面页
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class CoverBuilder implements PageBuilder {
    private static final String[] CONTENT_LABELS = new String[]{"企业名称", "委托检查单位,企业规模", "所属行业", "所属区域", "检查日期"};
    private static final String[] ENT_SCALES = new String[]{"", "大型", "中型", "小型", "微型"};
    private static final String[] ENT_COMP_TYPES = new String[]{"主管部门", "乡镇", "企业"};

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
        run.setVerticalAlignment("baseline");

        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run = paragraph.createRun();
        run.setText("检查报告");
        run.setFontSize(18);
        run.setBold(true);
        run.setTextPosition(400);
    }

    private void renderContentTable(XWPFDocument doc, CheckReport report){
        for(int i = 0; i < 5; i++){
            XWPFTable table = doc.createTable(1, i == 1? 4: 2);
            table.setTableAlignment(TableRowAlign.CENTER);
            XWPFTableRow row = createContentTableRow(table);
            String label = CONTENT_LABELS[i];

            if(i == 1){
                String[] array = StringUtils.split(label, ",");
                setContentTableLabel(row.getCell(0), array[0], 360 * 4);

                XWPFTableCell cell = row.getCell(1);
                setBottomLine(cell,360 * 8);
                XWPFParagraph paragraph  = cell.getParagraphArray(0);
                paragraph.setIndentationFirstLine(WpsUtils.CM_UNIT / 4);
                WpsUtils.checkBox(paragraph, "主管部门", report.getEntCompType() == 0);
                WpsUtils.checkBox(paragraph, "乡镇", report.getEntCompType() == 1);
                WpsUtils.checkBox(paragraph, "企业", report.getEntCompType() == 2);

                setContentTableLabel(row.getCell(2), array[1], 320 * 3);
                String v = getContentValue(report, i, 1);
                setContentTableValue(row.getCell(3), v, 360 * 3 + 120);
            }else {
                setContentTableLabel(row.getCell(0), label, 360 * 4);
                String v = getContentValue(report, i, 0);
                setContentTableValue(row.getCell(1), v, 360 * 14);
            }
        }
    }

    private XWPFTableRow createContentTableRow(XWPFTable table){
        XWPFTableRow row = table.getRow(0);
        CTTrPr trPr = row.getCtRow().addNewTrPr();
        CTHeight th = trPr.addNewTrHeight();
        th.setVal(BigInteger.valueOf(380));
        return row;
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
    }

    private void setContentTableValue(XWPFTableCell cell, String value, int widthPix){
        setBottomLine(cell, widthPix);

        XWPFParagraph paragraph  = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(WpsUtils.CM_UNIT / 4);
        XWPFRun run = paragraph.createRun();
        run.setBold(false);
        run.setFontSize(9);
        run.setText(value);
    }

    private void setBottomLine(XWPFTableCell cell, int widthPix){
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
        CTBorder bottomBorder = borders.addNewBottom();
        bottomBorder.setVal(STBorder.SINGLE);
        bottomBorder.setSz(BigInteger.valueOf(5));
    }

    private String getContentValue(CheckReport report, int i, int col){
        switch (i){
            case 0:
                return report.getCompName();
            case 1:
                if(col == 0){
                    return ENT_COMP_TYPES[report.getEntCompType()];
                }else{
                    return ENT_SCALES[report.getEntScale()];
                }
            case 2:
                return report.getIndustry();
            case 3:
                return report.getArea();
            default:
                //TODO 日期
                //return report.getCheckDate();
                return "";
        }
    }

    /**
     * 页脚
     *
     * @param doc {@link XWPFDocument}
     */
    private void renderFooter(XWPFDocument doc, CheckReport report){
        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.setText("\r");
        run.setTextPosition(420);

        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run = paragraph.createRun();
        run.setText(report.getChannelName());
        run.setFontSize(12);
        run.setBold(true);

        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        run = paragraph.createRun();
        run.setText("（盖章有效）");
        run.setTextPosition(50);
        run.setFontSize(8);
        run.addBreak(BreakType.PAGE);
    }
}
