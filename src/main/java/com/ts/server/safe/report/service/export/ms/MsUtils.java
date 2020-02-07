package com.ts.server.safe.report.service.export.ms;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

/**
 * ms word工具类
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class MsUtils {
    /**
     * 一厘米
     */
    public static final int CM_UNIT =  567;

    /**
     * 设置页标题
     *
     * @param doc  {@link XWPFDocument}
     * @param title 标题
     */
    public static void pageTitle(XWPFDocument doc, String title){
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setText(title);
        run.setFontSize(16);
        run.setFontFamily("宋体");
        run.setBold(true);
    }

    /**
     * 创建表格
     *
     * @param doc {@link XWPFTable}
     * @param row 行数
     * @param col 列数
     * @return {@link XWPFTable}
     */
    public static XWPFTable createTable(XWPFDocument doc, int row, int col){
        XWPFTable table = doc.createTable(row, col);
        table.setTableAlignment(TableRowAlign.CENTER);
        table.setWidth("100%");
        return table;
    }

    /**
     * 合并表格Cell
     *
     * @param table {@link XWPFTable}
     * @param col 合并列
     * @param fromRow 开始合并行
     * @param toRow 结束合并行
     */
    public static void mergeCellsV(XWPFTable table, int col, int fromRow, int toRow) {
        for (int rowIndex = fromRow; rowIndex <= toRow; rowIndex++) {
            XWPFTableCell cell = table.getRow(rowIndex).getCell(col);
            if (rowIndex == fromRow) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one,are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewVMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * 合并表格Cell
     *
     * @param table {@link XWPFTable}
     * @param row 合并行
     * @param fromCol 开始合并列
     * @param toCol 结束合并列
     */
    public static void mergeCellsH(XWPFTable table, int row, int fromCol, int toCol) {
        mergeCellsH(table.getRow(row), fromCol, toCol);
    }

    /**
     * 合并表格Cell
     *
     * @param row {@link XWPFTableRow}
     * @param fromCol 开始合并列，包含该列
     * @param toCol 结束合并列，不包含列
     */
    public static void mergeCellsH(XWPFTableRow row, int fromCol, int toCol){
        for (int colIndex = fromCol; colIndex < toCol; colIndex++) {
            XWPFTableCell cell = row.getCell(colIndex);
            if (colIndex == fromCol) {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    /**
     * 创建WORD checkbox
     *
     * @param paragraph {@link XWPFParagraph}
     * @param label 标签
     * @param enable true：选中
     */
    public static void checkBox(XWPFParagraph paragraph, String label, boolean enable){
        XWPFRun run = paragraph.createRun();
        run.setText(label + (enable? "\u2611":"\u2610") + "  ");
        run.setFontFamily("宋体");
        run.setFontSize(9);
        run.setBold(false);
    }

    /**
     * 设置Cell
     *
     * @param cell {@link XWPFTableCell}
     * @param value 值
     * @param alignment 对齐
     * @param bold 加粗
     */
    public static void setCell(XWPFTableCell cell, String value, ParagraphAlignment alignment, boolean bold){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(alignment);
        if(alignment == ParagraphAlignment.LEFT){
            paragraph.setIndentationFirstLine(CM_UNIT/2);
        }
        XWPFRun run = paragraph.createRun();
        run.setText(value);
        run.setFontSize(9);
        run.setBold(bold);
        run.setFontFamily("宋体");
    }

    /**
     * 设置 Table Cell 宽和边框
     *
     * @param cell {@link XWPFTableCell}
     * @param width 宽
     * @param showBorders 是否显示边框
     */
    public static void setCellWidthBorder(XWPFTableCell cell, String width, boolean[] showBorders){
        cell.setWidth(width);
        CTTcPr tcPr = cell.getCTTc().addNewTcPr();
        CTVerticalJc va = tcPr.addNewVAlign();
        va.setVal(STVerticalJc.CENTER);
        CTTcBorders borders =  tcPr.addNewTcBorders();
        borders.addNewTop().setVal(showBorders[0]? STBorder.SINGLE: STBorder.NIL);
        borders.addNewRight().setVal(showBorders[1]? STBorder.SINGLE: STBorder.NIL);
        borders.addNewBottom().setVal(showBorders[2]? STBorder.SINGLE: STBorder.NIL);
        borders.addNewLeft().setVal(showBorders[3]? STBorder.SINGLE: STBorder.NIL);
    }
}
