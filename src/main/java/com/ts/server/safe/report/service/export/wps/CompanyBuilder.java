package com.ts.server.safe.report.service.export.wps;

import com.ts.server.safe.company.domain.CompProduct;
import com.ts.server.safe.company.domain.RiskChemical;
import com.ts.server.safe.company.service.CompProductKey;
import com.ts.server.safe.report.domain.CheckReport;
import com.ts.server.safe.report.service.export.PageBuilder;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.util.List;
import java.util.Optional;

/**
 * 构建企业基本情况
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
 class CompanyBuilder implements PageBuilder {

    @Override
    public void build(XWPFDocument doc, CheckReport report) {

        renderTitle(doc);

        renderInfo(doc, report.getCompBaseInfo());
        renderDeclareCtg(doc, report.getCompBaseInfo());
        renderGreatTable(doc, report.getCompBaseInfo());
        renderDetail(doc, report.getCompBaseInfo());
        renderRisk(doc, report.getCompBaseInfo());
        renderSign(doc, report.getCompBaseInfo());

        XWPFParagraph paragraph = doc.createParagraph();
        XWPFRun run = paragraph.createRun();
        run.addBreak(BreakType.PAGE);
    }

    private void renderTitle(XWPFDocument doc){
        WpsUtils.pageTitle(doc, "二、受检企业基本情况");
    }

    private void renderInfo(XWPFDocument doc, CheckReport.CompBaseInfo info){
        XWPFTable table = createTable(doc, 3, 2);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "15%", "企业名称");
        cell = row.getCell(1);
        setCellWidth(cell, "85%", info.getCompName());
        row = table.getRow(1);
        cell = row.getCell(0);
        setCell(cell,"注册地址");
        cell = row.getCell(1);
        setCell(cell, info.getRegAddress());
        row = table.getRow(2);
        cell = row.getCell(0);
        setCell(cell,"社会信用代码");
        cell = row.getCell(1);
        setCell(cell, info.getCreditCode());
        
        renderCompanyPerson(doc, info);

        table = createTable(doc, 1, 2);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellWidth(cell, "15%", "企业简介");
        cell = row.getCell(1);
        setCellWidthLeft(cell, "85%", info.getCompProfile());
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

    private void setCell(XWPFTableCell cell, String content){
        WpsUtils.setCell(cell, content, ParagraphAlignment.CENTER);
    }

    private void setCellLeft(XWPFTableCell cell, String content){
        WpsUtils.setCell(cell, content, ParagraphAlignment.LEFT);
    }

    private void renderCompanyPerson(XWPFDocument doc, CheckReport.CompBaseInfo info) {
        XWPFTable table = createTable(doc, 3, 6);
        renderPerson(table, 0, "法定代表人", info.getConPerson());
        renderPerson(table, 1, "安全管理人", info.getConPerson());
        renderPerson(table, 2, "联 系 人", info.getConPerson());
    }

    private void renderPerson(XWPFTable table, int rowIndex, String title, CheckReport.PersonInfo person){
        XWPFTableRow row = table.getRow(rowIndex);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "15%", title);
        cell = row.getCell(1);
        setCellWidth(cell, "15%", person.getName());
        cell = row.getCell(2);
        setCellWidth(cell, "10%", "电 话");
        cell = row.getCell(3);
        String phone =  StringUtils.isBlank(person.getPhone())? "/" : person.getPhone();
        setCellWidth(cell, "25%", phone);
        cell = row.getCell(4);
        setCellWidth(cell, "10%", "手 机");
        cell = row.getCell(5);
        String mobile =  StringUtils.isBlank(person.getMobile())? "/" : person.getMobile();
        setCellWidth(cell, "25%", mobile);
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

    private void renderDeclareCtg(XWPFDocument doc, CheckReport.CompBaseInfo info){
        XWPFTable table = createTable(doc, 6, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "100%","安全生产社会化服务机构隐患排查申报平台类型");
        row = table.getRow(1);
        cell = row.getCell(0);
        renderRow1CheckBox(cell, info);
        row = table.getRow(2);
        cell = row.getCell(0);
        renderRow2CheckBox(cell, info);
        row = table.getRow(3);
        cell = row.getCell(0);
        renderRow3CheckBox(cell, info);
        row = table.getRow(4);
        cell = row.getCell(0);
        renderRow4CheckBox(cell, info);
        row = table.getRow(5);
        cell = row.getCell(0);
        renderRow5CheckBox(cell, info);
    }

    private void renderRow1CheckBox(XWPFTableCell cell, CheckReport.CompBaseInfo info){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        cell.setWidth("100%");
        renderCheckBox(paragraph, "涉及可燃爆粉尘作业场所", getCheckBoxValue(info, CompProductKey.KRBFCCS));
        renderCheckBox(paragraph, "喷涂作业", getCheckBoxValue(info, CompProductKey.PTCS));
        renderCheckBox(paragraph, "有限作业场所", getCheckBoxValue(info, CompProductKey.YXCS));
    }

    private boolean getCheckBoxValue(CheckReport.CompBaseInfo info, CompProductKey compProductKey){
        return getCompProductOpt(info, compProductKey).map(e -> e.getProValue() == 1).orElse(false);
    }

    private Optional<CompProduct> getCompProductOpt(CheckReport.CompBaseInfo info, CompProductKey compProductKey){
        return info.getProducts().stream()
                .filter(e -> StringUtils.equals(e.getProKey(), compProductKey.getKey()))
                .findFirst();
    }

    private void renderCheckBox(XWPFParagraph paragraph, String label, boolean enable){
        WpsUtils.checkBox(paragraph, label, enable);
    }

    private void renderRow2CheckBox(XWPFTableCell cell, CheckReport.CompBaseInfo info){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        cell.setWidth("100%");
        renderCheckBox(paragraph, "涉氨制冷企业", getCheckBoxValue(info, CompProductKey.SAZNQY));
        renderCheckBox(paragraph, "船舶维修企业", getCheckBoxValue(info, CompProductKey.CPWXQY));
        renderCheckBox(paragraph, "冶金企业", getCheckBoxValue(info, CompProductKey.YJQY));
        renderCheckBox(paragraph, "(工艺是否许可 是", false);
        renderCheckBox(paragraph, "否", true);
        XWPFRun run = paragraph.createRun();
        run.setText(")");
    }

    private void renderRow3CheckBox(XWPFTableCell cell, CheckReport.CompBaseInfo info){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        cell.setWidth("100%");
        int value = getCompProductOpt(info, CompProductKey.WHXP).map(CompProduct::getProValue).orElse(-1);
        renderCheckBox(paragraph, "危化学品 生产单位", value == 0);
        renderCheckBox(paragraph, "经营单位", value == 1);
        renderCheckBox(paragraph, "使用单位", value == 2);
    }

    private void renderRow4CheckBox(XWPFTableCell cell, CheckReport.CompBaseInfo info){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        cell.setWidth("100%");
        int value = getCompProductOpt(info, CompProductKey.YHBZQY).map(CompProduct::getProValue).orElse(-1);
        renderCheckBox(paragraph, "烟花爆竹企业 生产单位", value == 0);
        renderCheckBox(paragraph, "经营单位", value == 1);
    }

    private void renderRow5CheckBox(XWPFTableCell cell, CheckReport.CompBaseInfo info){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        cell.setWidth("100%");
        int value = getCompProductOpt(info, CompProductKey.KSQY).map(CompProduct::getProValue).orElse(-1);
        renderCheckBox(paragraph, "矿山企业 地下矿", value == 0);
        renderCheckBox(paragraph, "地上矿", value == 1);
        renderCheckBox(paragraph, "小型露天采石场", value == 2);
    }

    private void renderGreatTable(XWPFDocument doc, CheckReport.CompBaseInfo info){
        XWPFTable table = createTable(doc, 1, 2);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "50%", "是否涉及重大生产安全隐患");
        cell = row.getCell(1);
        cell.setWidth("50%");
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        boolean enabled = getCheckBoxValue(info, CompProductKey.SJZDAQSCYH);
        renderCheckBox(paragraph, "是", enabled);
        renderCheckBox(paragraph, "否", enabled);
    }

    private void renderDetail(XWPFDocument doc, CheckReport.CompBaseInfo info){
        XWPFTable table = createTable(doc, 2, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "100%", "涉及重点关注的工艺、场所、物料等情况描述");
        row = table.getRow(1);
        cell = row.getCell(0);
        setCellWidthLeft(cell, "100%", info.getProductDetail());
    }

    private void renderRisk(XWPFDocument doc, CheckReport.CompBaseInfo info){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "100%", "生产、存储、使用涉及《危险化学品目录(2015版)》查询情况");

        List<RiskChemical> riskChemicals = info.getRiskChemicals();
        int rowCount = riskChemicals.isEmpty()? 2: riskChemicals.size() + 1;
        table = createTable(doc, rowCount, 6);
        renderRiskHead(table);

        int rowIndex = 1;
        for(RiskChemical r: riskChemicals){
            row = table.getRow(rowIndex);
            cell = row.getCell(0);
            setCell(cell, String.valueOf(rowIndex));
            cell = row.getCell(1);
            setCell(cell, r.getName());
            cell = row.getCell(2);
            setCell(cell, r.getAlias());
            cell = row.getCell(3);
            setCell(cell, r.getCas());
            cell = row.getCell(4);
            setCell(cell, r.getMaxStore());
            cell = row.getCell(5);
            setCell(cell, r.getRemark());
            ++rowIndex;
        }
    }

    private void renderRiskHead(XWPFTable table){
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "8%", "序号");
        cell = row.getCell(1);
        setCellWidth(cell, "15%", "品名");
        cell = row.getCell(2);
        setCellWidth(cell, "15%", "别名");
        cell = row.getCell(3);
        setCellWidth(cell, "20%", "CAS号");
        cell = row.getCell(4);
        setCellWidth(cell, "16%", "最大存储量");
        cell = row.getCell(5);
        setCellWidth(cell, "26%", "备注");
    }

    private void renderSign(XWPFDocument doc, CheckReport.CompBaseInfo info){
        XWPFTable table = createTable(doc, 2, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "100%", "签收意见");

        row = table.getRow(1);
        cell = row.getCell(0);
        cell.setWidth("100%");
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setSpacingBetween(2, LineSpacingRule.AUTO);
        WpsUtils.setItemRun(paragraph.createRun(), 12, false, "委托单位签收意见：");

        XWPFRun run = cell.addParagraph().createRun();
        WpsUtils.setItemRun(run, 12, false, "");

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(WpsUtils.CM_UNIT * 8);
        paragraph.setSpacingBetween(2, LineSpacingRule.AUTO);
        run = paragraph.createRun();
        WpsUtils.setItemRun(run, 12, false, "监管人员签名：");

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(WpsUtils.CM_UNIT * 10);
        run = paragraph.createRun();
        WpsUtils.setItemRun(run, 12, false, "（委托单位盖章）");

        paragraph = cell.addParagraph();
        paragraph.setSpacingBetween(2, LineSpacingRule.AUTO);
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        run = paragraph.createRun();
        WpsUtils.setItemRun(run, 12, false, "年    月    日 ");
    }
}
