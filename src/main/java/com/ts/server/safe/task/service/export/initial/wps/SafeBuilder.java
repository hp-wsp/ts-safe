package com.ts.server.safe.task.service.export.initial.wps;

import com.ts.server.safe.task.domain.InitReportContent;
import com.ts.server.safe.task.service.export.PageBuilder;
import com.ts.server.safe.task.service.export.WpsUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.util.Arrays;
import java.util.List;

/**
 * 本周期安全生产社会化服务情况综述
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */

 class SafeBuilder implements PageBuilder<InitReportContent> {
    private static final String PROFILE_PATTER = "受%s安全生产隐患排查专项治理的服务委托，%s组成项目组于%s对测试企业进行安全生产社会化隐患排查技术服务";

    @Override
    public void build(XWPFDocument doc, InitReportContent report) {
        renderTitle(doc);
        renderProfile(doc, report);
        renderBaseDetail(doc, report);
        renderSceneDetail(doc, report);
        renderDisease(doc, report.getSafeProduct());
        renderSpePerson(doc, report.getSafeProduct());
        renderResult(doc, report.getSafeProduct());
        renderSafeRisk(doc, report.getSafeProduct());
        renderRiskType(doc, report.getSafeProduct());
        renderFooter(doc, report);
    }

    private void renderTitle(XWPFDocument doc){
        WpsUtils.pageTitle(doc, "三、本周期安全生产社会化服务情况综述");
    }

    private void renderProfile(XWPFDocument doc, InitReportContent report){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        cell.setWidth("100%");
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        String content =  String.format(PROFILE_PATTER, report.getCompName(), report.getChannelName(), report.getCheckTimeFrom());
        WpsUtils.setInd2Paragraph(paragraph, 12, false, content);

        paragraph = cell.addParagraph();
        content = "受限于时间、能力、专业水平等条件限制，本意见出现的误差或缺陷，请监管部门和受服务企业指正并谅解。";
        WpsUtils.setInd2Paragraph(paragraph, 12, false, content);
    }

    private XWPFTable createTable(XWPFDocument doc, int row, int col){
        return WpsUtils.createTable(doc, row, col);
    }

    private void renderBaseDetail(XWPFDocument doc, InitReportContent report){
        renderDetail(doc, "1、基础管理隐患描述及治理措施", report.getSafeProduct().getBaseContents());
    }

    private void renderDetail(XWPFDocument doc, String title, List<InitReportContent.CheckItem> contents){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "100%", title);

        WpsUtils.renderTable(doc, contents, Arrays.asList(
                new WpsUtils.ColSetting<>("8%", "序号", (e, i) -> String.valueOf(i)),
                new WpsUtils.ColSetting<>("35%", "隐患描述", (e, i) -> e.getDanDescribe()),
                new WpsUtils.ColSetting<>("35%", "法规依据或整改措施", (e, i) -> e.getDanSuggest()),
                new WpsUtils.ColSetting<>("22%", "备注", (e, i) -> e.getRemark())));
    }

    private void setCellWidth(XWPFTableCell cell, String width, String content){
        WpsUtils.setCellWidth(cell, width, content, ParagraphAlignment.CENTER);
    }

    private void renderSceneDetail(XWPFDocument doc, InitReportContent report){
        renderDetail(doc, "2、现场管理隐患描述及治理措施", report.getSafeProduct().getSceneContents());
    }

    private void renderDisease(XWPFDocument doc, InitReportContent.SafeProduct safeProduct){
        XWPFTable table = WpsUtils.createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        cell.setWidth("100%");
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFRun run = paragraph.createRun();
        WpsUtils.setItemRun(run, 12, false, "3、作业场所职业病危害因素的识别");

        paragraph = cell.addParagraph();
        WpsUtils.setInd2Paragraph(paragraph, 12, false,
                "依据《职业病危害因素分类目录》辨识，该企业存在的主要职业病危害因素有; （具体分布岗位及目录名称见下表）。");

        WpsUtils.renderTable(doc, safeProduct.getOccDiseaseJobs(), Arrays.asList(
                new WpsUtils.ColSetting<>("8%", "序号", (e, i) -> String.valueOf(1)),
                new WpsUtils.ColSetting<>("15%", "作业岗位", (e, i) -> e.getJob()),
                new WpsUtils.ColSetting<>("18%", "作业方式", (e, i) -> e.getJobWay()),
                new WpsUtils.ColSetting<>("18%", "作业形式", (e, i) -> e.getJobFormal()),
                new WpsUtils.ColSetting<>("26%", "存在职业病危害因素", (e, i) -> StringUtils.join(e.getRisks(), ",")),
                new WpsUtils.ColSetting<>("15%", "备注", (e, i) -> e.getRemark())));

        table = WpsUtils.createTable(doc, 1, 2);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellWidth(cell, "41%", "职业病危害风险分类辨识");
        cell = row.getCell(1);
        cell.setWidth("59%");
        XWPFParagraph p = cell.getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.CENTER);
        int level = safeProduct.getSafeRiskLevel();
        WpsUtils.checkBox(p, "一般", level == 0);
        WpsUtils.checkBox(p, "较重", level == 1);
        WpsUtils.checkBox(p, "严重", level == 2);
        WpsUtils.checkBox(p, "局部严重", level == 3);
    }

    private void renderSpePerson(XWPFDocument doc, InitReportContent.SafeProduct safeProduct){
        XWPFTable table = createTable(doc, 1, 2);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "41%", "4、涉及特种作业人员及证书");
        cell = row.getCell(1);
        cell.setWidth("59%");
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        WpsUtils.checkBox(paragraph, "涉及", safeProduct.isSpePerson());
        WpsUtils.checkBox(paragraph, "不涉及", !safeProduct.isSpePerson());

        WpsUtils.renderTable(doc, safeProduct.getSpePersons(), Arrays.asList(
                new WpsUtils.ColSetting<>("21%", "姓名", (e, i) -> e.getName()),
                new WpsUtils.ColSetting<>("20%", "类别", (e, i) -> e.getType()),
                new WpsUtils.ColSetting<>("30%", "证号", (e, i) -> e.getNum()),
                new WpsUtils.ColSetting<>("29%", "有效期", (e, i) -> e.getToDate())));

        table = createTable(doc, 1, 2);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellWidth(cell, "21%", "备注");
        cell = row.getCell(1);
        setCellWidth(cell, "79%", "");
    }

    private void renderResult(XWPFDocument doc, InitReportContent.SafeProduct safeProduct){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidthLeft(cell, "100%", "5.检查情况结论意见");
        WpsUtils.setInd2Paragraph(cell.addParagraph(), 12, false, safeProduct.getCheckResult());
    }

    private void setCellWidthLeft(XWPFTableCell cell, String width, String content){
        WpsUtils.setCellWidth(cell, width, content, ParagraphAlignment.LEFT);
    }

    private void renderSafeRisk(XWPFDocument doc, InitReportContent.SafeProduct safeProduct){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "100%", "6.检查判定企业综合安全风险状况");
        table = createTable(doc, 1, 2);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellWidth(cell, "38%", "检查判定企业综合安全风险状况");
        cell = row.getCell(1);
        cell.setWidth("62%");
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        int level = safeProduct.getSafeRiskLevel();
        WpsUtils.checkBox(paragraph, "高", level == 4);
        WpsUtils.checkBox(paragraph, "较高", level == 3);
        WpsUtils.checkBox(paragraph, "一般", level == 2);
        WpsUtils.checkBox(paragraph, "低", level == 1);

        table = createTable(doc, 1, 1);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellWidthLeft(cell, "100%", "风险判定说明：");

        paragraph = cell.addParagraph();
        WpsUtils.setInd2Paragraph(paragraph, 12, false, safeProduct.getRiskLevelExplain());
    }

    private void renderRiskType(XWPFDocument doc, InitReportContent.SafeProduct safeProduct){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidthLeft(cell, "100%", "7、生产安全事故类型风险辨识：");
        WpsUtils.setInd2Paragraph(cell.addParagraph(), 12, false, safeProduct.getSafeProductRisk());

        table = createTable(doc, 1, 1);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellWidthLeft(cell, "100%", "受检查企业意见：");
        WpsUtils.setInd2Paragraph(cell.addParagraph(), 12, false, safeProduct.getCheckCompanyIdea());

        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(WpsUtils.CM_UNIT * 10);
        WpsUtils.setItemRun(paragraph.createRun(), 12, false, "（单位盖章）");

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        WpsUtils.setItemRun(paragraph.createRun(), 12, false, "    年  月  日");
    }

    private void renderFooter(XWPFDocument doc, InitReportContent report){
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setSpacingBetween(2, LineSpacingRule.AUTO);
        WpsUtils.setItemRun(paragraph.createRun(), 12, false, report.getPrintDetail());
    }
}
