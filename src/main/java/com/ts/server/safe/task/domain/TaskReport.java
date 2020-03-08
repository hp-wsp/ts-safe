package com.ts.server.safe.task.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 检查报告
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class TaskReport<T> {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "服务商编号")
    private String channelId;
    @ApiModelProperty(value = "企业编号")
    private String compId;
    @ApiModelProperty(value = "企业名称")
    private String compName;
    @ApiModelProperty("服务系统编号")
    private String serviceId;
    @ApiModelProperty("服务名称")
    private String serviceName;
    @ApiModelProperty("检查任务编号")
    private String taskId;
    @ApiModelProperty("是否是初检报告")
    private boolean initial;
    @ApiModelProperty("报表内容")
    private T content;
    @ApiModelProperty("修改时间")
    private Date updateTime;
    @ApiModelProperty("创建时间")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        TaskReport<?> report = (TaskReport<?>) o;
        return initial == report.initial &&
                Objects.equals(id, report.id) &&
                Objects.equals(channelId, report.channelId) &&
                Objects.equals(compId, report.compId) &&
                Objects.equals(compName, report.compName) &&
                Objects.equals(serviceId, report.serviceId) &&
                Objects.equals(serviceName, report.serviceName) &&
                Objects.equals(taskId, report.taskId) &&
                Objects.equals(content, report.content) &&
                Objects.equals(updateTime, report.updateTime) &&
                Objects.equals(createTime, report.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channelId, compId, compName, serviceId, serviceName, taskId, initial, content, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("channelId", channelId)
                .append("compId", compId)
                .append("compName", compName)
                .append("serviceId", serviceId)
                .append("serviceName", serviceName)
                .append("taskId", taskId)
                .append("initial", initial)
                .append("content", content)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
