package com.ts.server.safe.task.controller.man.form;

import com.ts.server.safe.task.domain.CheckTask;
import io.swagger.annotations.ApiModelProperty;

/**
 * 修改检查任务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CheckTaskUpdateForm extends CheckTaskSaveForm {
    @ApiModelProperty("编号")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public CheckTask toDomain() {
        CheckTask t = super.toDomain();
        t.setId(id);

        return t;
    }
}
