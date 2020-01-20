package com.ts.server.safe.report.service;

import com.ts.server.safe.report.domain.CheckReport;
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
            exportWord.export(os, new CheckReport());
        }
    }
}
