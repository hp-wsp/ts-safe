package com.ts.server.safe.contract.controller.man;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.company.domain.CompInfo;
import com.ts.server.safe.company.service.CompInfoService;
import com.ts.server.safe.contract.controller.man.form.ContractCompleteForm;
import com.ts.server.safe.contract.controller.man.form.ContractSaveForm;
import com.ts.server.safe.contract.controller.man.form.ContractUpdateForm;
import com.ts.server.safe.contract.domain.Contract;
import com.ts.server.safe.contract.service.ContractService;
import com.ts.server.safe.controller.credential.ManCredential;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.security.CredentialContextUtils;
import com.ts.server.safe.security.annotation.ApiACL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理合同API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@ApiACL({"ROLE_MAN_SYS"})
@RequestMapping("/man/contract")
@Api(value = "/man/contract", tags = "M-管理合同API接口")
public class ContractManController {
    private final ContractService service;
    private final CompInfoService compService;

    @Autowired
    public ContractManController(ContractService service, CompInfoService compService) {
        this.service = service;
        this.compService = compService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增合同")
    public ResultVo<Contract> save(@Valid @RequestBody ContractSaveForm form){
        String channelId = getCredential().getChannelId();
        Contract t = form.toDomain();
        t.setSerCompanies(buildSerCompanies(form.getSerCompIds()));
        t.setChannelId(channelId);

        return ResultVo.success(service.save(t));
    }

    private List<Contract.SerCompany> buildSerCompanies(List<String> ids){
        return ids.stream().map(e -> {
            CompInfo info = compService.get(e);
            Contract.SerCompany s = new Contract.SerCompany();
            s.setId(info.getId());
            s.setName(info.getName());
            return s;
        }).collect(Collectors.toList());
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("修改合同")
    public ResultVo<Contract> update(@Valid @RequestBody ContractUpdateForm form){
        String channelId = getCredential().getChannelId();
        Contract t = service.get(form.getId());
        if(!StringUtils.equals(channelId, t.getChannelId())){
            throw new BaseException("无权修改合同");
        }
        t.setSerCompanies(buildSerCompanies(form.getSerCompIds()));
        return ResultVo.success(service.update(form.toDomain()));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除合同")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        Contract t = service.get(id);
        String channelId = getCredential().getChannelId();
        if(!StringUtils.equals(channelId, t.getChannelId())){
            throw new BaseException("无权修改合同");
        }
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @PutMapping(value = "complete", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("合同是否完成")
    public ResultVo<OkVo> complete(@Valid @RequestBody ContractCompleteForm form){
        return ResultVo.success(new OkVo(service.updateComplete(form.getId(), form.getComplete())));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到合同")
    public ResultVo<Contract> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询合同")
    public ResultPageVo<Contract> query(
            @ApiParam(value = "合同名称") @RequestParam(required = false)String name,
            @ApiParam(value = "合同编号") @RequestParam(required = false)String num,
            @ApiParam(value = "代理公司名称") @RequestParam(required = false)String entCompName,
            @ApiParam(value = "项目属地") @RequestParam(required = false) String proAddress,
            @ApiParam(value = "代理公司名称") @RequestParam(required = false) Integer entCompType,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String channelId = getCredential().getChannelId();
        return new ResultPageVo.Builder<>(page, rows, service.query(channelId, name, num, entCompName, proAddress, entCompType, page * rows, rows))
                .count(isCount, () -> service.count(channelId, name, num, entCompName, proAddress, entCompType))
                .build();
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
