package com.ts.server.safe.report.service.export.wps;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSym;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STMerge;

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
    public static void pageTitle(XWPFDocument doc, String title){
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = paragraph.createRun();
        run.setText(title);
        run.setFontSize(16);
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
    public static void checkBox(XWPFParagraph paragraph, String label, boolean enable){
        XWPFRun run = paragraph.createRun();
        run.setText(label);
        run.setFontSize(9);
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
}
