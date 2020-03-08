package com.ts.server.safe.task.controller.man;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.domain.Channel;
import com.ts.server.safe.channel.domain.Member;
import com.ts.server.safe.channel.service.ChannelService;
import com.ts.server.safe.channel.service.MemberService;
import com.ts.server.safe.common.utils.HttpUtils;
import com.ts.server.safe.company.domain.CompInfo;
import com.ts.server.safe.company.service.CompInfoService;
import com.ts.server.safe.company.service.CompProductService;
import com.ts.server.safe.company.service.OccDiseaseJobService;
import com.ts.server.safe.company.service.RiskChemicalService;
import com.ts.server.safe.contract.domain.ConService;
import com.ts.server.safe.contract.domain.Contract;
import com.ts.server.safe.contract.service.ConServiceService;
import com.ts.server.safe.contract.service.ContractService;
import com.ts.server.safe.controller.credential.ManCredential;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.task.controller.man.form.TaskReportInitSaveForm;
import com.ts.server.safe.task.domain.InitReportContent;
import com.ts.server.safe.task.domain.TaskReport;
import com.ts.server.safe.task.service.ReportService;
import com.ts.server.safe.security.CredentialContextUtils;
import com.ts.server.safe.task.domain.TaskCheck;
import com.ts.server.safe.task.domain.TaskItem;
import com.ts.server.safe.task.service.TaskCheckService;
import com.ts.server.safe.task.service.TaskItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理检查报表API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/taskReport")
@Api(value = "/man/taskReport", tags = "M-初检报告管理")
public class TaskReportManController {
    private final ReportService service;
    private final ChannelService channelService;
    private final TaskCheckService checkService;
    private final ConServiceService conService;
    private final ContractService contractService;
    private final CompInfoService compInfoService;
    private final MemberService memberService;
    private final CompProductService productService;
    private final RiskChemicalService riskChemicalService;
    private final TaskItemService taskContentService;
    private final OccDiseaseJobService occDiseaseJobService;

    @Autowired
    public TaskReportManController(ReportService service, ChannelService channelService,
                                   TaskCheckService checkService, ConServiceService conService,
                                   ContractService contractService, CompInfoService compInfoService,
                                   MemberService memberService, CompProductService productService,
                                   RiskChemicalService riskChemicalService, TaskItemService taskContentService,
                                   OccDiseaseJobService occDiseaseJobService) {

        this.service = service;
        this.channelService = channelService;
        this.checkService = checkService;
        this.conService = conService;
        this.contractService = contractService;
        this.compInfoService = compInfoService;
        this.memberService = memberService;
        this.productService = productService;
        this.riskChemicalService = riskChemicalService;
        this.taskContentService = taskContentService;
        this.occDiseaseJobService = occDiseaseJobService;
    }

    @PostMapping(value = "initial", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("保存初检报表")
    public ResultVo<TaskReport<?>> saveInitial(@Valid @RequestBody TaskReportInitSaveForm form){
        TaskReport<InitReportContent> r = new TaskReport<>();
        r.setTaskId(form.getTaskId());
        r.setContent(form.getContent());
        return ResultVo.success(service.save(r));
    }

    @GetMapping(value = "{taskId}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到检查报表")
    public ResultVo<TaskReport<?>> get(@PathVariable("taskId")String taskId){
        TaskCheck check = checkService.get(taskId);
        String channelId = getCredential().getChannelId();
        if(!StringUtils.equals(check.getChannelId(), channelId)){
            throw new BaseException("改检查报表不存在");
        }

        if(service.has(taskId)){
            TaskReport<?> o = service.get(taskId);

            return ResultVo.success(o);
        }

        if(check.isInitial()){
            TaskReport<InitReportContent> report = initReportInitial(check);
            return ResultVo.success(service.save(report));
        }

        //TODO 核查报告
        return null;
    }

    private TaskReport<InitReportContent> initReportInitial(TaskCheck check){
        String channelId = getCredential().getChannelId();
        if(!StringUtils.equals(check.getChannelId(), channelId)){
            throw new BaseException("不能初始检查报表");
        }

        TaskReport<InitReportContent> report = new TaskReport<>();
        report.setServiceId(check.getServiceId());
        report.setServiceName(check.getServiceName());
        report.setCompId(check.getCompId());
        report.setCompName(check.getCompName());
        report.setChannelId(check.getChannelId());
        report.setInitial(check.isInitial());
        report.setTaskId(check.getId());

        InitReportContent content = new InitReportContent();
        Channel channel = channelService.get(channelId);
        content.setChannelName(channel.getName());
        content.setCompName(check.getCompName());
        DateFormat dateForm = new SimpleDateFormat("yyyy年MM月dd日");
        content.setCheckTimeFrom(dateForm.format(check.getCheckTimeFrom()));
        content.setCheckTimeTo(dateForm.format(check.getCheckTimeTo()));
        CompInfo compInfo = compInfoService.get(check.getCompId());
        content.setEntScale(compInfo.getEntScale());
        if(!compInfo.getIndCtgs().isEmpty()){
            content.setIndustry(compInfo.getIndCtgs().get(0).getName());
        }
        ConService conSer = conService.get(check.getServiceId());
        Contract contract = contractService.get(conSer.getConId());
        content.setEntCompType(contract.getEntCompType());
        InitReportContent.ReportDetail detail = new InitReportContent.ReportDetail();
        detail.setEntCompName(check.getCompName());
        detail.setConNum(contract.getNum());
        detail.setCycleContent(buildCycleContent(contract, dateForm));
        detail.setChanName(channel.getName());
        detail.setChanAddress(channel.getAddress());
        detail.setChanBusScope(channel.getBusScope());
        detail.setChanPhone(channel.getPhone());
        detail.setChanMobile(channel.getMobile());
        InitReportContent.PersonInfo conPerson = buildChanContact(compInfo);
        detail.setChanContact(conPerson);
        detail.setLeader(buildPerson(conSer.getLeaId()));
        detail.setAuditPerson(conPerson);
        detail.setReportPerson(buildPerson(getCredential().getId()));
        content.setReportDetail(detail);

        InitReportContent.CompBaseInfo compBaseInfo = new InitReportContent.CompBaseInfo();
        compBaseInfo.setCompName(compInfo.getName());
        compBaseInfo.setRegAddress(compInfo.getRegAddress());
        compBaseInfo.setCreditCode(compInfo.getCreditCode());
        compBaseInfo.setConPerson(conPerson);
        compBaseInfo.setSafePerson(buildSafePerson(compInfo));
        compBaseInfo.setContractPerson(buildContactPerson(compInfo));
        compBaseInfo.setCompProfile(compInfo.getProfile());
        compBaseInfo.setProducts(productService.query(compInfo.getId()));
        compBaseInfo.setRiskChemicals(riskChemicalService.queryByCompId(compInfo.getId()));
        content.setCompBaseInfo(compBaseInfo);

        InitReportContent.SafeProduct safeProduct = new InitReportContent.SafeProduct();
        List<TaskItem> taskContents = taskContentService.query(check.getId());
        safeProduct.setBaseContents(filterTaskItems(taskContents, "001"));
        safeProduct.setSceneContents(filterTaskItems(taskContents, "002"));
        safeProduct.setOccDiseaseJobs(occDiseaseJobService.queryCompany(check.getCompId()));
        content.setSafeProduct(safeProduct);

        report.setContent(content);

        return report;
    }

    private InitReportContent.PersonInfo buildChanContact(CompInfo info) {
        InitReportContent.PersonInfo t = new InitReportContent.PersonInfo();
        t.setName(info.getLegalPerson());
        t.setMobile(info.getLegalMobile());
        t.setPhone(info.getLegalPhone());

        return t;
    }

    private InitReportContent.PersonInfo buildPerson(String memberId) {
        Member m = memberService.get(memberId);
        InitReportContent.PersonInfo t = new InitReportContent.PersonInfo();
        t.setName(m.getName());
        t.setMobile(m.getMobile());
        t.setPhone(m.getPhone());

        return t;
    }

    private InitReportContent.PersonInfo buildSafePerson(CompInfo info){
        InitReportContent.PersonInfo t = new InitReportContent.PersonInfo();
        t.setName(info.getSafePerson());
        t.setMobile(info.getSafeMobile());
        t.setPhone(info.getSafePhone());

        return t;
    }

    private InitReportContent.PersonInfo buildContactPerson(CompInfo info){
        InitReportContent.PersonInfo t = new InitReportContent.PersonInfo();
        t.setName(info.getContact());
        t.setMobile(info.getMobile());
        t.setPhone(info.getPhone());

        return t;
    }

    private String buildCycleContent(Contract t, DateFormat dataForm){
        return dataForm.format(t.getSerConDateFrom()) + " 至 " + dataForm.format(t.getSerConDateTo());
    }

    private List<InitReportContent.CheckItem> filterTaskItems(List<TaskItem> items, String typeId){
        return items.stream().filter(e -> StringUtils.equals(e.getTypeId(), typeId)).
                filter(e -> e.getCheckResult() == TaskItem.CheckResult.NOT_PASS).
                map(e -> {
                    InitReportContent.CheckItem t = new InitReportContent.CheckItem();
                    t.setDanDescribe(t.getDanDescribe());
                    t.setDanSuggest(t.getDanSuggest());
                    t.setImages(e.getImages());
                    t.setRemark(t.getRemark());
                    return t;
                }).collect(Collectors.toList());
    }


    @GetMapping(value = "export")
    public void export(@ApiParam("报表编号") @RequestParam("taskId") String taskId,
                       @ApiParam(value = "报表格式:WPS和MS", defaultValue = "WPS") @RequestParam(value = "format", required = false, defaultValue = "WPS") String format,
                       HttpServletResponse response){

        TaskCheck check = checkService.get(taskId);
        if(!StringUtils.equalsIgnoreCase(check.getChannelId(), getCredential().getChannelId())){
            throw new BaseException("没有导出权限");
        }

        HttpUtils.setContentDisposition(response, check.getCompName(), "docx");
        try(OutputStream outputStream = response.getOutputStream()){
            service.exportReport(outputStream, StringUtils.upperCase(format), taskId);
            outputStream.flush();
        }catch (IOException e){
            throw new BaseException("导出失败");
        }
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
