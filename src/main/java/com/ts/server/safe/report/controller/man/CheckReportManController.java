package com.ts.server.safe.report.controller.man;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.domain.Channel;
import com.ts.server.safe.channel.service.ChannelService;
import com.ts.server.safe.company.domain.CompInfo;
import com.ts.server.safe.company.service.CompInfoService;
import com.ts.server.safe.controller.credential.ManCredential;
import com.ts.server.safe.controller.vo.HasVo;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.report.controller.man.form.CheckReportSaveForm;
import com.ts.server.safe.report.controller.man.form.CheckReportUpdateForm;
import com.ts.server.safe.report.domain.CheckReport;
import com.ts.server.safe.report.service.CheckReportService;
import com.ts.server.safe.security.CredentialContextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理检查报表API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/checkReport")
@Api(value = "/man/checkReport", tags = "M-检查报表管理")
public class CheckReportManController {
    private final CheckReportService service;
    private final ChannelService channelService;
    private final CompInfoService compInfoService;

    @Autowired
    public CheckReportManController(CheckReportService service,
                                    ChannelService channelService,
                                    CompInfoService compInfoService) {
        this.service = service;
        this.channelService = channelService;
        this.compInfoService = compInfoService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增检查报表")
    public ResultVo<CheckReport> save(CheckReportSaveForm form){
        String channelId = getCredential().getChannelId();
        CheckReport t = form.toDomain();
        Channel channel = channelService.get(channelId);
        t.setCompName(channel.getName());
        CompInfo compInfo = compInfoService.get(form.getCompId());
        if(!StringUtils.equals(compInfo.getChannelId(), channelId)){
            throw new BaseException("不能添加企业检查报表");
        }
        t.setCompName(compInfo.getName());
        return ResultVo.success(service.save(t));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("修改检查报表")
    public ResultVo<CheckReport> update(CheckReportUpdateForm form){
        CheckReport t = form.toDomain();
        String channelId = getCredential().getChannelId();
        CheckReport o = service.get(form.getId());
        if(!StringUtils.equals(o.getChannelId(), channelId)){
            throw new BaseException("不能修改检查报表");
        }
        CompInfo compInfo = compInfoService.get(form.getCompId());
        if(!StringUtils.equals(compInfo.getChannelId(), channelId)){
            throw new BaseException("不能添加企业检查报表");
        }
        t.setCompName(compInfo.getName());
        return ResultVo.success(service.update(t));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除检查报表")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        CheckReport o = service.get(id);
        String channelId = getCredential().getChannelId();
        if(!StringUtils.equals(o.getChannelId(), channelId)){
            throw new BaseException("不能删除检查报表");
        }
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到检查报表")
    public ResultVo<CheckReport> get(@PathVariable("id")String id){
        CheckReport o = service.get(id);
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
    public ResultPageVo<CheckReport> query(
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

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
