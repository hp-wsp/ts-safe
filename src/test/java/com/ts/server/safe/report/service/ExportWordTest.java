package com.ts.server.safe.report.service;

import com.ts.server.safe.report.domain.CheckReport;
import com.ts.server.safe.report.service.export.ExportWord;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ExportWordTest {

    @Test
    public void testExport()throws IOException {
        ExportWord exportWord = new ExportWord();
        try (OutputStream os = new FileOutputStream(new File("word.docx"))) {
            exportWord.export(os, buildReport());
        }
    }

    private CheckReport buildReport(){
        CheckReport t = new CheckReport();

        t.setCompName("测试单位");
        t.setChannelName("委托检查单位");
        t.setIndustry("酒店");
        t.setArea("上海");
        t.setCheckDate("2020-01-01至2020-01-20");

        CheckReport.BzDetail bzDetail = new CheckReport.BzDetail();
        bzDetail.setConNum("1111-222222-333");
        bzDetail.setServiceBase("测试");
        bzDetail.setServiceMethod("测试一");
        bzDetail.setServiceRange("检查");
        t.setBzDetail(bzDetail);

        return t;
    }
}
