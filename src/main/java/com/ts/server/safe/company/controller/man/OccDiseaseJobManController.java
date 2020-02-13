package com.ts.server.safe.company.controller.man;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.company.controller.man.form.OccDiseaseJobSaveForm;
import com.ts.server.safe.company.controller.man.form.OccDiseaseJobUpdateForm;
import com.ts.server.safe.company.domain.CompInfo;
import com.ts.server.safe.company.domain.OccDiseaseJob;
import com.ts.server.safe.company.service.CompInfoService;
import com.ts.server.safe.company.service.OccDiseaseJobService;
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
 * 管理职业病岗位API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/occDiseaseJob")
@Api(value = "/man/occDiseaseJob", tags = "M-管理职业病岗位API接口")
public class OccDiseaseJobManController {
    private final OccDiseaseJobService service;
    private final CompInfoService compInfoService;

    @Autowired
    public OccDiseaseJobManController(OccDiseaseJobService service, CompInfoService compInfoService) {
        this.service = service;
        this.compInfoService = compInfoService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增职业病岗位")
    public ResultVo<OccDiseaseJob> save(@Valid @RequestBody OccDiseaseJobSaveForm form){
        OccDiseaseJob t = form.toDomain();
        validateCompId(t.getCompId(), "添加职业病岗位失败");
        return ResultVo.success(service.save(t));
    }

    private void validateCompId(String compId, String errMsg){
        CompInfo t = compInfoService.get(compId);
        String channelId = getCredential().getChannelId();
        if(!StringUtils.equals(t.getChannelId(), channelId)){
            throw new BaseException(errMsg);
        }
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("修改职业病岗位")
    public ResultVo<OccDiseaseJob> update(@Valid @RequestBody OccDiseaseJobUpdateForm form){
        OccDiseaseJob o = service.get(form.getId());
        validateCompId(o.getCompId(), "修改职业病岗位失败");
        return ResultVo.success(service.update(form.toDomain()));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到职业病岗位")
    public ResultVo<OccDiseaseJob> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除职业病岗位")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询职业病岗位")
    public ResultPageVo<OccDiseaseJob> query(
            @ApiParam(value = "公司编号", required = true) @RequestParam(required = false)String compId,
            @ApiParam(value = "岗位") @RequestParam(required = false)String job,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return new ResultPageVo.Builder<>(page, rows, service.query(compId, job, page * rows, rows))
                .count(isCount, () -> service.count(compId, job))
                .build();
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
