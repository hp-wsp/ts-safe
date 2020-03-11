package com.ts.server.safe.contract.controller.man;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.contract.controller.man.form.ConServiceSaveForm;
import com.ts.server.safe.contract.controller.man.form.ConServiceUpdateForm;
import com.ts.server.safe.contract.controller.man.vo.ConServiceVo;
import com.ts.server.safe.contract.domain.ConService;
import com.ts.server.safe.contract.domain.ConServiceItem;
import com.ts.server.safe.contract.service.ConServiceService;
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

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理合同服务API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping(value = "/man/conService")
@Api(value = "/man/conService", tags = "M-管理合同服务API接口")
public class ConServiceManController {
    private final ConServiceService service;

    @Autowired
    public ConServiceManController(ConServiceService service) {
        this.service = service;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增合同服务")
    public ResultVo<ConServiceVo> save(@Valid @RequestBody ConServiceSaveForm form){
        List<ConServiceItem> items = form.getItems().stream().map(this::buildConService).collect(Collectors.toList());
        String channelId = getCredential().getChannelId();
        ConService t = service.save(channelId, form.getName(), form.getConId(), form.getLeaId(), form.getCompId(), items);
        return ResultVo.success(new ConServiceVo(t, service.queryItems(t.getId())));
    }

    private ConServiceItem buildConService(ConServiceSaveForm.ConServiceItemForm itemForm){
        ConServiceItem t = new ConServiceItem();

        t.setItemId(itemForm.getItemId());
        t.setItemName(itemForm.getItemName());
        t.setItemValue(itemForm.getItemValue());

        return t;
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("修复合同服务")
    public ResultVo<ConServiceVo> update(@Valid @RequestBody ConServiceUpdateForm form){
        ConService o = service.get(form.getId());
        String channelId = getCredential().getChannelId();
        if(!StringUtils.equals(o.getChannelId(), channelId)){
            throw new BaseException("不能修改合同服务");
        }
        List<ConServiceItem> items = form.getItems().stream().map(this::buildConService).collect(Collectors.toList());
        ConService t = service.update(form.getId(), form.getName(), form.getConId(), form.getLeaId(), form.getCompId(), items);
        return ResultVo.success(new ConServiceVo(t, service.queryItems(t.getId())));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除合同服务")
    public ResultVo<OkVo> delete(@PathVariable("id") String id){
        String channelId = getCredential().getChannelId();
        return ResultVo.success(new OkVo(service.delete(id, channelId)));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    public ResultVo<ConServiceVo> get(@PathVariable("id") String id){
        return ResultVo.success(new ConServiceVo(service.get(id), service.queryItems(id)));
    }

    @GetMapping(value = "ofCompany", produces = APPLICATION_JSON_VALUE)
    public ResultVo<List<ConService>> queryOfCompany(@RequestParam("compId") String compId){
        return ResultVo.success(service.queryByCompId(compId));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询合同服务")
    public ResultPageVo<ConService> query(
            @ApiParam(value = "服务名称") @RequestParam(required = false)String name,
            @ApiParam(value = "公司名称") @RequestParam(required = false)String compName,
            @ApiParam(value = "状态") @RequestParam(required = false)String status,
            @ApiParam(value = "合同编号") @RequestParam(required = false) String conId,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String channelId = getCredential().getChannelId();
        ConService.Status statusObj = StringUtils.isBlank(status)? null: ConService.Status.valueOf(status);
        return new ResultPageVo.Builder<>(page, rows, service.query(channelId, name, compName, statusObj, conId, page * rows, rows))
                .count(isCount, () -> service.count(channelId, name, compName, statusObj, conId))
                .build();
    }

    @GetMapping(value = "owner", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("我的查询合同")
    public ResultPageVo<ConService> queryOwner(@ApiParam(value = "服务名称") @RequestParam(required = false)String name,
                                               @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows,
                                               @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                               @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount){

        String userId = getCredential().getId();
        return new ResultPageVo.Builder<>(page, rows, service.query(userId, name, page * rows, rows))
                .count(isCount, () -> service.count(userId, name))
                .build();
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
