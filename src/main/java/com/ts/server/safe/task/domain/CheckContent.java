package com.ts.server.safe.task.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 检查内容
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CheckContent {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("任务编号")
    private String taskId;
    @ApiModelProperty("检查表编号")
    private String tableId;
    @ApiModelProperty("检查类别编号")
    private String typeId;
    @ApiModelProperty("检查类别名称")
    private String typeName;
    @ApiModelProperty("检查项目编号")
    private String itemId;
    @ApiModelProperty("检查项目名称")
    private String itemName;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTableId() {
        return tableId;
    }

    public void setTableId(String tableId) {
        this.tableId = tableId;
    }

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
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
        CheckContent that = (CheckContent) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(taskId, that.taskId) &&
                Objects.equals(tableId, that.tableId) &&
                Objects.equals(typeId, that.typeId) &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(itemName, that.itemName) &&
                Objects.equals(content, that.content) &&
                Objects.equals(conDetail, that.conDetail) &&
                Objects.equals(lawItem, that.lawItem) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, taskId, tableId, typeId, typeName, itemId, itemName, content, conDetail, lawItem, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("taskId", taskId)
                .append("tableId", tableId)
                .append("typeId", typeId)
                .append("typeName", typeName)
                .append("itemId", itemId)
                .append("itemName", itemName)
                .append("content", content)
                .append("conDetail", conDetail)
                .append("lawItem", lawItem)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
