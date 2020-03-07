package com.ts.server.safe.task.controller.man;

import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.task.controller.man.form.TaskItemSaveForm;
import com.ts.server.safe.task.controller.man.form.TaskResultForm;
import com.ts.server.safe.task.domain.TaskItem;
import com.ts.server.safe.task.service.TaskItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理任务内容
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("man/taskContent")
@Api(value = "man/taskContent", tags = "M-管理任务内容")
public class TaskItemManController {
    private final TaskItemService service;

    @Autowired
    public TaskItemManController(TaskItemService service) {
        this.service = service;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("保存检查内容")
    public ResultVo<TaskItem> save(@Valid @RequestBody TaskItemSaveForm form){
        return ResultVo.success(service.save(form.getTaskId(), form.getContentId()));
    }

    @PutMapping(value = "result", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("保存检查结果")
    public ResultVo<TaskItem> saveResult(@Valid @RequestBody TaskResultForm form){
        TaskItem.CheckResult result = TaskItem.CheckResult.valueOf(form.getCheckResult());
        TaskItem.DangerLevel level = TaskItem.DangerLevel.valueOf(form.getDangerLevel());

        return ResultVo.success(service.saveResult(form.getId(), result, level,
                form.getDanDescribe(), form.getDanSuggest(), form.getImages()));
    }

    @GetMapping(value = "{taskId}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询任务内容")
    public ResultVo<List<TaskItem>> query(@RequestParam String taskId){
        return ResultVo.success(service.query(taskId));
    }
}
