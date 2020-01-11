package com.ts.server.safe.company.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 企业生产信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CompProduct {
    @ApiModelProperty("编号")
    private Integer id;
    @ApiModelProperty("服务商编号")
    private String channelId;
    @ApiModelProperty("公司编号")
    private String compId;
    @ApiModelProperty("生产信息key")
    private String proKey;
    @ApiModelProperty("生产信息值")
    private Integer proValue;
    @ApiModelProperty("创建时间")
    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getProKey() {
        return proKey;
    }

    public void setProKey(String proKey) {
        this.proKey = proKey;
    }

    public Integer getProValue() {
        return proValue;
    }

    public void setProValue(Integer proValue) {
        this.proValue = proValue;
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
        CompProduct that = (CompProduct) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(channelId, that.channelId) &&
                Objects.equals(compId, that.compId) &&
                Objects.equals(proKey, that.proKey) &&
                Objects.equals(proValue, that.proValue) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channelId, compId, proKey, proValue, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("channelId", channelId)
                .append("compId", compId)
                .append("proKey", proKey)
                .append("proValue", proValue)
                .append("createTime", createTime)
                .toString();
    }
}
