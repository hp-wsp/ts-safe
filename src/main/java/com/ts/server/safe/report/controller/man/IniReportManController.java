package com.ts.server.safe.report.controller.man;

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
import com.ts.server.safe.controller.vo.HasVo;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.report.controller.man.form.IniReportSaveForm;
import com.ts.server.safe.report.controller.man.form.IniReportUpdateForm;
import com.ts.server.safe.report.domain.IniReport;
import com.ts.server.safe.report.domain.ReportItem;
import com.ts.server.safe.report.service.IniReportService;
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
@RequestMapping("/man/checkReport")
@Api(value = "/man/checkReport", tags = "M-初检报告管理")
public class IniReportManController {
    private final IniReportService service;
    private final ChannelService channelService;
    private final TaskCheckService taskService;
    private final ConServiceService conService;
    private final ContractService contractService;
    private final CompInfoService compInfoService;
    private final MemberService memberService;
    private final CompProductService productService;
    private final RiskChemicalService riskChemicalService;
    private final TaskItemService taskContentService;
    private final OccDiseaseJobService occDiseaseJobService;

    @Autowired
    public IniReportManController(IniReportService service, ChannelService channelService,
                                  TaskCheckService taskService, ConServiceService conService,
                                  ContractService contractService, CompInfoService compInfoService,
                                  MemberService memberService, CompProductService productService,
                                  RiskChemicalService riskChemicalService, TaskItemService taskContentService,
                                  OccDiseaseJobService occDiseaseJobService) {

        this.service = service;
        this.channelService = channelService;
        this.taskService = taskService;
        this.conService = conService;
        this.contractService = contractService;
        this.compInfoService = compInfoService;
        this.memberService = memberService;
        this.productService = productService;
        this.riskChemicalService = riskChemicalService;
        this.taskContentService = taskContentService;
        this.occDiseaseJobService = occDiseaseJobService;
    }

    @GetMapping(value = "init/{taskId}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("初始化检查报表")
    public ResultVo<IniReport> init(@PathVariable("taskId")String taskId){
        TaskCheck task = taskService.get(taskId);
        String channelId = getCredential().getChannelId();
        if(!StringUtils.equals(task.getChannelId(), channelId)){
            throw new BaseException("不能初始检查报表");
        }

        IniReport report = new IniReport();
        report.setChannelId(task.getChannelId());
        Channel channel = channelService.get(channelId);
        report.setChannelName(channel.getName());
        report.setCompId(task.getCompId());
        report.setCompName(task.getCompName());
        report.setServiceId(task.getServiceId());
        report.setServiceName(task.getServiceName());
        report.setTaskId(taskId);
        DateFormat dateForm = new SimpleDateFormat("yyyy年MM月dd日");
        report.setTaskDetail(task.getNum() + " 检查日期 " + dateForm.format(task.getCheckTimeFrom()));
        report.setCheckTimeFrom(dateForm.format(task.getCheckTimeFrom()));
        report.setCheckTimeTo(dateForm.format(task.getCheckTimeTo()));
        CompInfo compInfo = compInfoService.get(task.getCompId());
        report.setEntScale(compInfo.getEntScale());
        if(!compInfo.getIndCtgs().isEmpty()){
            report.setIndustry(compInfo.getIndCtgs().get(0).getName());
        }
        ConService conSer = conService.get(task.getServiceId());
        Contract contract = contractService.get(conSer.getConId());
        report.setEntCompType(contract.getEntCompType());
        IniReport.ReportDetail detail = new IniReport.ReportDetail();
        detail.setEntCompName(task.getCompName());
        detail.setConNum(contract.getNum());
        detail.setCycleContent(buildCycleContent(contract, dateForm));
        detail.setChanName(channel.getName());
        detail.setChanAddress(channel.getAddress());
        detail.setChanBusScope(channel.getBusScope());
        detail.setChanPhone(channel.getPhone());
        detail.setChanMobile(channel.getMobile());
        IniReport.PersonInfo conPerson = buildChanContact(compInfo);
        detail.setChanContact(conPerson);
        detail.setLeader(buildPerson(conSer.getLeaId()));
        detail.setAuditPerson(conPerson);
        detail.setReportPerson(buildPerson(getCredential().getId()));
        report.setReportDetail(detail);

        IniReport.CompBaseInfo compBaseInfo = new IniReport.CompBaseInfo();
        compBaseInfo.setCompName(compInfo.getName());
        compBaseInfo.setRegAddress(compInfo.getRegAddress());
        compBaseInfo.setCreditCode(compInfo.getCreditCode());
        compBaseInfo.setConPerson(conPerson);
        compBaseInfo.setSafePerson(buildSafePerson(compInfo));
        compBaseInfo.setContractPerson(buildContactPerson(compInfo));
        compBaseInfo.setCompProfile(compInfo.getProfile());
        compBaseInfo.setProducts(productService.query(compInfo.getId()));
        compBaseInfo.setRiskChemicals(riskChemicalService.queryByCompId(compInfo.getId()));
        report.setCompBaseInfo(compBaseInfo);

        IniReport.SafeProduct safeProduct = new IniReport.SafeProduct();
        List<TaskItem> taskContents = taskContentService.query(taskId);
        safeProduct.setBaseContents(filterTaskContents(taskContents, "001"));
        safeProduct.setSceneContents(filterTaskContents(taskContents, "002"));
        safeProduct.setOccDiseaseJobs(occDiseaseJobService.queryCompany(task.getCompId()));
        report.setSafeProduct(safeProduct);

        return ResultVo.success(report);
    }

    private IniReport.PersonInfo buildChanContact(CompInfo info) {
        IniReport.PersonInfo t = new IniReport.PersonInfo();
        t.setName(info.getLegalPerson());
        t.setMobile(info.getLegalMobile());
        t.setPhone(info.getLegalPhone());

        return t;
    }

    private IniReport.PersonInfo buildPerson(String memberId) {
        Member m = memberService.get(memberId);
        IniReport.PersonInfo t = new IniReport.PersonInfo();
        t.setName(m.getName());
        t.setMobile(m.getMobile());
        t.setPhone(m.getPhone());

        return t;
    }

    private IniReport.PersonInfo buildSafePerson(CompInfo info){
        IniReport.PersonInfo t = new IniReport.PersonInfo();
        t.setName(info.getSafePerson());
        t.setMobile(info.getSafeMobile());
        t.setPhone(info.getSafePhone());

        return t;
    }

    private IniReport.PersonInfo buildContactPerson(CompInfo info){
        IniReport.PersonInfo t = new IniReport.PersonInfo();
        t.setName(info.getContact());
        t.setMobile(info.getMobile());
        t.setPhone(info.getPhone());

        return t;
    }

    private String buildCycleContent(Contract t, DateFormat dataForm){
        return dataForm.format(t.getSerConDateFrom()) + " 至 " + dataForm.format(t.getSerConDateTo());
    }

    private List<ReportItem> filterTaskContents(List<TaskItem> contents, String typeId){
        return contents.stream().filter(e -> StringUtils.equals(e.getTypeId(), typeId)).map(e -> {
            ReportItem t = new ReportItem();

            //TODO 完成转换
//            t.set
            return t;
        }).collect(Collectors.toList());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增检查报表")
    public ResultVo<IniReport> save(@Valid @RequestBody IniReportSaveForm form){
        String channelId = getCredential().getChannelId();
        IniReport t = form.toDomain();
        Channel channel = channelService.get(channelId);
        t.setChannelId(channel.getId());
        t.setChannelName(channel.getName());
        t.setCompName(channel.getName());
        TaskCheck task = taskService.get(form.getTaskId());
        if(!StringUtils.equals(task.getChannelId(), channelId)){
            throw new BaseException("不能添加企业检查报表");
        }
        t.setCompId(task.getCompId());
        t.setCompName(task.getCompName());
        t.setServiceId(task.getServiceId());
        t.setServiceName(task.getServiceName());

        return ResultVo.success(service.save(t));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("修改检查报表")
    public ResultVo<IniReport> update(@Valid @RequestBody IniReportUpdateForm form){
        IniReport t = form.toDomain();
        String channelId = getCredential().getChannelId();
        IniReport o = service.get(form.getId());
        if(!StringUtils.equals(o.getChannelId(), channelId)){
            throw new BaseException("不能修改检查报表");
        }
        TaskCheck task = taskService.get(form.getTaskId());
        if(!StringUtils.equals(task.getChannelId(), channelId)){
            throw new BaseException("不能修改企业检查报表");
        }
        t.setCompId(task.getCompId());
        t.setCompName(task.getCompName());
        t.setServiceId(task.getServiceId());
        t.setServiceName(task.getServiceName());
        return ResultVo.success(service.update(t));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除检查报表")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        IniReport o = service.get(id);
        String channelId = getCredential().getChannelId();
        if(!StringUtils.equals(o.getChannelId(), channelId)){
            throw new BaseException("不能删除检查报表");
        }
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到检查报表")
    public ResultVo<IniReport> get(@PathVariable("id")String id){
        IniReport o = service.get(id);
        String channelId = getCredential().getChannelId();
        if(!StringUtils.equals(o.getChannelId(), channelId)){
            throw new BaseException("改检查报表不存在");
        }
        return ResultVo.success(o);
    }

    @GetMapping(value = "hasCycleName", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("是否有检查周期名称")
    public ResultVo<HasVo> hasCycleName(@RequestParam("cycleName")String cycleName){
        String channelId = getCredential().getChannelId();
        boolean has = service.hasCycleName(channelId, cycleName);
        return ResultVo.success(HasVo.noneData(has));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询检查报表")
    public ResultPageVo<IniReport> query(
            @ApiParam(value = "企业名称") @RequestParam(required = false)String compName,
            @ApiParam(value = "检查周期名称") @RequestParam(required = false)String cycleName,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String channelId = getCredential().getChannelId();
        return new ResultPageVo.Builder<>(page, rows, service.query(channelId, compName, cycleName, page * rows, rows))
                .count(isCount, () -> service.count(channelId, compName, cycleName))
                .build();
    }

    @GetMapping(value = "export")
    public void export(@ApiParam("报表编号") @RequestParam("id") String id,
                       @ApiParam(value = "报表格式:WPS和MS", defaultValue = "WPS") @RequestParam(value = "format", required = false, defaultValue = "WPS") String format,
                       HttpServletResponse response){

        IniReport report = service.get(id);
        if(!StringUtils.equalsIgnoreCase(report.getChannelId(), getCredential().getChannelId())){
            throw new BaseException("没有导出权限");
        }

        HttpUtils.setContentDisposition(response, report.getCycleName(), "docx");
        try(OutputStream outputStream = response.getOutputStream()){
            service.exportReport(outputStream, StringUtils.upperCase(format), id);
            outputStream.flush();
        }catch (IOException e){
            throw new BaseException("导出失败");
        }
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
