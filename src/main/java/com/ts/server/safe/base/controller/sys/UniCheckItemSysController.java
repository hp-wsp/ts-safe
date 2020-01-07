package com.ts.server.safe.base.controller.sys;

import com.ts.server.safe.base.controller.sys.form.UniCheckItemSaveForm;
import com.ts.server.safe.base.controller.sys.form.UniCheckItemUpdateForm;
import com.ts.server.safe.base.domain.UniCheckItem;
import com.ts.server.safe.base.domain.UniCheckType;
import com.ts.server.safe.base.service.UniCheckItemService;
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
 * 管理检查项目API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@ApiACL("ROLE_SYS")
@RequestMapping("/sys/checkItem")
@Api(value = "/sys/checkItem", tags = "S-管理检查项目API接口")
public class UniCheckItemSysController {
    private final UniCheckItemService service;
    private final UniCheckTypeService typeService;

    @Autowired
    public UniCheckItemSysController(UniCheckItemService service, UniCheckTypeService typeService) {
        this.service = service;
        this.typeService = typeService;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("新增检查项目")
    public ResultVo<UniCheckItem> save(@Valid @RequestBody UniCheckItemSaveForm form){
        UniCheckItem t = form.toDomain();
        setTypeName(t);
        return ResultVo.success(service.save(t));
    }

    private void setTypeName(UniCheckItem t){
        UniCheckType type = typeService.get(t.getTypeId());
        t.setTypeName(type.getName());
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE, consumes = APPLICATION_JSON_VALUE)
    @ApiOperation("修改检查项目")
    public ResultVo<UniCheckItem> update(@Valid @RequestBody UniCheckItemUpdateForm form){
        UniCheckItem t = form.toDomain();
        setTypeName(t);
        return ResultVo.success(service.update(t));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除检查项目")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("获取检查项目")
    public ResultVo<UniCheckItem> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询检查类别")
    public ResultPageVo<UniCheckItem> query(
            @ApiParam(value = "检查类别名称") @RequestParam(required = false) String typeName,
            @ApiParam(value = "名称") @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return new ResultPageVo.Builder<>(page, rows, service.query(typeName, name, page * rows, rows))
                .count(isCount, () -> service.count(typeName, name))
                .build();
    }
}
