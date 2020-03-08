package com.ts.server.safe.task.service.export.initial.ms;

import com.ts.server.safe.task.domain.InitReportContent;
import com.ts.server.safe.task.service.export.ExportWord;
import com.ts.server.safe.task.service.export.PageBuilder;
import org.apache.poi.xwpf.model.XWPFHeaderFooterPolicy;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.impl.xb.xmlschema.SpaceAttribute;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 导出WORD报表
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class InitMsExportWord implements ExportWord {
    private final PageBuilder coverBuilder;
    private final PageBuilder entrustBuilder;
    private final PageBuilder companyBuilder;
    private final PageBuilder safeBuilder;
    private final PageBuilder attachImgBuilder;

    /**
     * 构造{@link InitMsExportWord}
     */
    public InitMsExportWord(){
        this.coverBuilder = new CoverBuilder();
        this.entrustBuilder = new DetailBuilder();
        this.companyBuilder = new CompanyBuilder();
        this.safeBuilder = new SafeBuilder();
        this.attachImgBuilder = new AttachImageBuilder();
    }

    /**
     * 导出WORD
     *
     * @param outputStream {@link OutputStream}
     * @param report {@link InitReportContent}
     * @throws IOException
     */
    @Override
    public void export(OutputStream outputStream, InitReportContent report)throws IOException {

        try(XWPFDocument doc = new XWPFDocument()){

            coverBuilder.build(doc, report);
            entrustBuilder.build(doc, report);
            companyBuilder.build(doc, report);
            safeBuilder.build(doc, report);
            attachImgBuilder.build(doc, report);
            numberFooter(doc);

            doc.write(outputStream);
        }
    }

    private void numberFooter(XWPFDocument document)  {
        CTP ctp = CTP.Factory.newInstance();
        XWPFParagraph codePara = new XWPFParagraph(ctp, document);
        XWPFRun r1 = codePara.createRun();
        r1.setText("第");
        r1.setFontSize(11);

        r1 = codePara.createRun();
        CTFldChar fldChar = r1.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));

        r1 = codePara.createRun();
        CTText ctText = r1.getCTR().addNewInstrText();
        ctText.setStringValue("PAGE  \\* MERGEFORMAT");
        ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
        r1.setFontSize(11);

        fldChar = r1.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

        r1 = codePara.createRun();
        r1.setText("页 总共");
        r1.setFontSize(11);

        r1 = codePara.createRun();
        fldChar = r1.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("begin"));

        r1 = codePara.createRun();
        ctText = r1.getCTR().addNewInstrText();
        ctText.setStringValue("NUMPAGES  \\* MERGEFORMAT ");
        ctText.setSpace(SpaceAttribute.Space.Enum.forString("preserve"));
        r1.setFontSize(11);

        fldChar = r1.getCTR().addNewFldChar();
        fldChar.setFldCharType(STFldCharType.Enum.forString("end"));

        r1 = codePara.createRun();
        r1.setText("页");
        r1.setFontSize(11);

        codePara.setAlignment(ParagraphAlignment.CENTER);
        codePara.setVerticalAlignment(TextAlignment.CENTER);
        codePara.setBorderTop(Borders.THICK);
        XWPFParagraph[] newParagraphs = new XWPFParagraph[1];
        newParagraphs[0] = codePara;
        CTSectPr sectPr = document.getDocument().getBody().addNewSectPr();
        XWPFHeaderFooterPolicy headerFooterPolicy = new XWPFHeaderFooterPolicy(document, sectPr);
        headerFooterPolicy.createFooter(STHdrFtr.DEFAULT, newParagraphs);
    }
}
