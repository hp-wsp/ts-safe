package com.ts.server.safe.report.service.wps;

import com.ts.server.safe.company.domain.RiskChemical;
import com.ts.server.safe.company.domain.SpePerson;
import com.ts.server.safe.report.domain.CheckReport;
import com.ts.server.safe.report.service.export.wps.WpsExportWord;
import com.ts.server.safe.task.domain.TaskContent;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WpsExportWordTest {

    @Test
    public void testExport()throws IOException {
        WpsExportWord exportWord = new WpsExportWord();
        try (OutputStream os = new FileOutputStream(new File("wps-word.docx"))) {
            exportWord.export(os, buildReport());
        }
    }

    private CheckReport buildReport(){
        CheckReport t = new CheckReport();

        t.setCompName("测试单位");
        t.setChannelName("委托检查单位");
        t.setIndustry("酒店");
        t.setArea("上海");
        //TODO 日期
        //t.setCheckDate("2020-01-01至2020-01-20");

        CheckReport.ReportDetail bzDetail = new CheckReport.ReportDetail();
        bzDetail.setConNum("1111-222222-333");
        bzDetail.setServiceBase("测试");
        bzDetail.setServiceMethod("测试一");
        bzDetail.setServiceRange("检查");
        bzDetail.setChanName("委托单位");
        bzDetail.setChanAddress("都市路银都路");
        bzDetail.setChanBusScope("安全防护门系统的开发；安全培训；安全咨询；安全生产技术服务；安全系统监控服务；二级安全生产标准化评审及咨询；安全事故物证分析和技术鉴定；职业卫生技术服务；引进新技术、新品种，开展技术培训、技术交流和咨询服务；电子商务平台的开发建设；软件开发系统集成服务；计算机网络系统工程服务；信息系统集成服务；网络集成系统建设、维护、运营、租赁；信息技术咨询服务；计算机技术咨询；物联网技术服务；标准及标准化服务；文化活动的组织与策划；文化设计与建设；软件技术服务；安全检查仪器的制造（限分支机构）；安全技术防范系统设计、施工、维修；劳动力外包服务；人力资源服务外包；文化创意设计；智慧城市的相关服务、规划、设计；劳保消防安全用品、通用仪器仪表的销售。（未经批准不得从事P2P网贷、股权众筹、互联网保险、资管及跨界从事金融、第三方支付、虚拟货币交易、ICO、非法外汇等互联网金融业务)（依法须经批准的项目，经相关部门批准后方可开展经营活动）");
        t.setReportDetail(bzDetail);

        CheckReport.PersonInfo personInfo = new CheckReport.PersonInfo();
        personInfo.setPhone("0792-6667839");
        personInfo.setName("测试1");
        personInfo.setMobile("18601689972");
        bzDetail.setChanContact(personInfo);

        CheckReport.PersonInfo leader = new CheckReport.PersonInfo();
        leader.setPhone("0792-6667839");
        leader.setName("领导1");
        leader.setMobile("");
        bzDetail.setLeader(leader);

        List<CheckReport.PersonInfo> workers = new ArrayList<>(3);
        for(int i = 0; i < 3; i++){
            CheckReport.PersonInfo worker = new CheckReport.PersonInfo();
            worker.setPhone("0792-666781" + i);
            worker.setName("检查员" + i);
            worker.setMobile("");
            workers.add(worker);
        }
        bzDetail.setWorkers(workers);

        CheckReport.PersonInfo principalPerson = new CheckReport.PersonInfo();
        principalPerson.setName("负责人1");
        principalPerson.setMobile("18601689972");
        bzDetail.setPrincipalPerson(principalPerson);

        CheckReport.PersonInfo auditPerson = new CheckReport.PersonInfo();
        auditPerson.setName("审核人1");
        auditPerson.setMobile("18601689972");
        bzDetail.setAuditPerson(auditPerson);

        CheckReport.PersonInfo reportPerson = new CheckReport.PersonInfo();
        reportPerson.setName("报告人1");
        reportPerson.setMobile("18601689972");
        bzDetail.setReportPerson(reportPerson);

        CheckReport.CompBaseInfo compBaseInfo = new CheckReport.CompBaseInfo();
        compBaseInfo.setCompName("测试公司");
        compBaseInfo.setRegAddress("银都新村231");
        compBaseInfo.setCreditCode("913301827595302508");
        compBaseInfo.setCompProfile("建德市永达五金工具有限公司注册于2004年4月，法定代表人李云良,现有员工约15人，营业执照经营范围：五金工具。主要原材料钢材。");
        t.setCompBaseInfo(compBaseInfo);

        CheckReport.PersonInfo conPerson = new CheckReport.PersonInfo();
        conPerson.setName("李云良");
        conPerson.setPhone("");
        conPerson.setMobile("13606603562");
        compBaseInfo.setConPerson(conPerson);

        CheckReport.PersonInfo safePerson = new CheckReport.PersonInfo();
        safePerson.setName("邓孝法");
        safePerson.setPhone("");
        safePerson.setMobile("18814839345");
        compBaseInfo.setSafePerson(safePerson);

        CheckReport.PersonInfo contractPerson = new CheckReport.PersonInfo();
        safePerson.setName("李云良");
        safePerson.setPhone("");
        safePerson.setMobile("13606603562");
        compBaseInfo.setContractPerson(contractPerson);

        List<RiskChemical> riskChemicals = new ArrayList<>();
        RiskChemical riskChemical = new RiskChemical();
        riskChemical.setName("测试1");
        riskChemical.setAlias("测试1");
        riskChemical.setCas("111-222-3333");
        riskChemical.setMaxStore("2公斤");
        riskChemicals.add(riskChemical);
        compBaseInfo.setRiskChemicals(riskChemicals);

        CheckReport.SafeProduct safeProduct = new CheckReport.SafeProduct();
        List<TaskContent> baseContents = new ArrayList<>();
        TaskContent taskContent = new TaskContent();
        taskContent.setDanDescribe("《中华人民共和国公司法》第七条企业经营者应当依法向工商行政管理机关办理有关登记手续，取得工商营业执照");
        taskContent.setDanSuggest("《中华人民共和国公司法》第七条企业经营者应当依法向工商行政管理机关办理有关登记手续，取得工商营业执照");
        taskContent.setRemark("基础隐患图1");
        List<String> images = new ArrayList<>();
        images.add("/Users/wangwei/work/workspace/ts-safe/src/test/resources/aaa.png");
        images.add("/Users/wangwei/work/workspace/ts-safe/src/test/resources/bbb.png");
        taskContent.setImages(images.toArray(String[]::new));
        baseContents.add(taskContent);
        safeProduct.setBaseContents(baseContents);
        safeProduct.setSceneContents(Collections.emptyList());
        t.setSafeProduct(safeProduct);

        List<SpePerson> spePersons = new ArrayList<>();
        SpePerson person = new SpePerson();
        person.setName("测试");
        person.setNum("1111-22222-333333");
        person.setType("电工");
        person.setToDate("2020年12月1日");
        spePersons.add(person);
        safeProduct.setSpePersons(spePersons);
        safeProduct.setSpePerson(true);

        return t;
    }
}
