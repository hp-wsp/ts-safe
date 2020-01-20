package com.ts.server.safe.report.service;

import com.ts.server.safe.report.domain.CheckReport;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;

/**
 * 导出WORD报表
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ExportWord {

    /**
     * 导出WORD
     *
     * @param outputStream {@link OutputStream}
     * @param report {@link CheckReport}
     * @throws IOException
     */
    public void export(OutputStream outputStream, CheckReport report)throws IOException {

        try(XWPFDocument doc = new XWPFDocument()){
            renderCover(doc, report);
            doc.write(outputStream);
        }

    }

    /**
     * 构建封面页
     *
     * @param doc {@link XWPFDocument}
     * @param report {@link CheckReport}
     */
    private void renderCover(XWPFDocument doc, CheckReport report){

        renderTitle(doc);

        renderCoverContent(doc, report);
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
        run.setTextPosition(200);
    }

    private void renderCoverContent(XWPFDocument doc, CheckReport report){
        String[] labels = new String[]{"企业名称", "委托检查单位,企业规模", "所属行业", "所属区域", "检查日期"};

        for(int i = 0; i < 5; i++){
            XWPFTable table = doc.createTable(1, i == 1? 4: 2);
            table.setTableAlignment(TableRowAlign.CENTER);
            XWPFTableRow row = table.getRow(0);
            setTitleTableRow(row);
            if(i == 1){
                String[] array = StringUtils.split(labels[i], ",");
                setTitleTableLabel(row.getCell(0), array[0], 360 * 4);
                setTitleTableValue(row.getCell(1), i, report, 360 * 6);
                setTitleTableLabel(row.getCell(2), array[1], 360 * 3);
                setTitleTableValue(row.getCell(3), i, report, 360 * 3);
            }else {
                setTitleTableLabel(row.getCell(0), labels[i], 360 * 4);
                setTitleTableValue(row.getCell(1), i, report, 360 * 12);
            }
        }
    }

    private void setTitleTableRow(XWPFTableRow row){
        CTTrPr trPr = row.getCtRow().addNewTrPr();
        CTHeight th = trPr.addNewTrHeight();
        th.setVal(BigInteger.valueOf(380));
    }

    private void setTitleTableLabel(XWPFTableCell cell, String title, int widthPix){
        CTTcPr tcpr = cell.getCTTc().addNewTcPr();
        CTTblWidth width = tcpr.addNewTcW();
        width.setW(BigInteger.valueOf(widthPix));
        CTVerticalJc va = tcpr.addNewVAlign();
        va.setVal(STVerticalJc.CENTER);
        CTTcBorders borders =  tcpr.addNewTcBorders();
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

    private void setTitleTableValue(XWPFTableCell cell, int rowIndex, CheckReport report, int widthPix){
        CTTcPr tcpr = cell.getCTTc().addNewTcPr();
        CTTblWidth width = tcpr.addNewTcW();
        width.setW(BigInteger.valueOf(widthPix));
        CTVerticalJc va = tcpr.addNewVAlign();
        va.setVal(STVerticalJc.CENTER);
        CTTcBorders borders =  tcpr.addNewTcBorders();
        borders.addNewTop().setVal(STBorder.NIL);
        borders.addNewBottom().setVal(STBorder.NIL);
        borders.addNewLeft().setVal(STBorder.NIL);
        borders.addNewRight().setVal(STBorder.NIL);
        CTBorder bottomBorder = borders.addNewBottom();
        bottomBorder.setVal(STBorder.SINGLE);
        bottomBorder.setSz(BigInteger.valueOf(5));
        XWPFParagraph paragraph  = cell.getParagraphArray(0);

        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = paragraph.createRun();
        run.setBold(false);
        run.setFontSize(9);

        switch (rowIndex){
            case 0:
                run.setText(" " + report.getCompName());
                break;
            case 1:
                run.setText(" " + report.getChannelName());
                break;
            case 2:
                run.setText(" " + report.getIndustry());
                break;
            case 3:
                run.setText(" " + report.getArea());
                break;
            default:
                run.setText(" " + report.getCheckDate());
        }
    }
}
