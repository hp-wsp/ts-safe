package com.ts.server.safe.report.service.export.wps;

import com.ts.server.safe.company.domain.SpePerson;
import com.ts.server.safe.report.domain.CheckReport;
import com.ts.server.safe.report.service.export.PageBuilder;
import com.ts.server.safe.task.domain.TaskContent;
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
        renderDisease(doc, report);
        renderSpePerson(doc, report);
        renderResult(doc, report);
        renderSafeRisk(doc, report);
        renderRiskType(doc, report);
        renderFooter(doc);
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
        paragraph.setIndentationFirstLine(WpsUtils.CM_UNIT/2);
        XWPFRun run = paragraph.createRun();
        //TODO 日期
//        run.setText(String.format(PROFILE_PATTER,
//                report.getCompBaseInfo().getCompName(), report.getChannelName(), report.getCheckDate()));
        run.setFontSize(9);
        run.setBold(false);
        paragraph = cell.addParagraph();
        paragraph.setIndentationFirstLine(WpsUtils.CM_UNIT/2);
        run = paragraph.createRun();
        run.setText("受限于时间、能力、专业水平等条件限制，本意见出现的误差或缺陷，请监管部门和受服务企业指正并谅解。");
        run.setFontSize(9);
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
        setCellLabelBold(cell, "100%", title);
        int rowCount = contents.isEmpty()? 2: contents.size() + 1;
        table = createTable(doc, rowCount, 4);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellLabel(cell, "8%", "序号");
        cell = row.getCell(1);
        setCellLabel(cell, "35%", "隐患描述");
        cell = row.getCell(2);
        setCellLabel(cell, "35%", "法规依据或整改措施");
        cell = row.getCell(3);
        setCellLabel(cell, "22%", "备注");
        if(contents.isEmpty()){
            row = table.getRow(1);
            cell = row.getCell(0);
            setCellValueCenter(cell, "8%", "");
            cell = row.getCell(1);
            setCellValueCenter(cell, "35%", "");
            cell = row.getCell(2);
            setCellValueCenter(cell, "35%", "");
            cell = row.getCell(3);
            setCellValueCenter(cell, "22%", "");
            return;
        }

        for(int i = 1; i < rowCount; i++){
            TaskContent t = contents.get(i - 1);
            row = table.getRow(i);
            cell = row.getCell(0);
            setCellValueCenter(cell, "8%", String.valueOf(i));
            cell = row.getCell(1);
            setCellValueCenter(cell, "35%", t.getDanDescribe());
            cell = row.getCell(2);
            setCellValueCenter(cell, "35%", t.getDanSuggest());
            cell = row.getCell(3);
            setCellValueCenter(cell, "22%", t.getRemark());
        }
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
        if(alignment == ParagraphAlignment.LEFT){
            paragraph.setIndentationFirstLine(WpsUtils.CM_UNIT/2);
        }
        XWPFRun run = paragraph.createRun();
        run.setText(value);
        run.setFontSize(9);
        run.setBold(bold);
    }

    private void renderSceneDetail(XWPFDocument doc, CheckReport report){
        renderDetail(doc, "2、现场管理隐患描述及治理措施", report.getSafeProduct().getSceneContents());
    }

    private void renderDisease(XWPFDocument doc, CheckReport report){
        XWPFTable table = WpsUtils.createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        cell.setWidth("100%");
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFRun run = paragraph.createRun();
        run.setText("3、作业场所职业病危害因素的识别");
        run.setFontSize(9);
        run.setBold(true);
        paragraph = cell.addParagraph();
        paragraph.setIndentationFirstLine(WpsUtils.CM_UNIT/2);
        run = paragraph.createRun();
        run.setText("依据《职业病危害因素分类目录》辨识，该企业存在的主要职业病危害因素有; （具体分布岗位及目录名称见下表）。");
        run.setFontSize(9);
        run.setBold(false);

        table = WpsUtils.createTable(doc, 2, 6);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellLabel(cell, "8%", "序号");
        cell = row.getCell(1);
        setCellLabel(cell, "15%", "作业岗位");
        cell = row.getCell(2);
        setCellLabel(cell, "18%", "作业方式");
        cell = row.getCell(3);
        setCellLabel(cell, "18%", "作业形式");
        cell = row.getCell(4);
        setCellLabel(cell, "26%", "存在职业病危害因素");
        cell = row.getCell(5);
        setCellLabel(cell, "15%", "备注");
        row = table.getRow(1);
        cell = row.getCell(0);
        setCellLabel(cell, "8%", "");
        cell = row.getCell(1);
        setCellLabel(cell, "15%", "");
        cell = row.getCell(2);
        setCellLabel(cell, "18%", "");
        cell = row.getCell(3);
        setCellLabel(cell, "18%", "");
        cell = row.getCell(4);
        setCellLabel(cell, "26%", "");
        cell = row.getCell(5);
        setCellLabel(cell, "15%", "");

        table = WpsUtils.createTable(doc, 1, 2);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellLabel(cell, "41%", "职业病危害风险分类辨识");
        cell = row.getCell(1);
        cell.setWidth("59%");
        XWPFParagraph p = cell.getParagraphArray(0);
        p.setAlignment(ParagraphAlignment.CENTER);
        WpsUtils.checkBox(p, "一般", false);
        WpsUtils.checkBox(p, "较重", false);
        WpsUtils.checkBox(p, "严重", false);
        WpsUtils.checkBox(p, "局部严重", false);
    }

    private void renderSpePerson(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 1, 2);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabelBold(cell, "41%", "4、涉及特种作业人员及证书");
        cell = row.getCell(1);
        cell.setWidth("59%");
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        WpsUtils.checkBox(paragraph, "涉及", report.getSafeProduct().isSpePerson());
        WpsUtils.checkBox(paragraph, "不涉及", !report.getSafeProduct().isSpePerson());

        int rowCount = report.getSafeProduct().getSpePersons().isEmpty()? 2: report.getSafeProduct().getSpePersons().size() + 1;
        table = createTable(doc, rowCount, 4);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellLabel(cell, "21%", "姓名");
        cell = row.getCell(1);
        setCellLabel(cell, "20%", "类别");
        cell = row.getCell(2);
        setCellLabel(cell, "30%", "证号");
        cell = row.getCell(3);
        setCellLabel(cell, "29%", "有效期");
        if(report.getSafeProduct().getSpePersons().isEmpty()){
            row = table.getRow(1);
            cell = row.getCell(0);
            setCellLabel(cell, "21%", "");
            cell = row.getCell(1);
            setCellLabel(cell, "20%", "");
            cell = row.getCell(2);
            setCellLabel(cell, "30%", "");
            cell = row.getCell(3);
            setCellLabel(cell, "29%", "");
            return ;
        }

        for(int i = 1; i < rowCount; i++){
            SpePerson person = report.getSafeProduct().getSpePersons().get(i -1);
            row = table.getRow(i);
            cell = row.getCell(0);
            setCellLabel(cell, "21%", person.getName());
            cell = row.getCell(1);
            setCellLabel(cell, "20%", person.getType());
            cell = row.getCell(2);
            setCellLabel(cell, "30%", person.getNum());
            cell = row.getCell(3);
            setCellLabel(cell, "29%", person.getToDate());
        }

        table = createTable(doc, 1, 2);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellLabel(cell, "21%", "备注");
        cell = row.getCell(1);
        cell.setWidth("79%");
    }

    private void renderResult(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        cell.setWidth("100%");
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFRun run = paragraph.createRun();
        run.setText("5.检查情况结论意见");
        run.setFontSize(9);
        run.setBold(true);
        paragraph = cell.addParagraph();
        paragraph.setIndentationFirstLine(WpsUtils.CM_UNIT/2);
        run = paragraph.createRun();
        run.setFontSize(9);
        run.setText("\r\r");
    }

    private void renderSafeRisk(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        setCellLabelBold(cell, "100%", "6.检查判定企业综合安全风险状况");
        table = createTable(doc, 1, 2);
        row = table.getRow(0);
        cell = row.getCell(0);
        setCellLabel(cell, "38%", "检查判定企业综合安全风险状况");
        cell = row.getCell(1);
        cell.setWidth("62%");
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        int level = report.getSafeProduct().getSafeRiskLevel();
        WpsUtils.checkBox(paragraph, "高", level == 4);
        WpsUtils.checkBox(paragraph, "较高", level == 3);
        WpsUtils.checkBox(paragraph, "一般", level == 2);
        WpsUtils.checkBox(paragraph, "低", level == 1);

        table = createTable(doc, 1, 1);
        row = table.getRow(0);
        cell = row.getCell(0);
        cell.setWidth("100%");
        paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = paragraph.createRun();
        run.setText("风险判定说明：");
        run.setFontSize(9);
        run.setBold(true);
        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(WpsUtils.CM_UNIT/2);
        run = paragraph.createRun();
        run.setFontSize(9);
        run.setBold(false);
        run.setText(report.getSafeProduct().getRiskExplain());
    }

    private void renderRiskType(XWPFDocument doc, CheckReport report){
        XWPFTable table = createTable(doc, 1, 1);
        XWPFTableRow row = table.getRow(0);
        XWPFTableCell cell = row.getCell(0);
        cell.setWidth("100%");
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = paragraph.createRun();
        run.setText("7、生产安全事故类型风险辨识:");
        run.setFontSize(9);
        run.setBold(true);
        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(WpsUtils.CM_UNIT/2);
        run = paragraph.createRun();
        run.setText(report.getSafeProduct().getSafeProductRisk());
        run.setBold(false);
        run.setFontSize(9);

        table = createTable(doc, 1, 1);
        row = table.getRow(0);
        cell = row.getCell(0);
        cell.setWidth("100%");
        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        run.setText("受检查企业意见:");
        run.setFontSize(9);
        run.setBold(true);

        paragraph = cell.addParagraph();
        run = paragraph.createRun();
        run.setText(report.getSafeProduct().getCheckCompanyIdea());
        run.setFontSize(9);
        run.setBold(false);

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(WpsUtils.CM_UNIT * 10);
        run = paragraph.createRun();
        run.setText("（单位盖章）");
        run.setFontSize(9);
        run.setBold(false);

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        run = paragraph.createRun();
        run.setText("    年  月  日");
        run.setFontSize(9);
        run.setBold(false);
    }

    private void renderFooter(XWPFDocument doc){
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setSpacingBetween(2, LineSpacingRule.AUTO);
        XWPFRun run = paragraph.createRun();
        run.setText("本报告一式三份，委托单位、受检企业、报告出具单位各一份");
        run.setFontSize(10);
    }
}
