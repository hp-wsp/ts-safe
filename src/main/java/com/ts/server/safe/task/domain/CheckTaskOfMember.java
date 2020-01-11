package com.ts.server.safe.task.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 检查员的检查任务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CheckTaskOfMember {
    @ApiModelProperty("编号")
    private Integer id;
    @ApiModelProperty("检查员编号")
    private String memId;
    @ApiModelProperty("任务编号")
    private String taskId;
    @ApiModelProperty("编号")
    private CheckTask.Status status;
    @ApiModelProperty("创建时间")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMemId() {
        return memId;
    }

    public void setMemId(String memId) {
        this.memId = memId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public CheckTask.Status getStatus() {
        return status;
    }

    public void setStatus(CheckTask.Status status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CheckTaskOfMember that = (CheckTaskOfMember) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(memId, that.memId) &&
                Objects.equals(taskId, that.taskId) &&
                status == that.status &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, memId, taskId, status, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("memId", memId)
                .append("taskId", taskId)
                .append("status", status)
                .append("createTime", createTime)
                .toString();
    }
}
