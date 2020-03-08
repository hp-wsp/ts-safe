package com.ts.server.safe.task.controller.man.form;

import com.ts.server.safe.task.domain.InitReportContent;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增检查报表提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class TaskReportInitSaveForm {
    @ApiModelProperty(value = "任务编号", required = true)
    @NotBlank
    private String taskId;
    @ApiModelProperty(value = "报表内容", required = true)
    private InitReportContent content;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public InitReportContent getContent() {
        return content;
    }

    public void setContent(InitReportContent content) {
        this.content = content;
    }
}
