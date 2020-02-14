package com.ts.server.safe.report.service.export.wps;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.math.BigInteger;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Wps工具类
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
class WpsUtils {
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
    static void pageTitle(XWPFDocument doc, String title){
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        setItemRun(run, 16, true, title);
    }

    /**
     * 创建表格
     *
     * @param doc {@link XWPFTable}
     * @param row 行数
     * @param col 列数
     * @return {@link XWPFTable}
     */
    static XWPFTable createTable(XWPFDocument doc, int row, int col){
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
    static void mergeCellsV(XWPFTable table, int col, int fromRow, int toRow) {
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
    static void mergeCellsH(XWPFTable table, int row, int fromCol, int toCol) {
        for (int colIndex = fromCol; colIndex <= toCol; colIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(colIndex);
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
    static void checkBox(XWPFParagraph paragraph, String label, boolean enable){
        XWPFRun run = paragraph.createRun();
        run.setText(label);
        run.setFontSize(12);
        run.setBold(false);
        run = paragraph.createRun();
        run.setVerticalAlignment("baseline");
        if(enable){
            CTSym sym = run.getCTR().addNewSym();
            sym.setFont("Wingdings 2");
            sym.setChar(new byte[]{82});
        }else{
            run.setFontFamily("Wingdings 2");
            run.setText("□");
        }
        run = paragraph.createRun();
        run.setText("  ");
    }

    /**
     * 设置表格头cell内容
     *
     * @param cell {@link XWPFTableCell}
     * @param width cell宽度
     * @param content 内容
     * @param alignment 对齐方式
     */
    static void setCellWidth(XWPFTableCell cell, String width, String content, ParagraphAlignment alignment){
        cell.setWidth(width);
        setCell(cell, content, alignment);
    }

    /**
     * 设置表格cell内容
     *
     * @param cell {@link XWPFTableCell}
     * @param content 内容
     * @param alignment 对齐方式
     */
    static void setCell(XWPFTableCell cell, String content, ParagraphAlignment alignment){
        XWPFParagraph paragraph = cell.getParagraphArray(0);
        paragraph.setAlignment(alignment);
        XWPFRun run = paragraph.createRun();
        setItemRun(run, 12, false, content);
    }

    /**
     * 设置显示内容
     *
     * @param run {@link XWPFRun}
     * @param fontSize 字体大小
     * @param bold true:粗体
     * @param content 显示内容
     */
    static void setItemRun(XWPFRun run, int fontSize, boolean bold, String content){
        run.setText(content);
        run.setFontSize(fontSize);
        run.setFontFamily("宋体");
        run.setBold(bold);
    }

    /**
     * 设置缩段落
     *
     * @param paragraph {@link XWPFParagraph}
     * @param fontSize 字体大小
     * @param bold true:粗体
     * @param content 显示内容
     */
    static void setInd2Paragraph(XWPFParagraph paragraph, int fontSize, boolean bold, String content){
        paragraph.setIndentationFirstLine(CM_UNIT/2);
        XWPFRun run = paragraph.createRun();
        setItemRun(run, fontSize, bold, content);
    }

    /**
     * 添加空段落
     *
     * @param document {@link XWPFDocument}
     * @param number 段落数
     */
    static void addEmptyParagraph(XWPFDocument document, int number){
        for(int i = 0; i < number; i++){
            XWPFParagraph paragraph = document.createParagraph();
            paragraph.setAlignment(ParagraphAlignment.CENTER);
            setItemRun(paragraph.createRun(), 12, false, " ");
        }
    }

    /**
     * 设置 Table Cell 宽和边框
     *
     * @param cell {@link XWPFTableCell}
     * @param widthPix 宽
     * @param showBorders 是否显示边框
     */
    static void setCellWidthBorder(XWPFTableCell cell, int widthPix, boolean[] showBorders){
        CTTcPr tcPr = cell.getCTTc().addNewTcPr();
        CTTblWidth width = tcPr.addNewTcW();
        width.setW(BigInteger.valueOf(widthPix));
        CTVerticalJc va = tcPr.addNewVAlign();
        va.setVal(STVerticalJc.CENTER);
        CTTcBorders borders =  tcPr.addNewTcBorders();
        borders.addNewTop().setVal(showBorders[0]? STBorder.SINGLE: STBorder.NIL);
        borders.addNewRight().setVal(showBorders[1]? STBorder.SINGLE: STBorder.NIL);
        borders.addNewBottom().setVal(showBorders[2]? STBorder.SINGLE: STBorder.NIL);
        borders.addNewLeft().setVal(showBorders[3]? STBorder.SINGLE: STBorder.NIL);
    }

    /**
     * 渲染表格
     *
     * @param doc {@link XWPFDocument}
     * @param data 显示数据
     * @param settings 表格设置
     * @param <T>
     */
    static <T> void renderTable(XWPFDocument doc, List<T> data, List<ColSetting<T>> settings){
        boolean isEmpty = data.isEmpty();
        int rowCount = isEmpty? 2: data.size() + 1;
        int colCount = settings.size();
        XWPFTable table = createTable(doc, rowCount, colCount);

        XWPFTableRow row = table.getRow(0);
        for(int i = 0; i < colCount; i++){
            XWPFTableCell cell = row.getCell(i);
            ColSetting<T> setting = settings.get(i);
            setCellWidth(cell, setting.width, setting.title, ParagraphAlignment.CENTER);
        }

        if(isEmpty){
            row = table.getRow(1);
            for(int i = 0; i < colCount; i++){
                ColSetting<T> setting = settings.get(i);
                XWPFTableCell cell = row.getCell(i);
                setCellWidth(cell, setting.width, "", ParagraphAlignment.CENTER);
            }
            return;
        }

        int rowIndex = 1;
        for(T t: data){
            row = table.getRow(rowIndex);
            for(int i = 0; i < colCount; i++){
                ColSetting<T> setting = settings.get(i);
                XWPFTableCell cell = row.getCell(i);
                String value = setting.valFun.apply(t, rowIndex);
                setCellWidth(cell, setting.width, value, ParagraphAlignment.CENTER);
            }
            ++rowIndex;
        }
    }

    /**
     * Table column 设置
     *
     * @param <T>
     */
    public static class ColSetting<T> {
        private final String width;
        private final String title;
        private final BiFunction<T, Integer, String> valFun;

        public ColSetting(String width, String title, BiFunction<T, Integer, String> valFun) {
            this.width = width;
            this.title = title;
            this.valFun = valFun;
        }
    }
}
