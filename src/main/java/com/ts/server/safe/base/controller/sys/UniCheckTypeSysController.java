package com.ts.server.safe.base.controller.sys;

import com.ts.server.safe.base.controller.sys.form.UniCheckTypeSaveForm;
import com.ts.server.safe.base.controller.sys.form.UniCheckTypeUpdateForm;
import com.ts.server.safe.base.domain.UniCheckType;
import com.ts.server.safe.base.service.UniCheckTypeService;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.security.annotation.ApiACL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理检查类别API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@ApiACL("ROLE_SYS")
@RequestMapping("/sys/checkType")
@Api(value = "/sys/checkType", tags = "S-管理检查类别API接口")
public class UniCheckTypeSysController {
    private final UniCheckTypeService service;

    @Autowired
    public UniCheckTypeSysController(UniCheckTypeService service) {
        this.service = service;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("新增检查类别")
    public ResultVo<UniCheckType> save(@Valid @RequestBody UniCheckTypeSaveForm form){
        return ResultVo.success(service.save(form.toDomain()));
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("修改检查类别")
    public ResultVo<UniCheckType> update(@Valid @RequestBody UniCheckTypeUpdateForm form){
        return ResultVo.success(service.update(form.toDomain()));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("获取检查类别")
    public ResultVo<UniCheckType> get(@PathVariable("id") String id){
        return ResultVo.success(service.get(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除检查类别")
    public ResultVo<OkVo> delete(@PathVariable("id") String id){
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询检查类别")
    public ResultPageVo<UniCheckType> query(
            @ApiParam(value = "名称") @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return new ResultPageVo.Builder<>(page, rows, service.query(name, page * rows, rows))
                .count(isCount, () -> service.count(name))
                .build();
    }
}
