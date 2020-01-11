package com.ts.server.safe.contract.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 合同服务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ConService {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("服务商编号")
    private String channelId;
    @ApiModelProperty("服务名称")
    private String name;
    @ApiModelProperty("合同编号")
    private String conId;
    @ApiModelProperty("合同名称")
    private String conName;
    @ApiModelProperty("服务企业编号")
    private String compId;
    @ApiModelProperty("服务企业名称")
    private String compName;
    @ApiModelProperty("负责人编号")
    private String leaId;
    @ApiModelProperty("负责人名称")
    private String leaName;
    @ApiModelProperty("状态")
    private Status status;
    @ApiModelProperty("修改时间")
    private Date updateTime;
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 合同服务状态
     */
    public enum Status {
        WAIT;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getConName() {
        return conName;
    }

    public void setConName(String conName) {
        this.conName = conName;
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
        ConService that = (ConService) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(channelId, that.channelId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(conId, that.conId) &&
                Objects.equals(conName, that.conName) &&
                Objects.equals(compId, that.compId) &&
                Objects.equals(compName, that.compName) &&
                Objects.equals(leaId, that.leaId) &&
                Objects.equals(leaName, that.leaName) &&
                status == that.status &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channelId, name, conId, conName, compId, compName, leaId, leaName, status, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("channelId", channelId)
                .append("name", name)
                .append("conId", conId)
                .append("conName", conName)
                .append("compId", compId)
                .append("compName", compName)
                .append("leaId", leaId)
                .append("leaName", leaName)
                .append("status", status)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
