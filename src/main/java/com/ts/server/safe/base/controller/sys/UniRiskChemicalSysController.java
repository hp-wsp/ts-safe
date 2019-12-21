package com.ts.server.safe.base.controller.sys;

import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.base.controller.logger.RiskChemistryLogDetailBuilder;
import com.ts.server.safe.base.controller.sys.form.UniRiskChemicalSaveForm;
import com.ts.server.safe.base.controller.sys.form.UniRiskChemicalUpdateForm;
import com.ts.server.safe.base.domain.UniRiskChemical;
import com.ts.server.safe.base.service.UniRiskChemicalService;
import com.ts.server.safe.logger.aop.annotation.EnableApiLogger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理危化品目录API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/riskChemical")
@Api(value = "/sys/riskChemical", tags = "S-管理危化品目录API接口")
public class UniRiskChemicalSysController {
    private final UniRiskChemicalService service;

    @Autowired
    public UniRiskChemicalSysController(UniRiskChemicalService service) {
        this.service = service;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "新增危化品目录", buildDetail = RiskChemistryLogDetailBuilder.SaveBuilder.class)
    @ApiOperation("新增危化品目录")
    public ResultVo<UniRiskChemical> save(@Valid @RequestBody UniRiskChemicalSaveForm form){
        return ResultVo.success(service.save(form.toDomain()));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "修改危化品目录", buildDetail = RiskChemistryLogDetailBuilder.UpdateBuilder.class)
    @ApiOperation("修改危化品目录")
    public ResultVo<UniRiskChemical> update(@Valid @RequestBody UniRiskChemicalUpdateForm form){
        return ResultVo.success(service.update(form.toDomain()));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "删除危化品目录", buildDetail = RiskChemistryLogDetailBuilder.DeleteBuilder.class)
    @ApiOperation("删除危化品目录")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到危化品目录")
    public ResultVo<UniRiskChemical> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询危化品目录")
    public ResultPageVo<UniRiskChemical> query(
            @ApiParam(value = "名称") @RequestParam(required = false) String name,
            @ApiParam(value = "别名") @RequestParam(required = false) String alias,
            @ApiParam(value = "CAS编号") @RequestParam(required = false) String cas,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return new ResultPageVo.Builder<>(page, rows, service.query(name, alias, cas,page * rows, rows))
                .count(isCount, () -> service.count(name, alias, cas))
                .build();
    }
}
