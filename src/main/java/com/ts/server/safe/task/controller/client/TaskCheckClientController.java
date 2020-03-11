package com.ts.server.safe.task.controller.client;

import com.ts.server.safe.controller.credential.ManCredential;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.security.CredentialContextUtils;
import com.ts.server.safe.security.annotation.ApiACL;
import com.ts.server.safe.task.controller.client.form.TaskItemResultForm;
import com.ts.server.safe.task.domain.TaskCheck;
import com.ts.server.safe.task.domain.TaskItem;
import com.ts.server.safe.task.service.TaskCheckService;
import com.ts.server.safe.task.service.TaskItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 客户端管理检查任务API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/client/task")
@ApiACL
@Api(value = "/client/task", tags = "C-管理检查任务API接口")
public class TaskCheckClientController {
    private final TaskCheckService service;
    private final TaskItemService itemService;

    @Autowired
    public TaskCheckClientController(TaskCheckService service, TaskItemService itemService) {
        this.service = service;
        this.itemService = itemService;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询检查任务")
    public ResultPageVo<TaskCheck> query(
            @RequestParam(required = false) @ApiParam(value = "公司名称") String compName,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String userId = getCredential().getId();
        return new ResultPageVo.Builder<>(page, rows, service.query(userId, compName, page * rows, rows))
                .count(isCount, () -> service.count(userId, compName))
                .build();
    }

    @PutMapping(value = "result", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("保存检查结果")
    public ResultVo<TaskItem> saveResult(@Valid @RequestBody TaskItemResultForm form){
        TaskItem.CheckResult result = TaskItem.CheckResult.valueOf(form.getCheckResult());
        TaskItem.DangerLevel level = TaskItem.DangerLevel.valueOf(form.getDangerLevel());

        return ResultVo.success(itemService.saveResult(form.getId(), result, level,
                form.getDanDescribe(), form.getDanSuggest(), form.getImages()));
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
