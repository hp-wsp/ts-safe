package com.ts.server.safe.report.service.export.wps;

import com.ts.server.safe.company.domain.OccDiseaseJob;
import com.ts.server.safe.company.domain.SpePerson;
import com.ts.server.safe.report.domain.CheckReport;
import com.ts.server.safe.report.service.export.PageBuilder;
import com.ts.server.safe.task.domain.TaskContent;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.*;

import java.util.List;

/**
 * 本周期安全生产社会化服务情况综述
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */

 class SafeBuilder implements PageBuilder {
    private static final String PROFILE_PATTER = "受%s安全生产隐患排查专项治理的服务委托，%s组成项目组于%s对测试企业进行安全生产社会化隐患排查技术服务";

    @Override
    public void build(XWPFDocument doc, CheckReport report) {
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

    private void renderProfile(XWPFDocument doc, CheckReport report){
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

    private void renderBaseDetail(XWPFDocument doc, CheckReport report){
        renderDetail(doc, "1、基础管理隐患描述及治理措施", report.getSafeProduct().getBaseContents());
    }

    private void renderDetail(XWPFDocument doc, String title, List<TaskContent> contents){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidth(cell, "100%", title);
        int rowCount = contents.isEmpty()? 2: contents.size() + 1;
        table = createTable(doc, rowCount, 4);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellWidth(cell, "8%", "序号");
        cell = row.getCell(1);
        setCellWidth(cell, "35%", "隐患描述");
        cell = row.getCell(2);
        setCellWidth(cell, "35%", "法规依据或整改措施");
        cell = row.getCell(3);
        setCellWidth(cell, "22%", "备注");

        int rowIndex = 1;
        for(TaskContent t: contents){
            row = table.getRow(rowIndex);
            cell = row.getCell(0);
            setCell(cell, String.valueOf(rowIndex));
            cell = row.getCell(1);
            setCell(cell, t.getDanDescribe());
            cell = row.getCell(2);
            setCell(cell, t.getDanSuggest());
            cell = row.getCell(3);
            setCell(cell, t.getRemark());
            rowIndex++;
        }
    }

    private void setCellWidth(XWPFTableCell cell, String width, String content){
        WpsUtils.setCellWidth(cell, width, content, ParagraphAlignment.CENTER);
    }

    private void setCell(XWPFTableCell cell, String content){
        WpsUtils.setCell(cell, content, ParagraphAlignment.CENTER);
    }

    private void renderSceneDetail(XWPFDocument doc, CheckReport report){
        renderDetail(doc, "2、现场管理隐患描述及治理措施", report.getSafeProduct().getSceneContents());
    }

    private void renderDisease(XWPFDocument doc, CheckReport.SafeProduct safeProduct){
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

        int rowCount = safeProduct.getOccDiseaseJobs().isEmpty()? 2: safeProduct.getOccDiseaseJobs().size() + 1;
        table = WpsUtils.createTable(doc, rowCount, 6);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellWidth(cell, "8%", "序号");
        cell = row.getCell(1);
        setCellWidth(cell, "15%", "作业岗位");
        cell = row.getCell(2);
        setCellWidth(cell, "18%", "作业方式");
        cell = row.getCell(3);
        setCellWidth(cell, "18%", "作业形式");
        cell = row.getCell(4);
        setCellWidth(cell, "26%", "存在职业病危害因素");
        cell = row.getCell(5);
        setCellWidth(cell, "15%", "备注");
        int rowIndex = 1;
        for(OccDiseaseJob job: safeProduct.getOccDiseaseJobs()){
            row = table.getRow(rowIndex);
            cell = row.getCell(0);
            setCell(cell, String.valueOf(rowIndex));
            cell = row.getCell(1);
            setCell(cell, job.getJob());
            cell = row.getCell(2);
            setCell(cell, job.getJobWay());
            cell = row.getCell(3);
            setCell(cell, job.getJobFormal());
            cell = row.getCell(4);
            setCell(cell, StringUtils.join(job.getRisks(), ","));
            cell = row.getCell(5);
            setCell(cell, job.getRemark());
            ++rowIndex;
        }

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

    private void renderSpePerson(XWPFDocument doc, CheckReport.SafeProduct safeProduct){
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

        int rowCount = safeProduct.getSpePersons().isEmpty()? 2: safeProduct.getSpePersons().size() + 1;
        table = createTable(doc, rowCount, 4);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellWidth(cell, "21%", "姓名");
        cell = row.getCell(1);
        setCellWidth(cell, "20%", "类别");
        cell = row.getCell(2);
        setCellWidth(cell, "30%", "证号");
        cell = row.getCell(3);
        setCellWidth(cell, "29%", "有效期");

        int rowIndex = 1;
        for(SpePerson person: safeProduct.getSpePersons()){
            row = table.getRow(rowIndex++);
            cell = row.getCell(0);
            setCell(cell, person.getName());
            cell = row.getCell(1);
            setCell(cell, person.getType());
            cell = row.getCell(2);
            setCell(cell, person.getNum());
            cell = row.getCell(3);
            setCell(cell, person.getToDate());
        }

        table = createTable(doc, 1, 2);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellWidth(cell, "21%", "备注");
        cell = row.getCell(1);
        setCellWidth(cell, "79%", "");
    }

    private void renderResult(XWPFDocument doc, CheckReport.SafeProduct safeProduct){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellWidthLeft(cell, "100%", "5.检查情况结论意见");
        WpsUtils.setInd2Paragraph(cell.addParagraph(), 12, false, safeProduct.getCheckResult());
    }

    private void setCellWidthLeft(XWPFTableCell cell, String width, String content){
        WpsUtils.setCellWidth(cell, width, content, ParagraphAlignment.LEFT);
    }

    private void renderSafeRisk(XWPFDocument doc, CheckReport.SafeProduct safeProduct){
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

    private void renderRiskType(XWPFDocument doc, CheckReport.SafeProduct safeProduct){
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

    private void renderFooter(XWPFDocument doc, CheckReport report){
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setSpacingBetween(2, LineSpacingRule.AUTO);
        XWPFRun run = paragraph.createRun();
        run.setText(report.getPrintDetail());
        run.setFontSize(10);
    }
}
