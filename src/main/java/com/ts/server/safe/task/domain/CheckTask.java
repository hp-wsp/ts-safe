package com.ts.server.safe.task.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 检查任务
 *
 * @author <a href="mailto:hhywangwei@gmail.om">WangWei</a>
 */
public class CheckTask {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("服务商编号")
    private String channelId;
    @ApiModelProperty("服务编号")
    private String serviceId;
    @ApiModelProperty("服务名称")
    private String serviceName;
    @ApiModelProperty("公司编号")
    private String compId;
    @ApiModelProperty("公司名称")
    private String compName;
    @ApiModelProperty("检查开始时间")
    private Date checkTimeFrom;
    @ApiModelProperty("检查结束时间")
    private Date checkTimeTo;
    @ApiModelProperty("检查人员编号集合")
    private String[] checkUserIds;
    @ApiModelProperty("检查人员姓名集合")
    private String[] checkUserNames;
    @ApiModelProperty("检查表编号集合")
    private String[] checkTableIds;
    @ApiModelProperty("检查表名称集合")
    private String[] checkTableNames;
    @ApiModelProperty("检查任务状态")
    private Status status;
    @ApiModelProperty("修改时间")
    private Date updateTime;
    @ApiModelProperty("创建时间")
    private Date createTime;

    public enum Status {
        WAIT, FINISH
    }

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

    public Date getCheckTimeFrom() {
        return checkTimeFrom;
    }

    public void setCheckTimeFrom(Date checkTimeFrom) {
        this.checkTimeFrom = checkTimeFrom;
    }

    public Date getCheckTimeTo() {
        return checkTimeTo;
    }

    public void setCheckTimeTo(Date checkTimeTo) {
        this.checkTimeTo = checkTimeTo;
    }

    public String[] getCheckUserIds() {
        return checkUserIds;
    }

    public void setCheckUserIds(String[] checkUserIds) {
        this.checkUserIds = checkUserIds;
    }

    public String[] getCheckUserNames() {
        return checkUserNames;
    }

    public void setCheckUserNames(String[] checkUserNames) {
        this.checkUserNames = checkUserNames;
    }

    public String[] getCheckTableIds() {
        return checkTableIds;
    }

    public void setCheckTableIds(String[] checkTableIds) {
        this.checkTableIds = checkTableIds;
    }

    public String[] getCheckTableNames() {
        return checkTableNames;
    }

    public void setCheckTableNames(String[] checkTableNames) {
        this.checkTableNames = checkTableNames;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        CheckTask checkTask = (CheckTask) o;
        return Objects.equals(id, checkTask.id) &&
                Objects.equals(channelId, checkTask.channelId) &&
                Objects.equals(serviceId, checkTask.serviceId) &&
                Objects.equals(serviceName, checkTask.serviceName) &&
                Objects.equals(compId, checkTask.compId) &&
                Objects.equals(compName, checkTask.compName) &&
                Objects.equals(checkTimeFrom, checkTask.checkTimeFrom) &&
                Objects.equals(checkTimeTo, checkTask.checkTimeTo) &&
                Arrays.equals(checkUserIds, checkTask.checkUserIds) &&
                Arrays.equals(checkUserNames, checkTask.checkUserNames) &&
                Arrays.equals(checkTableIds, checkTask.checkTableIds) &&
                Arrays.equals(checkTableNames, checkTask.checkTableNames) &&
                status == checkTask.status &&
                Objects.equals(updateTime, checkTask.updateTime) &&
                Objects.equals(createTime, checkTask.createTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, channelId, serviceId, serviceName, compId, compName, checkTimeFrom, checkTimeTo, status, updateTime, createTime);
        result = 31 * result + Arrays.hashCode(checkUserIds);
        result = 31 * result + Arrays.hashCode(checkUserNames);
        result = 31 * result + Arrays.hashCode(checkTableIds);
        result = 31 * result + Arrays.hashCode(checkTableNames);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("channelId", channelId)
                .append("serviceId", serviceId)
                .append("serviceName", serviceName)
                .append("compId", compId)
                .append("compName", compName)
                .append("checkTimeFrom", checkTimeFrom)
                .append("checkTimeTo", checkTimeTo)
                .append("checkUserIds", checkUserIds)
                .append("checkUserNames", checkUserNames)
                .append("checkTableIds", checkTableIds)
                .append("checkTableNames", checkTableNames)
                .append("status", status)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
