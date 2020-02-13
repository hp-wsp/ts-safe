package com.ts.server.safe.report.service.export.ms;

import com.ts.server.safe.company.domain.SpePerson;
import com.ts.server.safe.report.domain.CheckReport;
import com.ts.server.safe.report.service.export.PageBuilder;
import com.ts.server.safe.task.domain.TaskContent;
import org.apache.poi.xwpf.usermodel.*;

import java.util.Collections;
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

        XWPFTable table =createTable(doc);
        renderProfile(table, report);
        renderBaseDetail(table, report);
        renderSceneDetail(table, report);
        renderDisease(table, report);
        renderSpePerson(table, report);
        renderResult(table, report);
        renderSafeRisk(table, report);
        renderRiskType(table, report);
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

    private void renderProfile(XWPFTable table, CheckReport report){
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
        XWPFRun run = paragraph.createRun();
        run.setText(content);
        run.setFontSize(9);
        run.setFontFamily("宋体");
        run.setBold(false);
    }

    private void renderBaseDetail(XWPFTable table, CheckReport report){
        renderDetail(table, 1,"1、基础管理隐患描述及治理措施", report.getSafeProduct().getBaseContents());
    }

    private void renderDetail(XWPFTable table, int rowIndex, String title, List<TaskContent> contents){
        XWPFTableRow row = table.getRow(rowIndex);
        XWPFTableCell cell = row.getCell(0);
        setCellLabelBold(cell, title);

        row = table.getRow(rowIndex + 1);
        cell = row.getCell(0);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFTable contentTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        contentTable.getCTTbl().addNewTblPr().addNewTblW();
        contentTable.setWidth("100%");

        row = contentTable.createRow();
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "8%", new boolean[]{false, true, true, false});
        setCellLabel(cell, "序号");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "35%", new boolean[]{false, true, true, true});
        setCellLabel(cell, "隐患描述");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "35%", new boolean[]{false, true, true, true});
        setCellLabel(cell, "法规依据或整改措施");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "22%", new boolean[]{false, false, true, true});
        setCellLabel(cell, "备注");

        boolean isEmpty = contents.isEmpty();
        if(isEmpty){
            contents = Collections.singletonList(new TaskContent());
        }

        int rowCount = contents.size();
        for(int i = 0; i < rowCount; i++){
            TaskContent t = contents.get(i);
            row = contentTable.createRow();
            cell = row.getCell(0);
            boolean isLast = i == (rowCount - 1);
            setCellWidthBorder(cell, "8%", new boolean[]{true, true, !isLast, false});
            setCellValueCenter(cell, isEmpty? "": String.valueOf(i + 1));
            cell = row.getCell(1);
            setCellWidthBorder(cell, "35%", new boolean[]{true, true, !isLast, false});
            setCellValueCenter(cell, isEmpty? "":t.getDanDescribe());
            cell = row.getCell(2);
            setCellWidthBorder(cell, "35%", new boolean[]{true, true, !isLast, false});
            setCellValueCenter(cell, isEmpty? "":t.getDanSuggest());
            cell = row.getCell(3);
            setCellWidthBorder(cell, "22%", new boolean[]{true, false, !isLast, false});
            setCellValueCenter(cell, isEmpty? "":t.getRemark());
        }
    }

    private void setCellWidthBorder(XWPFTableCell cell, String width, boolean[] showBorders){
        MsUtils.setCellWidthBorder(cell, width, showBorders);
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

    private void renderSceneDetail(XWPFTable table, CheckReport report){
        renderDetail(table, 3, "2、现场管理隐患描述及治理措施", report.getSafeProduct().getSceneContents());
    }

    private void renderDisease(XWPFTable table, CheckReport report){
        XWPFTableRow row = table.getRow(5);
        XWPFTableCell cell = row.getCell(0);
        setCellLabelBold(cell, "3、作业场所职业病危害因素的识别");

        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT/2);
        XWPFRun run = paragraph.createRun();
        run.setText("依据《职业病危害因素分类目录》辨识，该企业存在的主要职业病危害因素有; （具体分布岗位及目录名称见下表）。");
        run.setFontSize(9);
        run.setBold(false);
        run.setFontFamily("宋体");

        row = table.getRow(6);
        cell = row.getCell(0);
        paragraph = cell.getParagraphArray(0);
        XWPFTable subTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        subTable.getCTTbl().addNewTblPr().addNewTblW();
        subTable.setWidth("100%");
        row = subTable.createRow();
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "8%", new boolean[]{false, true, true, false});
        setCellLabel(cell, "序号");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "15%", new boolean[]{false, true, true, true});
        setCellLabel(cell, "作业岗位");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "18%", new boolean[]{false, true, true, true});
        setCellLabel(cell, "作业方式");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "18%", new boolean[]{false, true, true, true});
        setCellLabel(cell,  "作业形式");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "26%", new boolean[]{false, true, true, true});
        setCellLabel(cell, "存在职业病危害因素");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "15%", new boolean[]{false, false, true, false});
        setCellLabel(cell,  "备注");
        row = subTable.createRow();
        cell = row.getCell(0);
        setCellWidthBorder(cell, "8%", new boolean[]{false, true, false, false});
        setCellLabel(cell, "");
        cell = row.getCell(1);
        setCellWidthBorder(cell, "15%", new boolean[]{false, true, false, false});
        setCellLabel(cell, "");
        cell = row.getCell(2);
        setCellWidthBorder(cell, "18%", new boolean[]{false, true, false, false});
        setCellLabel(cell, "");
        cell = row.getCell(3);
        setCellWidthBorder(cell, "18%", new boolean[]{false, true, false, false});
        setCellLabel(cell, "");
        cell = row.getCell(4);
        setCellWidthBorder(cell, "26%", new boolean[]{false, true, false, false});
        setCellLabel(cell, "");
        cell = row.getCell(5);
        setCellWidthBorder(cell, "15%", new boolean[]{false, false, false, false});
        setCellLabel(cell, "");

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

    private void renderSpePerson(XWPFTable table, CheckReport report){
        XWPFTableRow row = table.getRow(8);
        XWPFTableCell cell = row.getCell(0);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFTable subTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        subTable.getCTTbl().addNewTblPr().addNewTblW();
        subTable.setWidth("100%");
        row = subTable.createRow();
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "41%", new boolean[]{false, true, false, false});
        setCellLabelBold(cell,  "4、涉及特种作业人员及证书");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "59%", new boolean[]{false, false, false, false});
        paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        MsUtils.checkBox(paragraph, "涉及", report.getSafeProduct().isSpePerson());
        MsUtils.checkBox(paragraph, "不涉及", !report.getSafeProduct().isSpePerson());

        row = table.getRow(9);
        cell = row.getCell(0);
        paragraph = cell.getParagraphArray(0);
        subTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        subTable.getCTTbl().addNewTblPr().addNewTblW();
        subTable.setWidth("100%");
        List<SpePerson> persons = report.getSafeProduct().getSpePersons();
        boolean isEmpty = persons.isEmpty();
        if(isEmpty){
            persons = Collections.singletonList(new SpePerson());
        }

        row = subTable.createRow();
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "21%", new boolean[]{false, true, true, false});
        setCellLabel(cell, "姓名");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "20%", new boolean[]{false, true, true, false});
        setCellLabel(cell, "类别");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "30%", new boolean[]{false, true, true, false});
        setCellLabel(cell, "证号");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "29%", new boolean[]{false, true, true, false});
        setCellLabel(cell, "有效期");

        int rowCount = persons.size();
        for(int i = 0; i < rowCount; i++){
            boolean isLast = i == (rowCount - 1);
            SpePerson person = persons.get(i);
            row = subTable.createRow();
            cell = row.getCell(0);
            setCellWidthBorder(cell, "21%", new boolean[]{false, true, !isLast, false});
            setCellLabel(cell, isEmpty? "": person.getName());
            cell = row.getCell(1);
            setCellWidthBorder(cell, "20%", new boolean[]{false, true, !isLast, false});
            setCellLabel(cell, isEmpty? "": person.getType());
            cell = row.getCell(2);
            setCellWidthBorder(cell, "30%", new boolean[]{false, true, !isLast, false});
            setCellLabel(cell, isEmpty? "": person.getNum());
            cell = row.getCell(3);
            setCellWidthBorder(cell, "29%", new boolean[]{false, false, !isLast, false});
            setCellLabel(cell, isEmpty? "": person.getToDate());
        }

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

    private void renderResult(XWPFTable table, CheckReport report){
        XWPFTableRow row = table.getRow(11);
        XWPFTableCell cell = row.getCell(0);
        setCellLabelBold(cell,"5.检查情况结论意见");
        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT/2);
        cell.addParagraph();
    }

    private void renderSafeRisk(XWPFTable table, CheckReport report){
        XWPFTableRow row = table.getRow(12);
        XWPFTableCell cell = row.getCell(0);
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        XWPFTable subTable= cell.insertNewTbl(paragraph.getCTP().newCursor());
        subTable.getCTTbl().addNewTblPr().addNewTblW();
        subTable.setWidth("100%");

        row = subTable.createRow();
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "38%", new boolean[]{false, false, false, false});
        setCellLabelBold(cell, "6.检查判定企业综合安全风险状况");
        cell = row.addNewTableCell();
        setCellWidthBorder(cell, "62%", new boolean[]{false, false, false, false});
        MsUtils.mergeCellsH(row, 0, 2);
        row = subTable.createRow();
        cell = row.getCell(0);
        setCellWidthBorder(cell, "38%", new boolean[]{false, false, false, false});
        setCellLabel(cell, "检查判定企业综合安全风险状况");
        cell = row.getCell(1);
        setCellWidthBorder(cell, "62%", new boolean[]{false, false, false, false});
        paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        int level = report.getSafeProduct().getSafeRiskLevel();
        MsUtils.checkBox(paragraph, "高", level == 4);
        MsUtils.checkBox(paragraph, "较高", level == 3);
        MsUtils.checkBox(paragraph, "一般", level == 2);
        MsUtils.checkBox(paragraph, "低", level == 1);

        row = table.getRow(12);
        cell = row.getCell(0);
        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        XWPFRun run = paragraph.createRun();
        run.setText("风险判定说明：");
        run.setFontSize(9);
        run.setBold(true);
        run.setFontFamily("宋体");
        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT/2);
        run = paragraph.createRun();
        run.setText(report.getSafeProduct().getRiskLevelExplain());
        run.setFontSize(9);
        run.setBold(false);
        run.setFontFamily("宋体");
    }

    private void renderRiskType(XWPFTable table, CheckReport report){
        XWPFTableRow row = table.getRow(13);
        XWPFTableCell cell = row.getCell(0);
        setCellLabelBold(cell, "7、生产安全事故类型风险辨识");

        XWPFParagraph paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT/2);
        XWPFRun run = paragraph.createRun();
        run.setText(report.getSafeProduct().getSafeProductRisk());
        run.setBold(false);
        run.setFontSize(9);
        run.setFontFamily("宋体");

        row = table.getRow(14);
        cell = row.getCell(0);
        paragraph = cell.getParagraphArray(0);
        run = paragraph.createRun();
        run.setText("受检查企业意见:");
        run.setFontSize(9);
        run.setBold(true);
        run.setFontFamily("宋体");

        paragraph = cell.addParagraph();
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT/2);
        run = paragraph.createRun();
        run.setText(report.getSafeProduct().getCheckCompanyIdea());
        run.setFontSize(9);
        run.setBold(false);
        run.setFontFamily("宋体");

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT * 10);
        run = paragraph.createRun();
        run.setText("（单位盖章）");
        run.setFontSize(9);
        run.setBold(false);
        run.setFontFamily("宋体");

        paragraph = cell.addParagraph();
        paragraph.setAlignment(ParagraphAlignment.RIGHT);
        run = paragraph.createRun();
        run.setText("    年  月  日");
        run.setFontSize(9);
        run.setBold(false);
        run.setFontFamily("宋体");
    }

    private void renderFooter(XWPFDocument doc, CheckReport report){
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        paragraph.setSpacingBetween(2, LineSpacingRule.AUTO);
        XWPFRun run = paragraph.createRun();
        run.setText(report.getPrintDetail());
        run.setFontSize(10);
        run.setFontFamily("宋体");
    }
}
