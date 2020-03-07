package com.ts.server.safe.report.service.export.ms;

import com.ts.server.safe.report.domain.IniReport;
import com.ts.server.safe.report.service.export.PageBuilder;
import com.ts.server.safe.task.domain.TaskContent;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.util.Arrays;
import java.util.List;

/**
 * 本周期安全生产社会化服务情况综述
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */

 class SafeBuilder implements PageBuilder {
    private static final String PROFILE_PATTER = "受%s安全生产隐患排查专项治理的服务委托，%s组成项目组于%s对测试企业进行安全生产社会化隐患排查技术服务";

    @Override
    public void build(XWPFDocument doc, IniReport report) {
        renderTitle(doc);

        XWPFTable table =createTable(doc);
        renderProfile(table, report);
//        renderBaseDetail(table, report.getSafeProduct());
//        renderSceneDetail(table, report.getSafeProduct());
        renderDisease(table, report.getSafeProduct());
        renderSpePerson(table, report.getSafeProduct());
        renderResult(table, report.getSafeProduct());
        renderSafeRisk(table, report.getSafeProduct());
        renderRiskType(table, report.getSafeProduct());

        renderFooter(doc, report);
    }

    private void renderTitle(XWPFDocument doc){
        MsUtils.pageTitle(doc, "三、本周期安全生产社会化服务情况综述");
    }

    private XWPFTable createTable(XWPFDocument doc){
        XWPFTable table = MsUtils.createTable(doc, 15, 1);
        table.setWidthType(TableWidthType.PCT);
        fixColumnWidth(table);
        return table;
    }

    private void fixColumnWidth(XWPFTable table){
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        cell.setWidth("100%");
    }

    private void renderProfile(XWPFTable table, IniReport report){
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        String content =  String.format(PROFILE_PATTER, report.getCompBaseInfo().getCompName(),
                report.getChannelName(), report.getCheckTimeFrom());
        addProfileParagraph(cell, 0, content);

        addProfileParagraph(cell,  1,"受限于时间、能力、专业水平等条件限制，本意见出现的误差或缺陷，请监管部门和受服务企业指正并谅解。");
    }

    private void addProfileParagraph(XWPFTableCell cell, int index, String content){
        XWPFParagraph paragraph;
        if(index == 0){
            paragraph = cell.getParagraphArray(0);
        }else{
            paragraph = cell.addParagraph();
        }
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT/2);
        MsUtils.setItemRun(paragraph.createRun(), 12, false, content);
    }

//    private void renderBaseDetail(XWPFTable table, IniReport.SafeProduct product){
//        renderDetail(table, 1,"1、基础管理隐患描述及治理措施", product.getBaseContents());
//    }

    private void renderDetail(XWPFTable table, int rowIndex, String title, List<TaskContent> contents){
        XWPFTableRow row = table.getRow(rowIndex);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, title);

        row = table.getRow(rowIndex + 1);
        cell = row.getCell(0);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFTable contentTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        contentTable.getCTTbl().addNewTblPr().addNewTblW();
        contentTable.setWidth("100%");
        MsUtils.renderTable(contentTable, contents, Arrays.asList(
                new MsUtils.ColSetting<>("8%", "序号", (r, i) -> String.valueOf(i)),
                new MsUtils.ColSetting<>("35%", "隐患描述", (r, i) -> r.getDanDescribe()),
                new MsUtils.ColSetting<>("35%", "法规依据或整改措施", (r, i) -> r.getDanSuggest()),
                new MsUtils.ColSetting<>("22%", "备注", (r, i) -> r.getRemark())
        ));
    }

    private void setCellWidthBorder(XWPFTableCell cell, String width, boolean[] showBorders){
        MsUtils.setCellWidthBorder(cell, width, showBorders);
    }

    private void setCellLabel(XWPFTableCell cell, String label){
        setCell(cell, label, ParagraphAlignment.CENTER, false);
    }

    private void setCellValueLeft(XWPFTableCell cell, String value){
        setCell(cell, value, ParagraphAlignment.LEFT, false);
    }

    private void setCell(XWPFTableCell cell, String value, ParagraphAlignment alignment, boolean bold){
        MsUtils.setCell(cell, value, alignment, bold);
    }

//    private void renderSceneDetail(XWPFTable table, IniReport.SafeProduct product){
//        renderDetail(table, 3, "2、现场管理隐患描述及治理措施", product.getSceneContents());
//    }

    private void renderDisease(XWPFTable table, IniReport.SafeProduct product){
        XWPFTableRow row = table.getRow(5);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "3、作业场所职业病危害因素的识别");

        MsUtils.setInd2Paragraph(cell.addParagraph(), 12, false,
                "依据《职业病危害因素分类目录》辨识，该企业存在的主要职业病危害因素有; （具体分布岗位及目录名称见下表）。");

        row = table.getRow(6);
        cell = row.getCell(0);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFTable subTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        subTable.getCTTbl().addNewTblPr().addNewTblW();
        subTable.setWidth("100%");
        MsUtils.renderTable(subTable, product.getOccDiseaseJobs(), Arrays.asList(
                new MsUtils.ColSetting<>("8%", "序号", (r, i) -> String.valueOf(i)),
                new MsUtils.ColSetting<>("15%", "作业岗位", (r, i) -> r.getJob()),
                new MsUtils.ColSetting<>("18%", "作业方式", (r, i) -> r.getJobWay()),
                new MsUtils.ColSetting<>("18%", "作业形式", (r, i) -> r.getJobFormal()),
                new MsUtils.ColSetting<>("26%", "存在职业病危害因素", (r, i) -> StringUtils.join(r.getRisks(), ",")),
                new MsUtils.ColSetting<>("15%", "备注", (r, i) -> r.getRemark())
        ));

        row = table.getRow(7);
        cell = row.getCell(0);
        paragraph = cell.getParagraphArray(0);
        subTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        subTable.getCTTbl().addNewTblPr().addNewTblW();
        subTable.setWidth("100%");
        row = subTable.createRow();
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "41%", new boolean[]{false, true, false, false});
        setCellLabel(cell, "职业病危害风险分类辨识");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "59%", new boolean[]{false, false, false, false});
        XWPFParagraph p = cell.getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.CENTER);
        MsUtils.checkBox(p, "一般", false);
        MsUtils.checkBox(p, "较重", false);
        MsUtils.checkBox(p, "严重", false);
        MsUtils.checkBox(p, "局部严重", false);
    }

    private void renderSpePerson(XWPFTable table, IniReport.SafeProduct product){
        XWPFTableRow row = table.getRow(8);
        XWPFTableCell cell = row.getCell(0);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFTable subTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        subTable.getCTTbl().addNewTblPr().addNewTblW();
        subTable.setWidth("100%");
        row = subTable.createRow();
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "41%", new boolean[]{false, true, false, false});
        setCellLabel(cell,  "4、涉及特种作业人员及证书");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "59%", new boolean[]{false, false, false, false});
        paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        MsUtils.checkBox(paragraph, "涉及", product.isSpePerson());
        MsUtils.checkBox(paragraph, "不涉及", !product.isSpePerson());

        row = table.getRow(9);
        cell = row.getCell(0);
        paragraph = cell.getParagraphArray(0);
        subTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        subTable.getCTTbl().addNewTblPr().addNewTblW();
        subTable.setWidth("100%");
        MsUtils.renderTable(subTable, product.getSpePersons(), Arrays.asList(
                new MsUtils.ColSetting<>("21%", "姓名", (r, i) -> r.getName()),
                new MsUtils.ColSetting<>("20%", "类别", (r, i) -> r.getType()),
                new MsUtils.ColSetting<>("30%", "证号", (r, i) -> r.getNum()),
                new MsUtils.ColSetting<>("29%", "有效期", (r, i) -> r.getToDate())
        ));

        row = table.getRow(10);
        cell = row.getCell(0);
        paragraph = cell.getParagraphArray(0);
        subTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        subTable.getCTTbl().addNewTblPr().addNewTblW();
        subTable.setWidth("100%");
        row = subTable.createRow();
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "21%", new boolean[]{false, true, false, false});
        setCellLabel(cell, "备注");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "79%", new boolean[]{false, false, false, false});
        setCellValueLeft(cell, "");
    }

    private void renderResult(XWPFTable table, IniReport.SafeProduct product){
        XWPFTableRow row = table.getRow(11);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell,"5.检查情况结论意见");
        MsUtils.setInd2Paragraph(cell.addParagraph(), 12, false, product.getCheckResult());
    }

    private void renderSafeRisk(XWPFTable table, IniReport.SafeProduct product){
        XWPFTableRow row = table.getRow(12);
        XWPFTableCell cell = row.getCell(0);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFTable subTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        subTable.getCTTbl().addNewTblPr().addNewTblW();
        subTable.setWidth("100%");

        row = subTable.createRow();
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "45%", new boolean[]{false, false, false, false});
        setCellLabel(cell, "6.检查判定企业综合安全风险状况");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "55%", new boolean[]{false, false, false, false});
        MsUtils.mergeCellsH(row, 0, 2);
        row = subTable.createRow();
        cell = row.getCell(0);
        setCellWidthBorder(cell, "45%", new boolean[]{false, false, false, false});
        setCellLabel(cell, "检查判定企业综合安全风险状况");
        cell = row.getCell(1);
        setCellWidthBorder(cell, "55%", new boolean[]{false, false, false, false});
        paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        int level = product.getSafeRiskLevel();
        MsUtils.checkBox(paragraph, "高", level == 4);
        MsUtils.checkBox(paragraph, "较高", level == 3);
        MsUtils.checkBox(paragraph, "一般", level == 2);
        MsUtils.checkBox(paragraph, "低", level == 1);

        row = table.getRow(12);
        cell = row.getCell(0);
        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        MsUtils.setItemRun(paragraph.createRun(), 12, false, "风险判定说明：");

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT/2);
        MsUtils.setItemRun(paragraph.createRun(), 12, false, product.getRiskLevelExplain());
    }

    private void renderRiskType(XWPFTable table, IniReport.SafeProduct product){
        XWPFTableRow row = table.getRow(13);
        XWPFTableCell cell = row.getCell(0);
        setCellLabel(cell, "7、生产安全事故类型风险辨识");

        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT/2);
        MsUtils.setItemRun(paragraph.createRun(), 12, false, product.getSafeProductRisk());

        row = table.getRow(14);
        cell = row.getCell(0);
        paragraph = cell.getParagraphArray(0);
        MsUtils.setItemRun(paragraph.createRun(), 12, false, "受检查企业意见：");

        paragraph = cell.addParagraph();
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT/2);
        MsUtils.setItemRun(paragraph.createRun(), 12, false, product.getCheckCompanyIdea());

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT * 10);
        MsUtils.setItemRun(paragraph.createRun(), 12, false, "（单位盖章）");

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        MsUtils.setItemRun(paragraph.createRun(), 10, false, "    年  月  日");
    }

    private void renderFooter(XWPFDocument doc, IniReport report){
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setSpacingBetween(1.2, LineSpacingRule.AUTO);
        MsUtils.setItemRun(paragraph.createRun(), 12, false, report.getPrintDetail());
    }
}
