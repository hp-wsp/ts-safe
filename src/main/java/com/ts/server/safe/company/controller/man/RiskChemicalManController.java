package com.ts.server.safe.company.controller.man;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.company.controller.man.form.RiskChemicalSaveForm;
import com.ts.server.safe.company.controller.man.form.RiskChemicalUpdateForm;
import com.ts.server.safe.company.domain.CompInfo;
import com.ts.server.safe.company.domain.RiskChemical;
import com.ts.server.safe.company.service.CompInfoService;
import com.ts.server.safe.company.service.RiskChemicalService;
import com.ts.server.safe.controller.credential.ManCredential;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.security.CredentialContextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/man/riskChemical")
@Api(value = "/man/riskChemical", tags = "M-危化品存储管理")
public class RiskChemicalManController {
    private final RiskChemicalService service;
    private final CompInfoService compInfoService;

    @Autowired
    public RiskChemicalManController(RiskChemicalService service, CompInfoService compInfoService) {
        this.service = service;
        this.compInfoService = compInfoService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增危化品存储")
    public ResultVo<RiskChemical> save(@Valid @RequestBody RiskChemicalSaveForm form){
        validateCompId(form.getCompId(), "添加危化品存储失败");
        return ResultVo.success(service.save(form.toDomain()));
    }

    private void validateCompId(String compId, String errMsg){
        CompInfo t = compInfoService.get(compId);
        String channelId = getCredential().getChannelId();
        if(!StringUtils.equals(t.getChannelId(), channelId)){
            throw new BaseException(errMsg);
        }
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("修改危化品存储")
    public ResultVo<RiskChemical> update(@Valid @RequestBody RiskChemicalUpdateForm form){
        RiskChemical t = service.get(form.getId());
        validateCompId(t.getCompId(), "修改危化品存储失败");
        return ResultVo.success(service.update(form.toDomain()));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("获取危化品存储")
    public ResultVo<RiskChemical> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除危化品存储")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        RiskChemical t = service.get(id);
        validateCompId(t.getCompId(), "不能删除危化品存储");
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询服务商")
    public ResultPageVo<RiskChemical> query(
            @ApiParam(value = "公司编号", required = true) @RequestParam(required = false)String compId,
            @ApiParam(value = "名称") @RequestParam(required = false)String name,
            @ApiParam(value = "cas") @RequestParam(required = false)String cas,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return new ResultPageVo.Builder<>(page, rows, service.query(compId, name, cas, page * rows, rows))
                .count(isCount, () -> service.count(compId, name, cas))
                .build();
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
