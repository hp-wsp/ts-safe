package com.ts.server.safe.report.service.export.wps;

import com.ts.server.safe.report.domain.CheckReport;
import com.ts.server.safe.report.service.export.PageBuilder;
import org.apache.poi.xwpf.usermodel.*;

import java.util.Objects;

/**
 * 构建编制说明页
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class EntrustBuilder implements PageBuilder {

    @Override
    public void build(XWPFDocument doc, CheckReport report) {
        renderTitle(doc);

        renderProductInfo(doc, report);
        renderServiceRequest(doc, report);
        renderCompInfo(doc, report);
        renderContactPerson(doc, report);
        renderWorkPerson(doc, report);
        renderSignature(doc, report);
        renderFooter(doc);
    }

    private void renderTitle(XWPFDocument doc){
        WpsUtils.pageTitle(doc, "一、编制说明");
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
        setCellValueLeft(cell, "40%", report.getChannelName());
        cell = row.getCell(2);
        setCellLabel(cell, "17%", "(合同)编号");
        cell = row.getCell(3);
        setCellValueLeft(cell, "26%", report.getBzDetail().getConNum());
    }

    private XWPFTable createTable(XWPFDocument doc, int row, int col){
        return WpsUtils.createTable(doc, row, col);
    }

    private void setCellLabel(XWPFTableCell cell, String width, String label){
        setCell(cell, width, label, ParagraphAlignment.CENTER);
    }

    private void setCellValueLeft(XWPFTableCell cell, String width, String value){
        setCell(cell, width, value, ParagraphAlignment.LEFT);
    }

    private void setCellValueCenter(XWPFTableCell cell, String width, String value){
        setCell(cell, width, value, ParagraphAlignment.CENTER);
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
        setCellLabel(cell, "25%", "周期和内容");
        cell = row.getCell(1);
        setCellValueLeft(cell, "75%", report.getCheckDate());
        row = table.getRow(1);
        cell = row.getCell(0);
        setCellLabel(cell, "25%", "服务主要依据");
        cell = row.getCell(1);
        setCellValueLeft(cell, "75%", report.getBzDetail().getServiceBase());
        row = table.getRow(2);
        cell = row.getCell(0);
        setCellLabel(cell, "25%", "服务方法");
        cell = row.getCell(1);
        setCellValueLeft(cell, "75%", report.getBzDetail().getServiceMethod());
        row = table.getRow(3);
        cell = row.getCell(0);
        setCellLabel(cell, "25%", "服务范围");
        cell = row.getCell(1);
        setCellValueLeft(cell, "75%", report.getBzDetail().getServiceRange());
    }


    private void renderCompInfo(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "100%", "3.受委托单位信息");

        table = createTable(doc, 3, 2);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellLabel(cell, "25%", "单位名称");
        cell = row.getCell(1);
        setCellValueLeft(cell, "75%", report.getBzDetail().getChanName());
        row = table.getRow(1);
        cell = row.getCell(0);
        setCellLabel(cell, "25%", "单位地址");
        cell = row.getCell(1);
        setCellValueLeft(cell, "75%", report.getBzDetail().getChanAddress());
        row = table.getRow(2);
        cell = row.getCell(0);
        setCellLabel(cell, "25%", "经营范围");
        cell = row.getCell(1);
        setCellValueLeft(cell, "75%",  report.getBzDetail().getChanBusScope());
    }

    private void renderContactPerson(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 1, 6);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "15%", "法定代表人");
        cell = row.getCell(1);
        setCellValueCenter(cell, "20%", report.getBzDetail().getChanContact().getName());
        cell = row.getCell(2);
        setCellLabel(cell, "10%", "电话");
        cell = row.getCell(3);
        setCellValueCenter(cell, "25%", report.getBzDetail().getChanContact().getPhone());
        cell = row.getCell(4);
        setCellLabel(cell, "10%", "手机");
        cell = row.getCell(5);
        setCellValueCenter(cell, "20", report.getBzDetail().getChanContact().getMobile());
    }

    private void renderWorkPerson(XWPFDocument doc, CheckReport report){
        int rowCount = report.getBzDetail().getWorkers().size() + 2;
        XWPFTable table = createTable(doc, rowCount, 5);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "15%", "分工");
        cell = row.getCell(1);
        setCellLabel(cell, "20%", "姓名");
        cell = row.getCell(2);
        setCellLabel(cell, "35%", "职务/职称");
        cell = row.getCell(3);
        setCellLabel(cell, "10%", "专业");
        cell = row.getCell(4);
        setCellLabel(cell, "20%", "联系电话");
        row = table.getRow(1);
        cell = row.getCell(0);
        setCellLabel(cell, "15%", "组长");
        cell = row.getCell(1);
        setCellValueCenter(cell, "20%", report.getBzDetail().getLeader().getName());
        cell = row.getCell(2);
        setCellValueCenter(cell, "35%", "");
        cell = row.getCell(3);
        setCellValueCenter(cell, "10%", "");
        cell = row.getCell(4);
        setCellValueCenter(cell, "20%", report.getBzDetail().getLeader().getPhone());
        for(int i = 2; i < rowCount; i++){
            CheckReport.PersonInfo worker = report.getBzDetail().getWorkers().get(i - 2);
            row = table.getRow(i);
            cell = row.getCell(0);
            setCellLabel(cell, "15%", "成员");
            cell = row.getCell(1);
            setCellValueCenter(cell, "20%", worker.getName());
            cell = row.getCell(2);
            setCellValueCenter(cell, "35%", "");
            cell = row.getCell(3);
            setCellValueCenter(cell, "10%", "");
            cell = row.getCell(4);
            setCellValueCenter(cell, "20%", worker.getPhone());
        }
    }

    private void renderSignature(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 3, 6);

        CheckReport.PersonInfo personInfo = report.getBzDetail().getPrincipalPerson() == null?
                new CheckReport.PersonInfo(): report.getBzDetail().getPrincipalPerson();
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "15%", "主要负责人");
        cell = row.getCell(1);
        setCellValueCenter(cell, "20%", personInfo.getName());
        cell = row.getCell(2);
        setCellLabel(cell, "10%", "手机");
        cell = row.getCell(3);
        setCellValueCenter(cell, "25%", personInfo.getMobile());
        cell = row.getCell(4);
        setCellLabel(cell, "10%", "签名");
        cell = row.getCell(5);
        setCellValueCenter(cell, "20%", "");

        personInfo = report.getBzDetail().getAuditPerson() == null?
                new CheckReport.PersonInfo(): report.getBzDetail().getAuditPerson();
        row = table.getRow(1);
        cell = row.getCell(0);
        setCellLabel(cell, "15%", "报告审核人");
        cell = row.getCell(1);
        setCellValueCenter(cell, "20%", personInfo.getName());
        cell = row.getCell(2);
        setCellLabel(cell, "10%", "手机");
        cell = row.getCell(3);
        setCellValueCenter(cell, "25%", personInfo.getMobile());
        cell = row.getCell(4);
        setCellLabel(cell, "10%", "");
        cell = row.getCell(5);
        setCellValueCenter(cell, "20%", "");

        personInfo = report.getBzDetail().getReportPerson() == null?
                new CheckReport.PersonInfo(): report.getBzDetail().getReportPerson();
        row = table.getRow(2);
        cell = row.getCell(0);
        setCellLabel(cell, "15%", "报告审核人");
        cell = row.getCell(1);
        setCellValueCenter(cell, "20%", personInfo.getName());
        cell = row.getCell(2);
        setCellLabel(cell, "10%", "手机");
        cell = row.getCell(3);
        setCellValueCenter(cell, "25%", personInfo.getMobile());
        cell = row.getCell(4);
        setCellLabel(cell, "10%", "");
        cell = row.getCell(5);
        setCellValueCenter(cell, "20%", "");

        WpsUtils.mergeCellsV(table, 4, 0, 2);
    }

    private void renderFooter(XWPFDocument doc) {
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        paragraph.setSpacingBetween(3, LineSpacingRule.AUTO);
        XWPFRun run = paragraph.createRun();
        run.setText("(检查单位技术意见章)");
        run.setFontSize(14);
        run.setBold(false);

        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        run = paragraph.createRun();
        run.setText("年   月   日");
        run.setFontSize(10);
        run.setBold(false);
        run.addBreak(BreakType.PAGE);
    }
}
