package com.ts.server.safe.task.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 检查内容
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class TaskItem {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("任务编号")
    private String taskId;
    @ApiModelProperty("检查内容编号")
    private String contentId;
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
    @ApiModelProperty("检查结果")
    private CheckResult checkResult;
    @ApiModelProperty("隐患级别")
    private DangerLevel danLevel;
    @ApiModelProperty("隐患描述")
    private String danDescribe;
    @ApiModelProperty("整改建议")
    private String danSuggest;
    @ApiModelProperty("隐患图片")
    private String[] images;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("修改时间")
    private Date updateTime;
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 检查结果
     */
    public enum CheckResult {
        WAIT, PASS, NOT_PASS
    }

    /**
     * 隐患级别
     */
    public enum DangerLevel {
        NONE, COMMON, GREAT
    }

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

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
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

    public CheckResult getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(CheckResult checkResult) {
        this.checkResult = checkResult;
    }

    public DangerLevel getDanLevel() {
        return danLevel;
    }

    public void setDanLevel(DangerLevel danLevel) {
        this.danLevel = danLevel;
    }

    public String getDanDescribe() {
        return danDescribe;
    }

    public void setDanDescribe(String danDescribe) {
        this.danDescribe = danDescribe;
    }

    public String getDanSuggest() {
        return danSuggest;
    }

    public void setDanSuggest(String danSuggest) {
        this.danSuggest = danSuggest;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        TaskItem that = (TaskItem) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(taskId, that.taskId) &&
                Objects.equals(contentId, that.contentId) &&
                Objects.equals(typeId, that.typeId) &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(itemId, that.itemId) &&
                Objects.equals(itemName, that.itemName) &&
                Objects.equals(content, that.content) &&
                Objects.equals(conDetail, that.conDetail) &&
                Objects.equals(lawItem, that.lawItem) &&
                checkResult == that.checkResult &&
                danLevel == that.danLevel &&
                Objects.equals(danDescribe, that.danDescribe) &&
                Objects.equals(danSuggest, that.danSuggest) &&
                Arrays.equals(images, that.images) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, taskId, contentId, typeId, typeName, itemId, itemName, content, conDetail, lawItem, checkResult, danLevel, danDescribe, danSuggest, remark, updateTime, createTime);
        result = 31 * result + Arrays.hashCode(images);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("taskId", taskId)
                .append("contentId", contentId)
                .append("typeId", typeId)
                .append("typeName", typeName)
                .append("itemId", itemId)
                .append("itemName", itemName)
                .append("content", content)
                .append("conDetail", conDetail)
                .append("lawItem", lawItem)
                .append("checkResult", checkResult)
                .append("danLevel", danLevel)
                .append("danDescribe", danDescribe)
                .append("danSuggest", danSuggest)
                .append("images", images)
                .append("remark", remark)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
