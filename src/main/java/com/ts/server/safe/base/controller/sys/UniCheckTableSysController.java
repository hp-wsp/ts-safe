package com.ts.server.safe.base.controller.sys;

import com.ts.server.safe.base.controller.logger.UniCheckTableLogDetailBuilder;
import com.ts.server.safe.base.controller.sys.form.UniCheckTableSaveForm;
import com.ts.server.safe.base.controller.sys.form.UniCheckTableUpdateForm;
import com.ts.server.safe.base.domain.UniCheckItem;
import com.ts.server.safe.base.domain.UniCheckContent;
import com.ts.server.safe.base.service.UniCheckItemService;
import com.ts.server.safe.base.service.UniCheckContentService;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.logger.aop.annotation.EnableApiLogger;
import com.ts.server.safe.security.annotation.ApiACL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理检查表API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/checkTable")
@ApiACL(value = "ROLE_SYS")
@Api(value = "/sys/checkTable", tags = "S-管理检查表")
public class UniCheckTableSysController {
    private final UniCheckContentService service;
    private final UniCheckItemService itemService;

    @Autowired
    public UniCheckTableSysController(UniCheckContentService service, UniCheckItemService itemService) {
        this.service = service;
        this.itemService = itemService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "新增检查表", buildDetail = UniCheckTableLogDetailBuilder.SaveBuilder.class)
    @ApiOperation("新增检查表")
    public ResultVo<UniCheckContent> save(@Valid @RequestBody UniCheckTableSaveForm form){
        UniCheckContent t = form.toDomain();
        setItem(t);
        return ResultVo.success(service.save(t));
    }

    private void setItem(UniCheckContent t){
        UniCheckItem item = itemService.get(t.getItemId());
        t.setTypeId(item.getTypeId());
        t.setTypeName(item.getTypeName());
        t.setItemName(item.getName());
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "修改检查表", buildDetail = UniCheckTableLogDetailBuilder.UpdateBuilder.class)
    @ApiOperation("修改检查表")
    public ResultVo<UniCheckContent> update(@Valid @RequestBody UniCheckTableUpdateForm form){
        UniCheckContent t = form.toDomain();
        setItem(t);
        return ResultVo.success(service.update(t));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到检查表")
    public ResultVo<UniCheckContent> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "删除检查表", buildDetail = UniCheckTableLogDetailBuilder.DeleteBuilder.class)
    @ApiOperation("删除检查表")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询检查表")
    public ResultPageVo<UniCheckContent> query(
            @ApiParam(value = "检查类型") @RequestParam(required = false) String typeName,
            @ApiParam(value = "检查项目") @RequestParam(required = false) String itemName,
            @ApiParam(value = "检查内容") @RequestParam(required = false) String content,
            @ApiParam(value = "法律内容") @RequestParam(required = false) String lawItem,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return new ResultPageVo.Builder<>(page, rows, service.query(typeName, itemName, content, lawItem, page * rows, rows))
                .count(isCount, () -> service.count(typeName, itemName, content, lawItem))
                .build();
    }
}
