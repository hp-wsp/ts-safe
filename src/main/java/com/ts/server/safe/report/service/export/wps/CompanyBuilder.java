package com.ts.server.safe.report.service.export.wps;

import com.ts.server.safe.company.domain.RiskChemical;
import com.ts.server.safe.report.domain.CheckReport;
import com.ts.server.safe.report.service.export.PageBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;

/**
 * 构建企业基本情况
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
 class CompanyBuilder implements PageBuilder {

    @Override
    public void build(XWPFDocument doc, CheckReport report) {
        renderTitle(doc);
        renderInfo(doc, report);
        renderDeclareCtg(doc, report);
        renderGreatTable(doc, report);
        renderDetail(doc, report);
        renderRisk(doc, report);
        renderSign(doc, report);
    }

    private void renderTitle(XWPFDocument doc){
        WpsUtils.pageTitle(doc, "二、受检企业基本情况");
    }

    private void renderInfo(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 3, 2);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "15%", "企业名称");
        cell = row.getCell(1);
        setCellValueCenter(cell, "85%", report.getCompBaseInfo().getCompName());
        row = table.getRow(1);
        cell = row.getCell(0);
        setCellLabel(cell, "15%", "注册地址");
        cell = row.getCell(1);
        setCellValueCenter(cell, "85%", report.getCompBaseInfo().getRegAddress());
        row = table.getRow(2);
        cell = row.getCell(0);
        setCellLabel(cell, "15%", "社会信用代码");
        cell = row.getCell(1);
        setCellValueCenter(cell, "85%", report.getCompBaseInfo().getCreditCode());
        renderCompanyPerson(doc, report);
        table = createTable(doc, 1, 2);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellLabel(cell, "15%", "企业简介");
        cell = row.getCell(1);
        setCellValueLeft(cell, "85%", report.getCompBaseInfo().getCompProfile());

    }

    private void renderCompanyPerson(XWPFDocument doc, CheckReport report) {
        XWPFTable table = createTable(doc, 3, 6);
        renderPerson(table, 0, "法定代表人", report.getCompBaseInfo().getConPerson());
        renderPerson(table, 1, "安全管理人", report.getCompBaseInfo().getConPerson());
        renderPerson(table, 2, "联 系 人", report.getCompBaseInfo().getConPerson());
    }

    private void renderPerson(XWPFTable table, int rowIndex, String title, CheckReport.PersonInfo person){
        XWPFTableRow row = table.getRow(rowIndex);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "15%", title);
        cell = row.getCell(1);
        setCellValueCenter(cell, "15%", person.getName());
        cell = row.getCell(2);
        setCellLabel(cell, "10%", "电 话");
        cell = row.getCell(3);
        setCellValueCenter(cell, "25%", getPhone(person.getPhone()));
        cell = row.getCell(4);
        setCellLabel(cell, "10%", "手 机");
        cell = row.getCell(5);
        setCellValueCenter(cell, "25%", getPhone(person.getMobile()));
    }

    private String getPhone(String phone){
        return StringUtils.isBlank(phone)? "/" : phone;
    }

    private XWPFTable createTable(XWPFDocument doc, int row, int col){
        XWPFTable table = doc.createTable(row, col);
        table.setTableAlignment(TableRowAlign.CENTER);
        table.setWidth("98%");
        return table;
    }

    private void setCellLabel(XWPFTableCell cell, String width, String label){
        setCell(cell, width, label, ParagraphAlignment.CENTER, false);
    }

    private void setCellLabelBold(XWPFTableCell cell, String width, String label){
        setCell(cell, width, label, ParagraphAlignment.CENTER, true);
    }

    private void setCellValueLeft(XWPFTableCell cell, String width, String value){
        setCell(cell, width, value, ParagraphAlignment.LEFT, false);
    }

    private void setCellValueCenter(XWPFTableCell cell, String width, String value){
        setCell(cell, width, value, ParagraphAlignment.CENTER, false);
    }

    private void setCell(XWPFTableCell cell, String width, String value, ParagraphAlignment alignment, boolean bold){
        cell.setWidth(width);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(alignment);
        XWPFRun run = paragraph.createRun();
        run.setText(value);
        run.setFontSize(9);
        run.setBold(bold);
    }

    private void renderDeclareCtg(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 6, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabelBold(cell, "100%","安全生产社会化服务机构隐患排查申报平台类型");
        row = table.getRow(1);
        cell = row.getCell(0);
        renderRow1CheckBox(cell, report);
        row = table.getRow(2);
        cell = row.getCell(0);
        renderRow2CheckBox(cell, report);
        row = table.getRow(3);
        cell = row.getCell(0);
        renderRow3CheckBox(cell, report);
        row = table.getRow(4);
        cell = row.getCell(0);
        renderRow4CheckBox(cell, report);
        row = table.getRow(5);
        cell = row.getCell(0);
        renderRow5CheckBox(cell, report);
    }

    private void renderRow1CheckBox(XWPFTableCell cell, CheckReport report){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        cell.setWidth("100%");
        renderCheckBox(paragraph, "涉及可燃爆粉尘作业场所", false);
        renderCheckBox(paragraph, "喷涂作业", false);
        renderCheckBox(paragraph, "有限作业场所", true);
    }

    private void renderCheckBox(XWPFParagraph paragraph, String label, boolean enable){
        WpsUtils.checkBox(paragraph, label, enable);
    }

    private void renderRow2CheckBox(XWPFTableCell cell, CheckReport report){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        cell.setWidth("100%");
        renderCheckBox(paragraph, "涉氨制冷企业", false);
        renderCheckBox(paragraph, "船舶维修企业", false);
        renderCheckBox(paragraph, "冶金企业", false);
        renderCheckBox(paragraph, "(工艺是否许可 是", false);
        renderCheckBox(paragraph, "否", true);
        XWPFRun run = paragraph.createRun();
        run.setText(")");
    }

    private void renderRow3CheckBox(XWPFTableCell cell, CheckReport report){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        cell.setWidth("100%");
        renderCheckBox(paragraph, "危化学品 生产单位", false);
        renderCheckBox(paragraph, "经营单位", false);
        renderCheckBox(paragraph, "使用单位", false);
    }

    private void renderRow4CheckBox(XWPFTableCell cell, CheckReport report){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        cell.setWidth("100%");
        renderCheckBox(paragraph, "烟花爆竹企业 生产单位", false);
        renderCheckBox(paragraph, "经营单位", false);
    }

    private void renderRow5CheckBox(XWPFTableCell cell, CheckReport report){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        cell.setWidth("100%");
        renderCheckBox(paragraph, "矿山企业 地下矿", false);
        renderCheckBox(paragraph, "地上矿", false);
        renderCheckBox(paragraph, "小型露天采石场", false);
    }

    private void renderGreatTable(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 1, 2);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "50%", "是否涉及重大生产安全隐患");
        cell = row.getCell(1);
        cell.setWidth("50%");
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        renderCheckBox(paragraph, "是", false);
        renderCheckBox(paragraph, "否", true);
    }

    private void renderDetail(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 2, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabelBold(cell, "100%", "涉及重点关注的工艺、场所、物料等情况描述");
        row = table.getRow(1);
        cell = row.getCell(0);
        setCellValueLeft(cell, "100%", "");
    }

    private void renderRisk(XWPFDocument doc, CheckReport report){
        CheckReport.CompBaseInfo info = report.getCompBaseInfo();
        if(info.getRiskChemicals().isEmpty()){
           return ;
        }
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabelBold(cell, "100%", "生产、存储、使用涉及《危险化学品目录(2015版)》查询情况");

        int rowCount = info.getRiskChemicals().size() + 1;
        table = createTable(doc, rowCount, 6);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellLabel(cell, "8%", "序号");
        cell = row.getCell(1);
        setCellLabel(cell, "15%", "品名");
        cell = row.getCell(2);
        setCellLabel(cell, "15%", "别名");
        cell = row.getCell(3);
        setCellLabel(cell, "20%", "CAS号");
        cell = row.getCell(4);
        setCellLabel(cell, "16%", "最大存储量");
        cell = row.getCell(5);
        setCellLabel(cell, "26%", "备注");
        for(int i = 1; i < rowCount; i++){
            RiskChemical r = report.getCompBaseInfo().getRiskChemicals().get(i -1);
            row = table.getRow(i);
            cell = row.getCell(0);
            setCellValueCenter(cell, "8%", String.valueOf(i));
            cell = row.getCell(1);
            setCellLabel(cell, "15%", r.getName());
            cell = row.getCell(2);
            setCellLabel(cell, "15%", r.getAlias());
            cell = row.getCell(3);
            setCellLabel(cell, "20%", r.getCas());
            cell = row.getCell(4);
            setCellLabel(cell, "16%", r.getMaxStore());
            cell = row.getCell(5);
            setCellLabel(cell, "26%", r.getRemark());
        }
    }

    private void renderSign(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 2, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabelBold(cell, "100%", "签收意见");

        row = table.getRow(1);
        cell = row.getCell(0);
        cell.setWidth("100%");
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setSpacingBetween(2, LineSpacingRule.AUTO);
        XWPFRun run = paragraph.createRun();
        run.setText("委托单位签收意见：");
        paragraph = cell.addParagraph();
        run = paragraph.createRun();
        run.setText("\r\r");
        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(WpsUtils.CM_UNIT * 8);
        paragraph.setSpacingBetween(2, LineSpacingRule.AUTO);
        run = paragraph.createRun();
        run.setText("监管人员签名：");
        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(WpsUtils.CM_UNIT * 10);
        run = paragraph.createRun();
        run.setText("（委托单位盖章）");
        paragraph = cell.addParagraph();
        paragraph.setSpacingBetween(2, LineSpacingRule.AUTO);
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        run = paragraph.createRun();
        run.setText("年    月    日 ");

        paragraph = doc.createParagraph();
        run = paragraph.createRun();
        run.addBreak(BreakType.PAGE);
    }
}
