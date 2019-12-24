package com.ts.server.safe.channel.controller.man;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.controller.man.form.ContractSaveForm;
import com.ts.server.safe.channel.controller.man.form.ContractUpdateForm;
import com.ts.server.safe.channel.domain.Contract;
import com.ts.server.safe.channel.service.ContractService;
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

    @Autowired
    public ContractManController(ContractService service) {
        this.service = service;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增合同")
    public ResultVo<Contract> save(@Valid @RequestBody ContractSaveForm form){
        String channelId = getCredential().getChannelId();
        Contract t = form.toDomain();
        t.setChannelId(channelId);

        return ResultVo.success(service.save(t));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("修改合同")
    public ResultVo<Contract> update(@Valid @RequestBody ContractUpdateForm form){
        String channelId = getCredential().getChannelId();
        Contract t = service.get(form.getId());
        if(!StringUtils.equals(channelId, t.getChannelId())){
            throw new BaseException("无权修改合同");
        }

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
            @ApiParam(value = "代理公司名称") @RequestParam(required = false)String delCompanyName,
            @ApiParam(value = "项目属地") @RequestParam(required = false) String proAddress,
            @ApiParam(value = "代理公司名称") @RequestParam(required = false) Integer delCompanyType,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String channelId = getCredential().getChannelId();
        return new ResultPageVo.Builder<>(page, rows, service.query(channelId, name, num, delCompanyName, proAddress, delCompanyType, page * rows, rows))
                .count(isCount, () -> service.count(channelId, name, num, delCompanyName, proAddress, delCompanyType))
                .build();
    }

    @GetMapping(value = "noneAlloc", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询没有分配合同")
    public ResultVo<List<Contract>> queryNoneAlloc(){
        String channelId = getCredential().getChannelId();
        return ResultVo.success(service.queryNoneAlloc(channelId));
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
