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
    @ApiModelProperty("检查任务编号")
    private String num;
    @ApiModelProperty("检查开始时间")
    private Date checkTimeFrom;
    @ApiModelProperty("检查结束时间")
    private Date checkTimeTo;
    @ApiModelProperty("检查人员集合")
    private List<CheckUser> checkUsers;
    @ApiModelProperty("行业分类集合")
    private List<CheckIndCtg> checkIndCtgs;
    @ApiModelProperty("复查以前查出的隐患")
    private boolean review;
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

    public List<CheckIndCtg> getCheckIndCtgs() {
        return checkIndCtgs;
    }

    public void setCheckIndCtgs(List<CheckIndCtg> checkIndCtgs) {
        this.checkIndCtgs = checkIndCtgs;
    }

    public boolean isReview() {
        return review;
    }

    public void setReview(boolean review) {
        this.review = review;
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
        return review == checkTask.review &&
                Objects.equals(id, checkTask.id) &&
                Objects.equals(channelId, checkTask.channelId) &&
                Objects.equals(serviceId, checkTask.serviceId) &&
                Objects.equals(serviceName, checkTask.serviceName) &&
                Objects.equals(compId, checkTask.compId) &&
                Objects.equals(compName, checkTask.compName) &&
                Objects.equals(num, checkTask.num) &&
                Objects.equals(checkTimeFrom, checkTask.checkTimeFrom) &&
                Objects.equals(checkTimeTo, checkTask.checkTimeTo) &&
                Objects.equals(checkUsers, checkTask.checkUsers) &&
                Objects.equals(checkIndCtgs, checkTask.checkIndCtgs) &&
                status == checkTask.status &&
                Objects.equals(updateTime, checkTask.updateTime) &&
                Objects.equals(createTime, checkTask.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channelId, serviceId, serviceName, compId, compName, num, checkTimeFrom, checkTimeTo, checkUsers, checkIndCtgs, review, status, updateTime, createTime);
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
                .append("num", num)
                .append("checkTimeFrom", checkTimeFrom)
                .append("checkTimeTo", checkTimeTo)
                .append("checkUsers", checkUsers)
                .append("checkIndCtgs", checkIndCtgs)
                .append("review", review)
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

    /**
     * 检查行业
     */
    public static class CheckIndCtg {
        @ApiModelProperty("行业分类")
        private String id;
        @ApiModelProperty("行业名称")
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
            CheckIndCtg indCtg = (CheckIndCtg) o;
            return Objects.equals(id, indCtg.id) &&
                    Objects.equals(name, indCtg.name);
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
