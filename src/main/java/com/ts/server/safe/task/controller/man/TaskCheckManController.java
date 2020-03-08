package com.ts.server.safe.task.controller.man;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.controller.credential.ManCredential;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.security.CredentialContextUtils;
import com.ts.server.safe.security.annotation.ApiACL;
import com.ts.server.safe.task.controller.man.form.TaskCheckSaveForm;
import com.ts.server.safe.task.controller.man.form.TaskCheckUpdateForm;
import com.ts.server.safe.task.controller.man.vo.CheckTaskVo;
import com.ts.server.safe.task.domain.TaskItem;
import com.ts.server.safe.task.domain.TaskCheck;
import com.ts.server.safe.task.service.TaskCheckService;
import com.ts.server.safe.task.service.TaskItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理检查任务API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/task")
@ApiACL
@Api(value = "/man/task", tags = "M-管理检查任务API接口")
public class TaskCheckManController {
    private final TaskCheckService service;
    private final TaskItemService contentService;

    @Autowired
    public TaskCheckManController(TaskCheckService service, TaskItemService contentService) {
        this.service = service;
        this.contentService = contentService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增检查任务")
    public ResultVo<CheckTaskVo> save(@Valid @RequestBody TaskCheckSaveForm form){
        TaskCheck t = form.toDomain();
        ManCredential credential = getCredential();
        t.setChannelId(credential.getChannelId());
        List<TaskItem> items = form.getItems().stream().map(this::buildItem).collect(Collectors.toList());
        TaskCheck n = service.save(t, items, credential.getId());
        List<TaskItem> contents = contentService.query(n.getId());
        return ResultVo.success(new CheckTaskVo(n, contents));
    }

    private TaskItem buildItem(TaskCheckSaveForm.ItemForm itemForm){
        TaskItem t = new TaskItem();
        t.setTypeId(itemForm.getTypeId());
        t.setTypeName(itemForm.getTypeName());
        t.setContent(itemForm.getContent());
        t.setConDetail(itemForm.getConDetail());
        t.setLawItem(itemForm.getLawItem());
        return t;
    }

    public ResultVo<CheckTaskVo> update(){
        return null;
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("修改检查任务")
    public ResultVo<CheckTaskVo> update(@Valid @RequestBody TaskCheckUpdateForm form){
        TaskCheck t = form.toDomain();
        ManCredential credential = getCredential();
        t.setChannelId(credential.getChannelId());
        List<TaskItem> items = form.getItems().stream().map(this::buildItem).collect(Collectors.toList());
        TaskCheck n = service.update(t, items, credential.getId());
        List<TaskItem> contents = contentService.query(n.getId());
        return ResultVo.success(new CheckTaskVo(n, contents));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到检查任务")
    public ResultVo<CheckTaskVo> get(@PathVariable("id")String id){
        TaskCheck t = service.get(id);
        List<TaskItem> contents = contentService.query(id);
        return ResultVo.success(new CheckTaskVo(t, contents));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除检查任务")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        TaskCheck t = service.get(id);
        if(!StringUtils.equals(t.getChannelId(), getCredential().getChannelId())){
            throw new BaseException("不能删除检查任务");
        }
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(value = "ofService", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询服务所属任务")
    public ResultVo<List<TaskCheck>> queryOfService(@RequestParam("serviceId") String serviceId){
        return ResultVo.success(service.queryByServiceId(serviceId));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询检查任务")
    public ResultPageVo<TaskCheck> query(
            @RequestParam(required = false) @ApiParam(value = "公司名称") String compName,
            @RequestParam(required = false) @ApiParam(value = "状态") String status,
            @RequestParam(required = false) @ApiParam(value = "检查开始时间") Date checkTimeFrom,
            @RequestParam(required = false) @ApiParam(value = "检查结束时间") Date checkTimeTo,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String channelId = getCredential().getChannelId();
        TaskCheck.Status statusObj = status == null? null: TaskCheck.Status.valueOf(status);
        return new ResultPageVo.Builder<>(page, rows, service.query(channelId, compName, statusObj, checkTimeFrom, checkTimeTo, page * rows, rows))
                .count(isCount, () -> service.count(channelId, compName, statusObj, checkTimeFrom, checkTimeTo))
                .build();
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
