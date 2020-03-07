package com.ts.server.safe.report.service.export.ms;

import com.ts.server.safe.company.domain.CompProduct;
import com.ts.server.safe.company.service.CompProductKey;
import com.ts.server.safe.report.domain.IniReport;
import com.ts.server.safe.report.service.export.PageBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.util.Arrays;
import java.util.Optional;

/**
 * 构建企业基本情况
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
 class CompanyBuilder implements PageBuilder {

    @Override
    public void build(XWPFDocument doc, IniReport report) {
        renderTitle(doc);

        XWPFTable table = createTable(doc);
        renderInfo(table, report);
        renderDeclareCtg(table, report.getCompBaseInfo());
        renderDetail(table, report.getCompBaseInfo());
        renderRisk(table, report.getCompBaseInfo());
        renderSign(table);

        MsUtils.addEmptyParagraph(doc, 2);
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

    private void renderInfo(XWPFTable table, IniReport report){
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

    private void setCellValueLeft(XWPFTableCell cell, String value){
        setCell(cell, value, ParagraphAlignment.LEFT, false);
    }

    private void setCellValueCenter(XWPFTableCell cell, String value){
        setCell(cell, value, ParagraphAlignment.CENTER, false);
    }

    private void setCell(XWPFTableCell cell, String value, ParagraphAlignment alignment, boolean bold){
        MsUtils.setCell(cell, value, alignment, bold);
    }

    private void renderCompanyPerson(XWPFTable table, IniReport report) {
        renderPerson(table, 3, "法定代表人", report.getCompBaseInfo().getConPerson());
        renderPerson(table, 4, "安全管理人", report.getCompBaseInfo().getConPerson());
        renderPerson(table, 5, "联 系 人", report.getCompBaseInfo().getConPerson());
    }

    private void renderPerson(XWPFTable table, int rowIndex, String title, IniReport.PersonInfo person){
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

    private void renderDeclareCtg(XWPFTable table, IniReport.CompBaseInfo info){
        XWPFTableRow row = table.getRow(7);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "安全生产社会化服务机构隐患排查申报平台类型");
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(8);
        cell = row.getCell(0);
        renderRow1CheckBox(cell, info);
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(9);
        cell = row.getCell(0);
        renderRow2CheckBox(cell, info);
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(10);
        cell = row.getCell(0);
        renderRow3CheckBox(cell, info);
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(11);
        cell = row.getCell(0);
        renderRow4CheckBox(cell, info);
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(12);
        cell = row.getCell(0);
        renderRow5CheckBox(cell, info);
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(13);
        MsUtils.mergeCellsH(row, 0, 6);
        cell = row.getCell(0);
        renderGreatTable(cell, info);
    }

    private void renderRow1CheckBox(XWPFTableCell cell, IniReport.CompBaseInfo info){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        renderCheckBox(paragraph, "涉及可燃爆粉尘作业场所", getCheckBoxValue(info, CompProductKey.KRBFCCS));
        renderCheckBox(paragraph, "喷涂作业", getCheckBoxValue(info, CompProductKey.PTCS));
        renderCheckBox(paragraph, "有限作业场所", getCheckBoxValue(info, CompProductKey.YXCS));
    }

    private void renderCheckBox(XWPFParagraph paragraph, String label, boolean enable){
        MsUtils.checkBox(paragraph, label, enable);
    }

    private boolean getCheckBoxValue(IniReport.CompBaseInfo info, CompProductKey compProductKey){
        return getCompProductOpt(info, compProductKey).map(e -> e.getProValue() == 1).orElse(false);
    }

    private Optional<CompProduct> getCompProductOpt(IniReport.CompBaseInfo info, CompProductKey compProductKey){
        return info.getProducts().stream()
                .filter(e -> StringUtils.equals(e.getProKey(), compProductKey.getKey()))
                .findFirst();
    }

    private void renderRow2CheckBox(XWPFTableCell cell, IniReport.CompBaseInfo info){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        renderCheckBox(paragraph, "涉氨制冷企业", getCheckBoxValue(info, CompProductKey.SAZNQY));
        renderCheckBox(paragraph, "船舶维修企业", getCheckBoxValue(info, CompProductKey.CPWXQY));
        renderCheckBox(paragraph, "冶金企业", getCheckBoxValue(info, CompProductKey.YJQY));
        renderCheckBox(paragraph, "(工艺是否许可 是", false);
        renderCheckBox(paragraph, "否", true);
        XWPFRun run = paragraph.createRun();
        run.setText(")");
    }

    private void renderRow3CheckBox(XWPFTableCell cell, IniReport.CompBaseInfo info){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        int value = getCompProductOpt(info, CompProductKey.WHXP).map(CompProduct::getProValue).orElse(-1);
        renderCheckBox(paragraph, "危化学品 生产单位", value == 0);
        renderCheckBox(paragraph, "经营单位", value == 1);
        renderCheckBox(paragraph, "使用单位", value == 2);
    }

    private void renderRow4CheckBox(XWPFTableCell cell, IniReport.CompBaseInfo info){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        int value = getCompProductOpt(info, CompProductKey.YHBZQY).map(CompProduct::getProValue).orElse(-1);
        renderCheckBox(paragraph, "烟花爆竹企业 生产单位", value == 0);
        renderCheckBox(paragraph, "经营单位", value == 1);
    }

    private void renderRow5CheckBox(XWPFTableCell cell, IniReport.CompBaseInfo info){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        int value = getCompProductOpt(info, CompProductKey.KSQY).map(CompProduct::getProValue).orElse(-1);
        renderCheckBox(paragraph, "矿山企业 地下矿", value == 0);
        renderCheckBox(paragraph, "地上矿", value == 1);
        renderCheckBox(paragraph, "小型露天采石场", value == 2);
    }

    private void renderGreatTable(XWPFTableCell cell, IniReport.CompBaseInfo info){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFTable safeTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        safeTable.getCTTbl().addNewTblPr().addNewTblW();
        safeTable.setWidth("100%");
        XWPFTableRow row = safeTable.createRow();
        cell = row.addNewTableCell();
        setCellWidth(cell, "50%", new boolean[]{false, true, false, false});
        setCellLabel(cell, "是否涉及重大生产安全隐患");
        cell = row.addNewTableCell();
        setCellWidth(cell, "50%", new boolean[]{false, false, false, false});
        paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        boolean enabled = getCheckBoxValue(info, CompProductKey.SJZDAQSCYH);
        renderCheckBox(paragraph, "是", enabled);
        renderCheckBox(paragraph, "否", !enabled);
    }

    private void renderDetail(XWPFTable table, IniReport.CompBaseInfo info){
        XWPFTableRow row = table.getRow(14);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell,  "涉及重点关注的工艺、场所、物料等情况描述");
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(15);
        cell = row.getCell(0);
        cell.addParagraph();
        cell.addParagraph();
        MsUtils.mergeCellsH(row, 0, 6);
    }

    private void renderRisk(XWPFTable table, IniReport.CompBaseInfo info){
        XWPFTableRow row = table.getRow(16);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell,  "生产、存储、使用涉及《危险化学品目录(2015版)》查询情况");
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(17);
        MsUtils.mergeCellsH(row, 0, 6);

        cell = row.getCell(0);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFTable subTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        subTable.getCTTbl().addNewTblPr().addNewTblW();
        subTable.setWidth("100%");

        MsUtils.renderTable(subTable, info.getRiskChemicals(), Arrays.asList(
                new MsUtils.ColSetting<>("8%", "序号", (r, i) -> String.valueOf(i)),
                new MsUtils.ColSetting<>("15%", "品名", (r, i) -> r.getName()),
                new MsUtils.ColSetting<>("15%", "别名", (r, i) -> r.getAlias()),
                new MsUtils.ColSetting<>("20%", "CAS号", (r, i) -> r.getCas()),
                new MsUtils.ColSetting<>("16%", "最大存储量", (r, i) -> r.getMaxStore()),
                new MsUtils.ColSetting<>("26%", "备注", (r, i) -> r.getRemark())
        ));
    }

    private void setCellWidth(XWPFTableCell cell, String width, boolean[] showBorders){
        MsUtils.setCellWidthBorder(cell, width, showBorders);
    }

    private void renderSign(XWPFTable table){
        XWPFTableRow row = table.getRow(18);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell,  "签收意见");
        MsUtils.mergeCellsH(row, 0, 6);

        row = table.getRow(19);
        cell = row.getCell(0);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        MsUtils.setItemRun(paragraph.createRun(), 12, false, "委托单位签收意见：");
        cell.addParagraph();
        cell.addParagraph();

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT * 8);
        MsUtils.setItemRun(paragraph.createRun(), 12, false, "监管人员签名：");

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT * 10);
        MsUtils.setItemRun(paragraph.createRun(), 12, false, "（委托单位盖章）");

        paragraph = cell.addParagraph();
        paragraph.setSpacingBetween(2, LineSpacingRule.AUTO);
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        MsUtils.setItemRun(paragraph.createRun(), 10, false, "年    月    日 ");
        MsUtils.mergeCellsH(row, 0, 6);
    }
}
