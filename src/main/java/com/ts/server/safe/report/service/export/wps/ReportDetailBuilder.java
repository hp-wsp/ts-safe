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
class ReportDetailBuilder implements PageBuilder {

    @Override
    public void build(XWPFDocument doc, CheckReport report) {
        renderTitle(doc);

        renderProductInfo(doc, report);
        renderServiceRequest(doc, report);
        renderCompInfo(doc, report.getReportDetail());
        renderContactPerson(doc, report.getReportDetail());
        renderWorkPerson(doc, report.getReportDetail());
        renderSignature(doc, report.getReportDetail());
        renderFooter(doc);

        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.createRun().addBreak(BreakType.PAGE);
    }

    private void renderTitle(XWPFDocument doc){
        WpsUtils.pageTitle(doc, "一、编制说明");
    }

    private void renderProductInfo(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "100%", "1.安全生产社会化信息");
        table = createTable(doc,1, 4);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellWidth(cell, "17%", "委托单位");
        cell = row.getCell(1);
        setCellWidthLeft(cell, "40%", report.getChannelName());
        cell = row.getCell(2);
        setCellWidth(cell, "17%", "(合同)编号");
        cell = row.getCell(3);
        setCellWidthLeft(cell, "26%", report.getReportDetail().getConNum());
    }

    private XWPFTable createTable(XWPFDocument doc, int row, int col){
        return WpsUtils.createTable(doc, row, col);
    }

    private void setCellWidth(XWPFTableCell cell, String width, String label){
        WpsUtils.setCellWidth(cell, width, label, ParagraphAlignment.CENTER);
    }

    private void setCellWidthLeft(XWPFTableCell cell, String width, String content){
        WpsUtils.setCellWidth(cell, width, content, ParagraphAlignment.LEFT);
    }

    private void renderServiceRequest(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "100%", "2.委托服务要求");

        table = createTable(doc,4, 2);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellWidth(cell, "25%", "周期和内容");
        cell = row.getCell(1);
        String checkContent = report.getCheckTimeFrom() + " 至 " + report.getCheckTimeTo() +
                "代表委托方检查辖区企业（单位）与安全生产相关国家法律、法规、标准、政策等要求的符合性。";
        setCellWidthLeft(cell, "75%", checkContent);
        row = table.getRow(1);
        cell = row.getCell(0);
        setCell(cell, "服务主要依据");
        cell = row.getCell(1);
        setCellLeft(cell, report.getReportDetail().getServiceBase());
        row = table.getRow(2);
        cell = row.getCell(0);
        setCell(cell, "服务方法");
        cell = row.getCell(1);
        setCellLeft(cell, report.getReportDetail().getServiceMethod());
        row = table.getRow(3);
        cell = row.getCell(0);
        setCell(cell,"服务范围");
        cell = row.getCell(1);
        setCellLeft(cell, report.getReportDetail().getServiceRange());
    }

    private void setCell(XWPFTableCell cell, String label){
        WpsUtils.setCell(cell, label, ParagraphAlignment.CENTER);
    }

    private void setCellLeft(XWPFTableCell cell, String content){
        WpsUtils.setCell(cell, content, ParagraphAlignment.LEFT);
    }

    private void renderCompInfo(XWPFDocument doc, CheckReport.ReportDetail detail){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "100%", "3.受委托单位信息");

        table = createTable(doc, 3, 2);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellWidth(cell, "25%", "单位名称");
        cell = row.getCell(1);
        setCellWidthLeft(cell, "75%", detail.getChanName());
        row = table.getRow(1);
        cell = row.getCell(0);
        setCell(cell,"单位地址");
        cell = row.getCell(1);
        setCellLeft(cell, detail.getChanAddress());
        row = table.getRow(2);
        cell = row.getCell(0);
        setCell(cell,"经营范围");
        cell = row.getCell(1);
        setCellLeft(cell, detail.getChanBusScope());
    }

    private void renderContactPerson(XWPFDocument doc, CheckReport.ReportDetail detail){
        XWPFTable table = createTable(doc, 1, 6);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "15%", "法定代表人");
        cell = row.getCell(1);
        setCellWidth(cell, "20%", detail.getChanContact().getName());
        cell = row.getCell(2);
        setCellWidth(cell, "10%", "电话");
        cell = row.getCell(3);
        setCellWidth(cell, "25%", detail.getChanContact().getPhone());
        cell = row.getCell(4);
        setCellWidth(cell, "10%", "手机");
        cell = row.getCell(5);
        setCellWidth(cell, "20%", detail.getChanContact().getMobile());
    }

    private void renderWorkPerson(XWPFDocument doc, CheckReport.ReportDetail detail){
        int rowCount = detail.getWorkers().size() + 2;
        XWPFTable table = createTable(doc, rowCount, 5);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "15%", "分工");
        cell = row.getCell(1);
        setCellWidth(cell, "20%", "姓名");
        cell = row.getCell(2);
        setCellWidth(cell, "35%", "职务/职称");
        cell = row.getCell(3);
        setCellWidth(cell, "10%", "专业");
        cell = row.getCell(4);
        setCellWidth(cell, "20%", "联系电话");
        renderWorkPersonRow(table, 1, "组长", detail.getLeader());
        int rowIndex = 2;
        for(CheckReport.PersonInfo person: detail.getWorkers()){
            renderWorkPersonRow(table, rowIndex++, "成员", person);
        }
    }

    private void renderWorkPersonRow(XWPFTable table, int rowIndex, String title, CheckReport.PersonInfo person){
        XWPFTableRow row = table.getRow(rowIndex);
        XWPFTableCell cell = row.getCell(0);
        setCell(cell,  title);
        cell = row.getCell(1);
        setCell(cell, person.getName());
        cell = row.getCell(2);
        setCell(cell, "");
        cell = row.getCell(3);
        setCell(cell, "");
        cell = row.getCell(4);
        setCell(cell, person.getPhone());
    }

    private void renderSignature(XWPFDocument doc, CheckReport.ReportDetail detail){
        XWPFTable table = createTable(doc, 3, 6);

        CheckReport.PersonInfo personInfo = Objects.isNull(detail.getPrincipalPerson())? new CheckReport.PersonInfo(): detail.getPrincipalPerson();
        renderSignatureRow(table, 0, "主要负责人", personInfo);

        personInfo = Objects.isNull(detail.getAuditPerson())? new CheckReport.PersonInfo(): detail.getAuditPerson();
        renderSignatureRow(table, 1, "报告审核人", personInfo);

        personInfo = Objects.isNull(detail.getReportPerson())? new CheckReport.PersonInfo(): detail.getReportPerson();
        renderSignatureRow(table, 2, "报告编制人", personInfo);

        WpsUtils.mergeCellsV(table, 4, 0, 2);
    }

    private void renderSignatureRow(XWPFTable table, int rowIndex, String title, CheckReport.PersonInfo person){
        XWPFTableRow row = table.getRow(rowIndex);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "15%", title);
        cell = row.getCell(1);
        setCellWidth(cell, "20%", person.getName());
        cell = row.getCell(2);
        setCellWidth(cell, "10%", "手机");
        cell = row.getCell(3);
        setCellWidth(cell, "25%", person.getMobile());
        cell = row.getCell(4);
        setCellWidth(cell, "10%", "签名");
        cell = row.getCell(5);
        setCellWidth(cell, "20%", "");
    }

    private void renderFooter(XWPFDocument doc) {
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        paragraph.setSpacingBetween(3, LineSpacingRule.AUTO);
        XWPFRun run = paragraph.createRun();
        WpsUtils.setItemRun(run, 14, false, "(检查单位技术意见章)");

        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        run = paragraph.createRun();
        WpsUtils.setItemRun(run, 12, false, "年   月   日");
    }
}
