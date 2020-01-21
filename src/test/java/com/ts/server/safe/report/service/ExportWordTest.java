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
        bzDetail.setChanName("委托单位");
        bzDetail.setChanAddress("都市路银都路");
        bzDetail.setChanBusScope("安全防护门系统的开发；安全培训；安全咨询；安全生产技术服务；安全系统监控服务；二级安全生产标准化评审及咨询；安全事故物证分析和技术鉴定；职业卫生技术服务；引进新技术、新品种，开展技术培训、技术交流和咨询服务；电子商务平台的开发建设；软件开发系统集成服务；计算机网络系统工程服务；信息系统集成服务；网络集成系统建设、维护、运营、租赁；信息技术咨询服务；计算机技术咨询；物联网技术服务；标准及标准化服务；文化活动的组织与策划；文化设计与建设；软件技术服务；安全检查仪器的制造（限分支机构）；安全技术防范系统设计、施工、维修；劳动力外包服务；人力资源服务外包；文化创意设计；智慧城市的相关服务、规划、设计；劳保消防安全用品、通用仪器仪表的销售。（未经批准不得从事P2P网贷、股权众筹、互联网保险、资管及跨界从事金融、第三方支付、虚拟货币交易、ICO、非法外汇等互联网金融业务)（依法须经批准的项目，经相关部门批准后方可开展经营活动）");
        t.setBzDetail(bzDetail);

        return t;
    }
}
