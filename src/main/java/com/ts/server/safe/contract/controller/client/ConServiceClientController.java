package com.ts.server.safe.contract.controller.client;

import com.ts.server.safe.contract.domain.ConService;
import com.ts.server.safe.contract.service.ConServiceService;
import com.ts.server.safe.controller.credential.ManCredential;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.security.CredentialContextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理合同服务API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping(value = "/client/conService")
@Api(value = "/client/conService", tags = "C-管理合同服务API接口")
public class ConServiceClientController {
    private final ConServiceService service;

    @Autowired
    public ConServiceClientController(ConServiceService service) {
        this.service = service;
    }

    @PutMapping(value = "accept/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("接受服务")
    public ResultVo<OkVo> accept(@PathVariable("id") String id){
        String userId = getCredential().getId();
        return ResultVo.success(new OkVo(service.accept(id, userId)));
    }

    @PutMapping(value = "refuse/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("接受服务")
    public ResultVo<OkVo> refuse(@PathVariable("id")String id){
        String userId = getCredential().getId();
        return ResultVo.success(new OkVo(service.refuse(id, userId)));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询合同服务")
    public ResultPageVo<ConService> query(
            @ApiParam(value = "服务名称") @RequestParam(required = false)String name,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String userId = getCredential().getId();
        return new ResultPageVo.Builder<>(page, rows, service.query(userId, name, page * rows, rows)).build();
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
