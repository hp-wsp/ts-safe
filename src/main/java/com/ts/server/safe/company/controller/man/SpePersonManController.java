package com.ts.server.safe.company.controller.man;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.company.controller.man.form.SpePersonSaveForm;
import com.ts.server.safe.company.controller.man.form.SpePersonUpdateForm;
import com.ts.server.safe.company.domain.CompInfo;
import com.ts.server.safe.company.domain.SpePerson;
import com.ts.server.safe.company.service.CompInfoService;
import com.ts.server.safe.company.service.SpePersonService;
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

/**
 * 管理特殊作业人员和证书
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/spePerson")
@Api(value = "/man/spePerson", tags = "M-管理特殊作业人员和证书")
public class SpePersonManController {
    private final SpePersonService service;
    private final CompInfoService compInfoService;

    @Autowired
    public SpePersonManController(SpePersonService service, CompInfoService compInfoService) {
        this.service = service;
        this.compInfoService = compInfoService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增特殊作业人员和证书")
    public ResultVo<SpePerson> save(@Valid @RequestBody SpePersonSaveForm form){
        validateCompId(form.getCompId(), "新增特殊作业人员和证书失败");
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
    @ApiOperation("修改特殊作业人员和证书")
    public ResultVo<SpePerson> update(@Valid @RequestBody SpePersonUpdateForm form){
        SpePerson t = service.get(form.getId());
        validateCompId(t.getCompId(), "新增修改特殊作业人员和证书失败");
        return ResultVo.success(service.update(form.toDomain()));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到特殊作业人员和证书")
    public ResultVo<SpePerson> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除特殊作业人员和证书")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        SpePerson t = service.get(id);
        validateCompId(t.getCompId(), "删除特殊作业人员和证书失败");
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询特殊作业人员和证书")
    public ResultPageVo<SpePerson> query(
            @ApiParam(value = "公司编号", required = true) @RequestParam(required = false)String compId,
            @ApiParam(value = "姓名") @RequestParam(required = false)String name,
            @ApiParam(value = "工种") @RequestParam(required = false)String type,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return new ResultPageVo.Builder<>(page, rows, service.query(compId, name, type,page * rows, rows))
                .count(isCount, () -> service.count(compId, name, type))
                .build();
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
