package com.ts.server.safe.report.service.export.ms;

import com.ts.server.safe.company.domain.RiskChemical;
import com.ts.server.safe.report.domain.CheckReport;
import com.ts.server.safe.report.service.export.PageBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.util.Collections;
import java.util.List;

/**
 * 构建企业基本情况
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
 class CompanyBuilder implements PageBuilder {

    @Override
    public void build(XWPFDocument doc, CheckReport report) {
        renderTitle(doc);

        XWPFTable table = createTable(doc);
        renderInfo(table, report);
        renderDeclareCtg(table, report);
        renderDetail(table, report);
        renderRisk(table, report);
        renderSign(table, report);

        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.createRun().addBreak(BreakType.PAGE);
    }

    private void renderTitle(XWPFDocument doc){
        MsUtils.pageTitle(doc, "二、受检企业基本情况");
    }

    private XWPFTable createTable(XWPFDocument doc){
        XWPFTable table = MsUtils.createTable(doc, 20, 6);
        table.setWidthType(TableWidthType.PCT);
        fixColumnWidth(table);
        return table;
    }

    private void fixColumnWidth(XWPFTable table){
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        cell.setWidth("15%");
        cell = row.getCell(1);
        cell.setWidth("25%");
        cell = row.getCell(2);
        cell.setWidth("10%");
        cell = row.getCell(3);
        cell.setWidth("20%");
        cell = row.getCell(4);
        cell.setWidth("10%");
        cell = row.getCell(5);
        cell.setWidth("20%");
    }

    private void renderInfo(XWPFTable table, CheckReport report){
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "企业名称");
        cell = row.getCell(1);
        setCellValueCenter(cell, report.getCompBaseInfo().getCompName());
        MsUtils.mergeCellsH(row, 1, 6);
        row = table.getRow(1);
        cell = row.getCell(0);
        setCellLabel(cell, "注册地址");
        cell = row.getCell(1);
        setCellValueCenter(cell, report.getCompBaseInfo().getRegAddress());
        MsUtils.mergeCellsH(row, 1, 6);
        row = table.getRow(2);
        cell = row.getCell(0);
        setCellLabel(cell,"社会信用代码");
        cell = row.getCell(1);
        setCellValueCenter(cell, report.getCompBaseInfo().getCreditCode());
        MsUtils.mergeCellsH(row, 1, 6);

        renderCompanyPerson(table, report);

        row = table.getRow(6);
        cell = row.getCell(0);
        setCellLabel(cell, "企业简介");
        cell = row.getCell(1);
        setCellValueLeft(cell, report.getCompBaseInfo().getCompProfile());
        MsUtils.mergeCellsH(row, 1, 6);

    }

    private void setCellLabel(XWPFTableCell cell, String label){
        setCell(cell, label, ParagraphAlignment.CENTER, false);
    }

    private void setCellLabelBold(XWPFTableCell cell, String label){
        setCell(cell, label, ParagraphAlignment.CENTER, true);
    }

    private void setCellValueLeft(XWPFTableCell cell, String value){
        setCell(cell, value, ParagraphAlignment.LEFT, false);
    }

    private void setCellValueCenter(XWPFTableCell cell, String value){
        setCell(cell, value, ParagraphAlignment.CENTER, false);
    }

    private void setCell(XWPFTableCell cell, String value, ParagraphAlignment alignment, boolean bold){
        MsUtils.setCell(cell, value, alignment, bold);
    }

    private void renderCompanyPerson(XWPFTable table, CheckReport report) {
        renderPerson(table, 3, "法定代表人", report.getCompBaseInfo().getConPerson());
        renderPerson(table, 4, "安全管理人", report.getCompBaseInfo().getConPerson());
        renderPerson(table, 5, "联 系 人", report.getCompBaseInfo().getConPerson());
    }

    private void renderPerson(XWPFTable table, int rowIndex, String title, CheckReport.PersonInfo person){
        XWPFTableRow row = table.getRow(rowIndex);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, title);
        cell = row.getCell(1);
        setCellValueCenter(cell, person.getName());
        cell = row.getCell(2);
        setCellLabel(cell,  "电 话");
        cell = row.getCell(3);
        setCellValueCenter(cell, getPhone(person.getPhone()));
        cell = row.getCell(4);
        setCellLabel(cell,  "手 机");
        cell = row.getCell(5);
        setCellValueCenter(cell, getPhone(person.getMobile()));
    }

    private String getPhone(String phone){
        return StringUtils.isBlank(phone)? "/" : phone;
    }

    private void renderDeclareCtg(XWPFTable table, CheckReport report){
        XWPFTableRow row = table.getRow(7);
        XWPFTableCell cell = row.getCell(0);
        setCellLabelBold(cell, "安全生产社会化服务机构隐患排查申报平台类型");
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(8);
        cell = row.getCell(0);
        renderRow1CheckBox(cell, report);
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(9);
        cell = row.getCell(0);
        renderRow2CheckBox(cell, report);
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(10);
        cell = row.getCell(0);
        renderRow3CheckBox(cell, report);
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(11);
        cell = row.getCell(0);
        renderRow4CheckBox(cell, report);
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(12);
        cell = row.getCell(0);
        renderRow5CheckBox(cell, report);
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(13);
        MsUtils.mergeCellsH(row, 0, 6);
        cell = row.getCell(0);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFTable safeTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        safeTable.getCTTbl().addNewTblPr().addNewTblW();
        safeTable.setWidth("100%");
        row = safeTable.createRow();
        cell = row.addNewTableCell();
        setCellWidth(cell, "50%", new boolean[]{false, true, false, false});
        setCellLabel(cell, "是否涉及重大生产安全隐患");
        cell = row.addNewTableCell();
        setCellWidth(cell, "50%", new boolean[]{false, false, false, false});
        paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        renderCheckBox(paragraph, "是", false);
        renderCheckBox(paragraph, "否", true);
    }

    private void renderRow1CheckBox(XWPFTableCell cell, CheckReport report){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        renderCheckBox(paragraph, "涉及可燃爆粉尘作业场所", false);
        renderCheckBox(paragraph, "喷涂作业", false);
        renderCheckBox(paragraph, "有限作业场所", true);
    }

    private void renderCheckBox(XWPFParagraph paragraph, String label, boolean enable){
        MsUtils.checkBox(paragraph, label, enable);
    }

    private void renderRow2CheckBox(XWPFTableCell cell, CheckReport report){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
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
        renderCheckBox(paragraph, "危化学品 生产单位", false);
        renderCheckBox(paragraph, "经营单位", false);
        renderCheckBox(paragraph, "使用单位", false);
    }

    private void renderRow4CheckBox(XWPFTableCell cell, CheckReport report){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        renderCheckBox(paragraph, "烟花爆竹企业 生产单位", false);
        renderCheckBox(paragraph, "经营单位", false);
    }

    private void renderRow5CheckBox(XWPFTableCell cell, CheckReport report){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        renderCheckBox(paragraph, "矿山企业 地下矿", false);
        renderCheckBox(paragraph, "地上矿", false);
        renderCheckBox(paragraph, "小型露天采石场", false);
    }

    private void renderDetail(XWPFTable table, CheckReport report){
        XWPFTableRow row = table.getRow(14);
        XWPFTableCell cell = row.getCell(0);
        setCellLabelBold(cell,  "涉及重点关注的工艺、场所、物料等情况描述");
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(15);
        cell = row.getCell(0);
        cell.addParagraph();
        cell.addParagraph();
        MsUtils.mergeCellsH(row, 0, 6);
    }

    private void renderRisk(XWPFTable table, CheckReport report){
        XWPFTableRow row = table.getRow(16);
        XWPFTableCell cell = row.getCell(0);
        setCellLabelBold(cell,  "生产、存储、使用涉及《危险化学品目录(2015版)》查询情况");
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(17);
        MsUtils.mergeCellsH(row, 0, 6);

        cell = row.getCell(0);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFTable personTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        personTable.getCTTbl().addNewTblPr().addNewTblW();
        personTable.setWidth("100%");

        row = personTable.createRow();
        cell = row.addNewTableCell();
        setCellWidth(cell, "8%", new boolean[]{false, true, true, false});
        setCellLabel(cell, "序号");
        cell = row.addNewTableCell();
        setCellWidth(cell, "15%", new boolean[]{false, true, true, false});
        setCellLabel(cell, "品名");
        cell = row.addNewTableCell();
        setCellWidth(cell, "15%", new boolean[]{false, true, true, false});
        setCellLabel(cell, "别名");
        cell = row.addNewTableCell();
        setCellWidth(cell, "20%", new boolean[]{false, true, true, false});
        setCellLabel(cell, "CAS号");
        cell = row.addNewTableCell();
        setCellWidth(cell, "16%", new boolean[]{false, true, true, false});
        setCellLabel(cell, "最大存储量");
        cell = row.addNewTableCell();
        setCellWidth(cell, "26%", new boolean[]{false, false, true, false});
        setCellLabel(cell, "备注");

        List<RiskChemical> chemicals = report.getCompBaseInfo().getRiskChemicals();
        if(chemicals.isEmpty()){
            RiskChemical t = new RiskChemical();
            t.setName("");
            t.setAlias("");
            t.setCas("");
            t.setMaxStore("");
            t.setRemark("");
            chemicals = Collections.singletonList(t);
        }

        int rowCount = chemicals.size();
        for(int i = 0; i < rowCount; i++){
            boolean isLast = i == (rowCount -1);
            RiskChemical r = chemicals.get(i);
            row = personTable.createRow();
            cell = row.getCell(0);
            setCellValueCenter(cell,  String.valueOf(i + 1));
            setCellWidth(cell, "8%", new boolean[]{false, true, !isLast, false});
            cell = row.getCell(1);
            setCellLabel(cell, r.getName());
            setCellWidth(cell, "15%", new boolean[]{false, true, !isLast, false});
            cell = row.getCell(2);
            setCellLabel(cell, r.getAlias());
            setCellWidth(cell, "15%", new boolean[]{false, true, !isLast, false});
            cell = row.getCell(3);
            setCellLabel(cell, r.getCas());
            setCellWidth(cell, "20%", new boolean[]{false, true, !isLast, false});
            cell = row.getCell(4);
            setCellLabel(cell, r.getMaxStore());
            setCellWidth(cell, "16%", new boolean[]{false, true, !isLast, false});
            cell = row.getCell(5);
            setCellLabel(cell, r.getRemark());
            setCellWidth(cell, "26%", new boolean[]{false, false, !isLast, false});
        }
    }

    private void setCellWidth(XWPFTableCell cell, String width, boolean[] showBorders){
        MsUtils.setCellWidthBorder(cell, width, showBorders);
    }

    private void renderSign(XWPFTable table, CheckReport report){
        XWPFTableRow row = table.getRow(18);
        XWPFTableCell cell = row.getCell(0);
        setCellLabelBold(cell,  "签收意见");
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(19);
        cell = row.getCell(0);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFRun run = paragraph.createRun();
        run.setText("委托单位签收意见：");
        run.setFontFamily("宋体");
        cell.addParagraph();
        cell.addParagraph();

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT * 8);
        run = paragraph.createRun();
        run.setText("监管人员签名：");
        run.setFontFamily("宋体");
        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT * 10);
        run = paragraph.createRun();
        run.setText("（委托单位盖章）");
        run.setFontFamily("宋体");
        paragraph = cell.addParagraph();
        paragraph.setSpacingBetween(2, LineSpacingRule.AUTO);
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        run = paragraph.createRun();
        run.setText("年    月    日 ");
        run.setFontFamily("宋体");
        MsUtils.mergeCellsH(row, 0, 6);
    }
}
