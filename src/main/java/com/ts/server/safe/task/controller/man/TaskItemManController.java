package com.ts.server.safe.task.controller.man;

import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.task.controller.man.form.TaskItemResultForm;
import com.ts.server.safe.task.domain.TaskItem;
import com.ts.server.safe.task.service.TaskItemService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理检查项目
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("man/taskItem")
@Api(value = "man/taskItem", tags = "M-管理检查项目")
public class TaskItemManController {
    private final TaskItemService service;

    @Autowired
    public TaskItemManController(TaskItemService service) {
        this.service = service;
    }

    @PutMapping(value = "result", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("保存检查结果")
    public ResultVo<TaskItem> saveResult(@Valid @RequestBody TaskItemResultForm form){
        TaskItem.CheckResult result = TaskItem.CheckResult.valueOf(form.getCheckResult());
        TaskItem.DangerLevel level = TaskItem.DangerLevel.valueOf(form.getDangerLevel());

        return ResultVo.success(service.saveResult(form.getId(), result, level,
                form.getDanDescribe(), form.getDanSuggest(), form.getImages()));
    }
}
