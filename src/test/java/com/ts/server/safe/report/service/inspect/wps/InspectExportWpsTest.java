package com.ts.server.safe.report.service.inspect.wps;

import com.ts.server.safe.task.domain.InsReportContent;
import com.ts.server.safe.task.service.export.inspect.wps.InspectExportWps;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class InspectExportWpsTest {

    @Test
    public void testExport()throws IOException {
        InspectExportWps exportWord = new InspectExportWps();
        try (OutputStream os = new FileOutputStream(new File("ins-wps-word.docx"))) {
            exportWord.export(os, buildReport());
        }
    }

    private InsReportContent buildReport() {
        InsReportContent t = new InsReportContent();

        t.setCompName("测试单位");
        t.setChannelName("委托检查单位");
        t.setIndustry("酒店");
        t.setArea("上海");
        t.setLastTime("2020年01月01日");
        t.setCurrentTime("2020年03月01日");
        t.setEntCompType(1);
        t.setArea("上海闵行银都新村 一区26栋601");

        return t;
    }
}
