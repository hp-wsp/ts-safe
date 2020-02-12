package com.ts.server.safe.base.controller.sys;

import com.ts.server.safe.base.controller.sys.form.UniRiskSaveForm;
import com.ts.server.safe.base.controller.sys.form.UniRiskUpdateForm;
import com.ts.server.safe.base.domain.UniRisk;
import com.ts.server.safe.base.service.UniRiskService;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
/**
 * 危险识别管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/risk")
@Api(value = "/sys/risk", tags = "S-危化品目录识别管理")
public class UniRiskSysController {
    private final UniRiskService service;

    @Autowired
    public UniRiskSysController(UniRiskService service) {
        this.service = service;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增危险识别")
    public ResultVo<UniRisk> save(@Valid @RequestBody UniRiskSaveForm form){
        return ResultVo.success(service.save(form.toDomain()));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("修改危险识别")
    public ResultVo<UniRisk> update(@Valid @RequestBody UniRiskUpdateForm form){
        return ResultVo.success(service.update(form.toDomain()));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除危险识别")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到危险识别")
    public ResultVo<UniRisk> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询危险识别")
    public ResultPageVo<UniRisk> query(@RequestParam(required = false)  @ApiParam("名称") String name,
                                       @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
                                       @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
                                       @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        List<UniRisk> data = service.query(name, page * rows, rows);
        return new ResultPageVo.Builder<>(page, rows, data)
                .count(isCount, () -> service.count(name))
                .build();

    }
}
