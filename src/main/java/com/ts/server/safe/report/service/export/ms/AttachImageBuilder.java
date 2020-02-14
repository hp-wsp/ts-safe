package com.ts.server.safe.report.service.export.ms;

import com.ts.server.safe.report.domain.CheckReport;
import com.ts.server.safe.report.service.export.PageBuilder;
import com.ts.server.safe.task.domain.TaskContent;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * 附件图片
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
 class AttachImageBuilder implements PageBuilder {
    private static final Logger LOGGER = LoggerFactory.getLogger(AttachImageBuilder.class);

    @Override
    public void build(XWPFDocument doc, CheckReport report) {
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.LEFT);
        MsUtils.setItemRun(paragraph.createRun(), 12, false, "附件：");
        List<String> images = getImages(report);
        for(int i = 0; i < images.size(); i++){
            renderAttachImage(doc, i, images.get(i));
        }
    }

    private List<String> getImages(CheckReport report){
        List<String> images = new ArrayList<>();

        if(report.getSafeProduct().getImages().length > 0){
            images.addAll(Arrays.asList(report.getSafeProduct().getImages()));
        }

        for(TaskContent content: report.getSafeProduct().getBaseContents()){
            if(Objects.nonNull(content.getImages())){
                images.addAll(Arrays.asList(content.getImages()));
            }
        }

        for(TaskContent content: report.getSafeProduct().getSceneContents()){
            if(Objects.nonNull(content.getImages())){
                images.addAll(Arrays.asList(content.getImages()));
            }
        }

        return images;
    }

    private void renderAttachImage(XWPFDocument doc, int index, String path){
        int format = getImgFormat(path);
        if(format == -1){
            LOGGER.error("Image format fail path={}", path);
            return ;
        }
        XWPFParagraph paragraph = doc.createParagraph();
        MsUtils.setInd2Paragraph(paragraph, 10, false, String.format("附图%d", index + 1));
        renderImage(doc, format, path);
    }

    private void renderImage(XWPFDocument doc, int format, String path){
        XWPFParagraph paragraph = doc.createParagraph();
        paragraph.setIndentationFirstLine(MsUtils.CM_UNIT);
        XWPFRun run = paragraph.createRun();
        try(InputStream inputStream = new FileInputStream(path)){
            run.addPicture(inputStream, format, path, Units.toEMU(200), Units.toEMU(200));
        }catch (Exception e){
            LOGGER.error("Render image fail path={},throw={}", path, e.getMessage());
        }
        doc.createParagraph();
    }

    private int getImgFormat(String imgFile){
        if (imgFile.endsWith(".emf")) {
            return XWPFDocument.PICTURE_TYPE_EMF;
        } else if (imgFile.endsWith(".wmf")) {
            return XWPFDocument.PICTURE_TYPE_WMF;
        } else if (imgFile.endsWith(".pict")) {
            return XWPFDocument.PICTURE_TYPE_PICT;
        } else if (imgFile.endsWith(".jpeg") || imgFile.endsWith(".jpg")) {
            return XWPFDocument.PICTURE_TYPE_JPEG;
        } else if (imgFile.endsWith(".png")) {
            return XWPFDocument.PICTURE_TYPE_PNG;
        } else if (imgFile.endsWith(".dib")) {
            return XWPFDocument.PICTURE_TYPE_DIB;
        } else if (imgFile.endsWith(".gif")) {
            return XWPFDocument.PICTURE_TYPE_GIF;
        } else if (imgFile.endsWith(".tiff")) {
            return XWPFDocument.PICTURE_TYPE_TIFF;
        } else if (imgFile.endsWith(".eps")) {
            return XWPFDocument.PICTURE_TYPE_EPS;
        } else if (imgFile.endsWith(".bmp")) {
            return XWPFDocument.PICTURE_TYPE_BMP;
        } else if (imgFile.endsWith(".wpg")) {
            return XWPFDocument.PICTURE_TYPE_WPG;
        } else {
            return -1;
        }
    }
}
