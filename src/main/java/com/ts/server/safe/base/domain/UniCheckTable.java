package com.ts.server.safe.base.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 统一检查表
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniCheckTable {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("检查类别")
    private String checkType;
    @ApiModelProperty("检查项目")
    private String checkItem;
    @ApiModelProperty("检查内容")
    private String content;
    @ApiModelProperty("检查内容明细")
    private String conDetail;
    @ApiModelProperty("法律依据")
    private String lawItem;
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

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConDetail() {
        return conDetail;
    }

    public void setConDetail(String conDetail) {
        this.conDetail = conDetail;
    }

    public String getLawItem() {
        return lawItem;
    }

    public void setLawItem(String lawItem) {
        this.lawItem = lawItem;
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
        UniCheckTable that = (UniCheckTable) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(checkType, that.checkType) &&
                Objects.equals(checkItem, that.checkItem) &&
                Objects.equals(content, that.content) &&
                Objects.equals(conDetail, that.conDetail) &&
                Objects.equals(lawItem, that.lawItem) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, checkType, checkItem, content, conDetail, lawItem, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("checkType", checkType)
                .append("checkItem", checkItem)
                .append("content", content)
                .append("conDetail", conDetail)
                .append("lawItem", lawItem)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
