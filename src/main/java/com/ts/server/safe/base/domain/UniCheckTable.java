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
    @ApiModelProperty("检查类型编号")
    private String supId;
    @ApiModelProperty("检查名称")
    private String name;
    @ApiModelProperty("检查内容")
    private String content;
    @ApiModelProperty("检查内容明细")
    private String conDetail;
    @ApiModelProperty("法律名")
    private String lawName;
    @ApiModelProperty("法律条目")
    private String lawItem;
    @ApiModelProperty("法律内容")
    private String lawContent;
    @ApiModelProperty("检查类型")
    private int checkType;
    @ApiModelProperty("显示排序")
    private int showOrder;
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

    public String getSupId() {
        return supId;
    }

    public void setSupId(String supId) {
        this.supId = supId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getLawName() {
        return lawName;
    }

    public void setLawName(String lawName) {
        this.lawName = lawName;
    }

    public String getLawItem() {
        return lawItem;
    }

    public void setLawItem(String lawItem) {
        this.lawItem = lawItem;
    }

    public String getLawContent() {
        return lawContent;
    }

    public void setLawContent(String lawContent) {
        this.lawContent = lawContent;
    }

    public int getCheckType() {
        return checkType;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    public int getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(int showOrder) {
        this.showOrder = showOrder;
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
        return checkType == that.checkType &&
                showOrder == that.showOrder &&
                Objects.equals(id, that.id) &&
                Objects.equals(supId, that.supId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(content, that.content) &&
                Objects.equals(conDetail, that.conDetail) &&
                Objects.equals(lawName, that.lawName) &&
                Objects.equals(lawItem, that.lawItem) &&
                Objects.equals(lawContent, that.lawContent) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, supId, name, content, conDetail, lawName, lawItem, lawContent, checkType, showOrder, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("supId", supId)
                .append("name", name)
                .append("content", content)
                .append("conDetail", conDetail)
                .append("lawName", lawName)
                .append("lawItem", lawItem)
                .append("lawContent", lawContent)
                .append("checkType", checkType)
                .append("showOrder", showOrder)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
