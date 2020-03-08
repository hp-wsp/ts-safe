package com.ts.server.safe.task.service.export.initial.ms;

import com.ts.server.safe.task.domain.InitReportContent;
import com.ts.server.safe.task.service.export.PageBuilder;
import org.apache.poi.xwpf.usermodel.*;

import java.util.List;

/**
 * 构建编制说明页
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class DetailBuilder implements PageBuilder {

    @Override
    public void build(XWPFDocument doc, InitReportContent report) {
        renderTitle(doc);

        XWPFTable table = createTable(doc, report);
        renderProductInfo(table, report);
        renderServiceRequest(table, report);
        renderCompInfo(table, report.getReportDetail());
        renderContactPerson(table, report.getReportDetail());
        int offsetRow = renderWorkPersons(table, report.getReportDetail());
        renderSignature(table, offsetRow, report);
        renderFooter(doc);

        MsUtils.addEmptyParagraph(doc, 2);
    }

    private void renderTitle(XWPFDocument doc){
        MsUtils.pageTitle(doc, "一、编制说明");
    }

    private XWPFTable createTable(XWPFDocument doc, InitReportContent report){
        int rowCount = mathRowCount(report);
        XWPFTable table = MsUtils.createTable(doc, rowCount, 6);
        fixColumnWidth(table);
        return table;
    }

    private int mathRowCount(InitReportContent report){
        return 17 + Math.max(report.getReportDetail().getWorkers().size(), 1);
    }

    private void fixColumnWidth(XWPFTable table){
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        cell.setWidth("15%");
        cell = row.getCell(1);
        cell.setWidth("20%");
        cell = row.getCell(2);
        cell.setWidth("12%");
        cell = row.getCell(3);
        cell.setWidth("23%");
        cell = row.getCell(4);
        cell.setWidth("10%");
        cell = row.getCell(5);
        cell.setWidth("20%");
    }

    private void renderProductInfo(XWPFTable table, InitReportContent report){
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "1.安全生产社会化信息");
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(1);
        cell = row.getCell(0);
        setCellLabel(cell, "委托单位");
        cell = row.getCell(1);
        setCellValueCenter(cell, report.getChannelName());
        MsUtils.mergeCellsH(row, 1, 4);
        cell = row.getCell(4);
        setCellLabel(cell, "(合同)编号");
        cell = row.getCell(5);
        setCellValueCenter(cell, report.getReportDetail().getConNum());
    }

    private void setCellLabel(XWPFTableCell cell, String label){
        setCell(cell, label, ParagraphAlignment.CENTER, false);
    }

    private void setCellValueLeft(XWPFTableCell cell, String value){
        setCell(cell, value, ParagraphAlignment.LEFT, false);
    }

    private void setCellValueCenter(XWPFTableCell cell, String value){
        setCell(cell, value, ParagraphAlignment.CENTER, false);
    }

    private void setCell(XWPFTableCell cell, String content, ParagraphAlignment alignment, boolean bold){
        MsUtils.setCell(cell, content, alignment, bold);
    }

    private void renderServiceRequest(XWPFTable table, InitReportContent report){
        XWPFTableRow row = table.getRow(2);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell,  "2.委托服务要求");
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(3);
        cell = row.getCell(0);
        setCellLabel(cell, "周期和内容");
        cell = row.getCell(1);
        String checkContent = report.getCheckTimeFrom() + " 至 " + report.getCheckTimeTo() +
                "代表委托方检查辖区企业（单位）与安全生产相关国家法律、法规、标准、政策等要求的符合性。";
        setCellValueLeft(cell, checkContent);
        MsUtils.mergeCellsH(row, 1, 6);

        row = table.getRow(4);
        cell = row.getCell(0);
        setCellLabel(cell, "服务主要依据");
        cell = row.getCell(1);
        setCellValueLeft(cell, report.getReportDetail().getServiceBase());
        MsUtils.mergeCellsH(row, 1, 6);

        row = table.getRow(5);
        cell = row.getCell(0);
        setCellLabel(cell, "服务方法");
        cell = row.getCell(1);
        setCellValueLeft(cell, report.getReportDetail().getServiceMethod());
        MsUtils.mergeCellsH(row, 1, 6);

        row = table.getRow(6);
        cell = row.getCell(0);
        setCellLabel(cell, "服务范围");
        cell = row.getCell(1);
        setCellValueLeft(cell, report.getReportDetail().getServiceRange());
        MsUtils.mergeCellsH(row, 1, 6);
    }


    private void renderCompInfo(XWPFTable table, InitReportContent.ReportDetail detail){
        XWPFTableRow row = table.getRow(7);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "3.受委托单位信息");
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(8);
        cell = row.getCell(0);
        setCellLabel(cell, "单位名称");
        cell = row.getCell(1);
        setCellValueLeft(cell, detail.getChanName());
        MsUtils.mergeCellsH(row, 1, 6);

        row = table.getRow(9);
        cell = row.getCell(0);
        setCellLabel(cell, "单位地址");
        cell = row.getCell(1);
        setCellValueLeft(cell, detail.getChanAddress());
        MsUtils.mergeCellsH(row, 1, 6);

        row = table.getRow(10);
        cell = row.getCell(0);
        setCellLabel(cell, "经营范围");
        cell = row.getCell(1);
        setCellValueLeft(cell, detail.getChanBusScope());
        MsUtils.mergeCellsH(row, 1, 6);
    }

    private void renderContactPerson(XWPFTable table, InitReportContent.ReportDetail detail){
        XWPFTableRow row = table.getRow(11);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "法定代表人");
        cell = row.getCell(1);
        setCellValueCenter(cell, detail.getChanContact().getName());
        cell = row.getCell(2);
        setCellLabel(cell,  "电话");
        cell = row.getCell(3);
        setCellValueCenter(cell, detail.getChanContact().getPhone());
        cell = row.getCell(4);
        setCellLabel(cell, "手机");
        cell = row.getCell(5);
        setCellValueCenter(cell, detail.getChanContact().getMobile());
    }

    private int renderWorkPersons(XWPFTable table, InitReportContent.ReportDetail detail){
        XWPFTableRow row = table.getRow(12);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "分工");
        cell = row.getCell(1);
        setCellLabel(cell, "姓名");
        cell = row.getCell(2);
        setCellLabel(cell, "职务/职称");
        MsUtils.mergeCellsH(row, 2, 4);
        cell = row.getCell(4);
        setCellLabel(cell, "专业");
        cell = row.getCell(5);
        setCellLabel(cell, "联系电话");

        renderWorkPerson(table, 13, "组长", detail.getLeader());

        if(detail.getWorkers().isEmpty()){
            InitReportContent.PersonInfo emptyPerson = new InitReportContent.PersonInfo();
            emptyPerson.setName("");
            emptyPerson.setPhone("");
            renderWorkPerson(table, 14, "成员", emptyPerson);
            return 14;
        }
        List<InitReportContent.PersonInfo> workers = detail.getWorkers();
        for(int i = 0; i < workers.size(); i++){
            int rowIndex = 14 + i;
            renderWorkPerson(table, rowIndex, "成员", workers.get(i));
        }
        return 13 + workers.size();
    }

    private void renderWorkPerson(XWPFTable table, int rowIndex, String label, InitReportContent.PersonInfo person){
        XWPFTableRow row = table.getRow(rowIndex);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, label);
        cell = row.getCell(1);
        setCellValueCenter(cell, person.getName());
        cell = row.getCell(2);
        setCellValueCenter(cell, "");
        MsUtils.mergeCellsH(row, 2, 4);
        cell = row.getCell(4);
        setCellValueCenter(cell, "");
        cell = row.getCell(5);
        setCellValueCenter(cell, person.getPhone());
    }

    private void renderSignature(XWPFTable table, int offsetRow, InitReportContent report){
        renderSignaturePerson(table, offsetRow + 1, "主要负责人", report.getReportDetail().getPrincipalPerson());
        renderSignaturePerson(table, offsetRow + 2, "报告审核人", report.getReportDetail().getAuditPerson());
        renderSignaturePerson(table, offsetRow + 3, "报告审核人", report.getReportDetail().getReportPerson());
        MsUtils.mergeCellsV(table, 4, offsetRow + 1, offsetRow + 3);
    }

    private void renderSignaturePerson(XWPFTable table, int rowIndex, String label, InitReportContent.PersonInfo person){
        person = person == null? new InitReportContent.PersonInfo(): person;
        XWPFTableRow row = table.getRow(rowIndex);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, label);
        cell = row.getCell(1);
        setCellValueCenter(cell, person.getName());
        cell = row.getCell(2);
        setCellLabel(cell, "手机");
        cell = row.getCell(3);
        setCellValueCenter(cell, person.getMobile());
        cell = row.getCell(4);
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        setCellLabel(cell, "签名");
        cell = row.getCell(5);
        setCellValueCenter(cell, "");
    }

    private void renderFooter(XWPFDocument doc) {
        MsUtils.addEmptyParagraph(doc, 1);

        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT * 7);
        paragraph.setSpacingBetween(2, LineSpacingRule.AUTO);
        MsUtils.setItemRun(paragraph.createRun(), 12, false, "(检查单位技术意见章)");

        paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT * 8);
        MsUtils.setItemRun(paragraph.createRun(), 10, false, "年   月   日");
    }
}
