package com.ts.server.safe.base.controller.sys;

import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.base.controller.logger.UniIndCtgLogDetailBuilder;
import com.ts.server.safe.base.controller.sys.form.UniIndCtgSaveForm;
import com.ts.server.safe.base.controller.sys.form.UniIndCtgUpdateForm;
import com.ts.server.safe.base.domain.UniIndCtg;
import com.ts.server.safe.base.service.UniIndCtgService;
import com.ts.server.safe.logger.aop.annotation.EnableApiLogger;
import com.ts.server.safe.security.annotation.ApiACL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理监管分类API
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@ApiACL("ROLE_SYS")
@RequestMapping("/sys/indCtg")
@Api(value = "/sys/indCtg", tags = "S-管理行业分类API")
public class UniIndCtgSysController {
    private final UniIndCtgService service;

    @Autowired
    public UniIndCtgSysController(UniIndCtgService service) {
        this.service = service;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "新增监管分类", buildDetail = UniIndCtgLogDetailBuilder.SaveBuilder.class)
    @ApiOperation("新增监管分类")
    public ResultVo<UniIndCtg> save(@Valid @RequestBody UniIndCtgSaveForm form){
        return ResultVo.success(service.save(form.toDomain()));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "修改监管分类", buildDetail = UniIndCtgLogDetailBuilder.UpdateBuilder.class)
    @ApiOperation("修改监管分类")
    public ResultVo<UniIndCtg> update(@Valid @RequestBody UniIndCtgUpdateForm form){
        return ResultVo.success(service.update(form.toDomain()));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "删除监管分类", buildDetail = UniIndCtgLogDetailBuilder.DeleteBuilder.class)
    @ApiOperation("删除监管分类")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到监管分类")
    public ResultVo<UniIndCtg> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @GetMapping(value = "children/{parentId}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询下级监管分类")
    public ResultVo<List<UniIndCtg>> queryChildren(@PathVariable("parentId")String parentId){
        return ResultVo.success(service.queryChildren(parentId));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询监管分类")
    public ResultPageVo<UniIndCtg> query(
            @ApiParam(value = "上级分类编号") @RequestParam(required = false) String parentId,
            @ApiParam(value = "名称") @RequestParam(required = false) String name,
            @ApiParam(value = "num") @RequestParam(required = false) String num,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return new ResultPageVo.Builder<>(page, rows, service.query(parentId, name, num,page * rows, rows))
                .count(isCount, () -> service.count(parentId, name, num))
                .build();
    }
}
