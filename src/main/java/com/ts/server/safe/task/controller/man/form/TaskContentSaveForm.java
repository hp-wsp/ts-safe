package com.ts.server.safe.task.controller.man.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增任务内容提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class TaskContentSaveForm {
    @ApiModelProperty(value = "检查任务编号", required = true)
    @NotBlank
    private String taskId;
    @ApiModelProperty(value = "检查内容编号", required = true)
    @NotBlank
    private String contentId;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }
}
