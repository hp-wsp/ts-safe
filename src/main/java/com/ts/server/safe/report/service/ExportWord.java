package com.ts.server.safe.report.service;

import com.ts.server.safe.report.domain.CheckReport;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

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

            setTitle(doc, report);

            doc.write(outputStream);
        }

    }

    private void setTitle(XWPFDocument doc, CheckReport report){
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

        paragraph = doc.createParagraph();
        run = paragraph.createRun();
        run.setTextPosition(200);

        XWPFTable table = doc.createTable(5, 2);
        CTTblGridCol  col = table.getCTTbl().addNewTblGrid().addNewGridCol();
        col.s
        STTwipsMeasure measure = STTwipsMeasure.Factory.newInstance();
        col.xsetW(STTwipsMeasure);
        table.setTableAlignment(TableRowAlign.CENTER);
        setTableNoneBorder(table);

        XWPFTableCell cell = table.getRow(0).getCell(0);
        setTitleTableLabel(cell, "企业名称:");
        cell = table.getRow(1).getCell(0);
        setTitleTableLabel(cell, "委托检查单位:");
        cell = table.getRow(2).getCell(0);
        setTitleTableLabel(cell, "所属行业:");
        cell = table.getRow(3).getCell(0);
        setTitleTableLabel(cell, "所属区域:");
        cell = table.getRow(4).getCell(0);
        setTitleTableLabel(cell, "检查日期:");

        setTitleTableValue(doc, table);
    }

    private void setTableNoneBorder(XWPFTable table){
        table.setTopBorder(XWPFTable.XWPFBorderType.NIL, 0, 0, "");
        table.setLeftBorder(XWPFTable.XWPFBorderType.NIL, 0, 0, "");
        table.setRightBorder(XWPFTable.XWPFBorderType.NIL, 0, 0, "");
        table.setBottomBorder(XWPFTable.XWPFBorderType.NIL, 0, 0, "");
        table.setInsideHBorder(XWPFTable.XWPFBorderType.NIL, 0, 0, "");
        table.setInsideVBorder(XWPFTable.XWPFBorderType.NIL, 0, 0, "");
    }

    private void setTitleTableLabel(XWPFTableCell cell, String title){
        XWPFParagraph paragraph  = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        XWPFRun run = paragraph.createRun();
        run.setText(title);
        run.setBold(true);
        run.setFontSize(9);
    }

    private void setTitleTableValue(XWPFDocument doc, XWPFTable table){
        for(int i = 0; i < 5; i++){
            XWPFTableCell cell = table.getRow(i).getCell(1);
            cell.setWidth("80%");
        }
    }
}
