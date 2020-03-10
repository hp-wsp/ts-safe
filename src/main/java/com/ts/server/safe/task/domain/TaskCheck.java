package com.ts.server.safe.task.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 检查任务
 *
 * @author <a href="mailto:hhywangwei@gmail.om">WangWei</a>
 */
public class TaskCheck {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("服务商编号")
    private String channelId;
    @ApiModelProperty("合同编号")
    private String conId;
    @ApiModelProperty("服务编号")
    private String serviceId;
    @ApiModelProperty("服务名称")
    private String serviceName;
    @ApiModelProperty("公司编号")
    private String compId;
    @ApiModelProperty("公司名称")
    private String compName;
    @ApiModelProperty("检查任务编号")
    private String num;
    @ApiModelProperty("检查开始时间")
    private Date checkTimeFrom;
    @ApiModelProperty("检查结束时间")
    private Date checkTimeTo;
    @ApiModelProperty("检查人员集合")
    private List<CheckUser> checkUsers;
    @ApiModelProperty("复查以前查出的隐患")
    private boolean review;
    @ApiModelProperty("是否是初检")
    private boolean initial;
    @ApiModelProperty("负责人编号")
    private String leaId;
    @ApiModelProperty("负责人名称")
    private String leaName;
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

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
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

    public List<CheckUser> getCheckUsers() {
        return checkUsers;
    }

    public void setCheckUsers(List<CheckUser> checkUsers) {
        this.checkUsers = checkUsers;
    }

    public boolean isReview() {
        return review;
    }

    public void setReview(boolean review) {
        this.review = review;
    }

    public boolean isInitial() {
        return initial;
    }

    public void setInitial(boolean initial) {
        this.initial = initial;
    }

    public String getLeaId() {
        return leaId;
    }

    public void setLeaId(String leaId) {
        this.leaId = leaId;
    }

    public String getLeaName() {
        return leaName;
    }

    public void setLeaName(String leaName) {
        this.leaName = leaName;
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
        TaskCheck check = (TaskCheck) o;
        return review == check.review &&
                initial == check.initial &&
                Objects.equals(id, check.id) &&
                Objects.equals(channelId, check.channelId) &&
                Objects.equals(conId, check.conId) &&
                Objects.equals(serviceId, check.serviceId) &&
                Objects.equals(serviceName, check.serviceName) &&
                Objects.equals(compId, check.compId) &&
                Objects.equals(compName, check.compName) &&
                Objects.equals(num, check.num) &&
                Objects.equals(checkTimeFrom, check.checkTimeFrom) &&
                Objects.equals(checkTimeTo, check.checkTimeTo) &&
                Objects.equals(checkUsers, check.checkUsers) &&
                Objects.equals(leaId, check.leaId) &&
                Objects.equals(leaName, check.leaName) &&
                status == check.status &&
                Objects.equals(updateTime, check.updateTime) &&
                Objects.equals(createTime, check.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channelId, conId, serviceId, serviceName, compId, compName, num, checkTimeFrom, checkTimeTo, checkUsers, review, initial, leaId, leaName, status, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("channelId", channelId)
                .append("conId", conId)
                .append("serviceId", serviceId)
                .append("serviceName", serviceName)
                .append("compId", compId)
                .append("compName", compName)
                .append("num", num)
                .append("checkTimeFrom", checkTimeFrom)
                .append("checkTimeTo", checkTimeTo)
                .append("checkUsers", checkUsers)
                .append("review", review)
                .append("initial", initial)
                .append("leaId", leaId)
                .append("leaName", leaName)
                .append("status", status)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }

    /**
     * 检查用户
     */
    public static class CheckUser {
        @ApiModelProperty("检查员编号")
        private String id;
        @ApiModelProperty("检查员名")
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CheckUser checkUser = (CheckUser) o;
            return Objects.equals(id, checkUser.id) &&
                    Objects.equals(name, checkUser.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("id", id)
                    .append("name", name)
                    .toString();
        }
    }
}
