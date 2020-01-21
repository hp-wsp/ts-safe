package com.ts.server.safe.report.service.export;

import com.ts.server.safe.report.domain.CheckReport;
import org.apache.poi.xwpf.usermodel.*;

/**
 * 编制页
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class BzPageBuilder implements PageBuilder {

    @Override
    public void build(XWPFDocument doc, CheckReport report) {
        renderTitle(doc);

        renderProductInfo(doc, report);
        renderServiceRequest(doc, report);
        renderCompInfo(doc, report);
    }

    private void renderTitle(XWPFDocument doc){
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setText("一、编制说明");
        run.setFontSize(16);
        run.setBold(true);
    }

    private void renderProductInfo(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "100%", "1.安全生产社会化信息");

        table = createTable(doc,1, 4);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellLabel(cell, "17%", "委托单位");
        cell = row.getCell(1);
        setCellValue(cell, "40%", report.getChannelName());
        cell = row.getCell(2);
        setCellLabel(cell, "17%", "(合同)编号");
        cell = row.getCell(3);
        setCellValue(cell, "26%", report.getBzDetail().getConNum());
    }

    private XWPFTable createTable(XWPFDocument doc, int row, int col){
        XWPFTable table = doc.createTable(row, col);
        table.setTableAlignment(TableRowAlign.CENTER);
        table.setWidth("98%");
        return table;
    }

    private void setCellLabel(XWPFTableCell cell, String width, String label){
        setCell(cell, width, label, ParagraphAlignment.CENTER);
    }

    private void setCellValue(XWPFTableCell cell, String width, String value){
        setCell(cell, width, value, ParagraphAlignment.LEFT);
    }

    private void setCell(XWPFTableCell cell, String width, String value, ParagraphAlignment alignment){
        cell.setWidth(width);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(alignment);
        XWPFRun run = paragraph.createRun();
        run.setText(value);
        run.setFontSize(9);
    }

    private void renderServiceRequest(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "100%", "2.委托服务要求");

        table = createTable(doc,4, 2);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellLabel(cell, "30%", "周期和内容");
        cell = row.getCell(1);
        setCellValue(cell, "70%", report.getCheckDate());
        row = table.getRow(1);
        cell = row.getCell(0);
        setCellLabel(cell, "30%", "服务主要依据");
        cell = row.getCell(1);
        setCellValue(cell, "70%", report.getBzDetail().getServiceBase());
        row = table.getRow(2);
        cell = row.getCell(0);
        setCellLabel(cell, "30%", "服务方法");
        cell = row.getCell(1);
        setCellValue(cell, "70%", report.getBzDetail().getServiceMethod());
        row = table.getRow(3);
        cell = row.getCell(0);
        setCellLabel(cell, "30%", "服务范围");
        cell = row.getCell(1);
        setCellValue(cell, "70%", report.getBzDetail().getServiceRange());
    }


    private void renderCompInfo(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "100%", "3.受委托单位信息");


    }
}
