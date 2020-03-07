package com.ts.server.safe.task.controller.man.form;

import com.ts.server.safe.task.domain.TaskCheck;
import io.swagger.annotations.ApiModelProperty;

/**
 * 修改检查任务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class TaskCheckUpdateForm extends TaskCheckSaveForm {
    @ApiModelProperty("编号")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public TaskCheck toDomain() {
        TaskCheck t = super.toDomain();
        t.setId(id);

        return t;
    }
}
