package com.ts.server.safe.report.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 报表检查项目
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ReportItem {
    @ApiModelProperty("id")
    private String id;
    @ApiModelProperty("公司编号")
    private String compId;
    @ApiModelProperty("报表编号")
    private String reportId;
    @ApiModelProperty("报表类型")
    private ReportType reportType;
    @ApiModelProperty("检查类别编号")
    private String typeId;
    @ApiModelProperty("检查类别名称")
    private String typeName;
    @ApiModelProperty("检查内容")
    private String content;
    @ApiModelProperty("检查内容明细")
    private String detail;
    @ApiModelProperty("法律依据")
    private String lawItem;
    @ApiModelProperty("检查结果")
    private CheckResult checkResult;
    @ApiModelProperty("隐患描述")
    private String danDescribe;
    @ApiModelProperty("整改建议")
    private String danSuggest;
    @ApiModelProperty("隐患图片")
    private String[] images;
    @ApiModelProperty("复查结果")
    private String insResult;
    @ApiModelProperty("创建时间")
    private Date createTime;

    /**
     * 报表类型
     */
    public enum ReportType {
        INITIAL, INSPECT
    }

    /**
     * 检查结果
     */
    public enum CheckResult {
        WAIT, PASS, NOT_PASS
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public ReportType getReportType() {
        return reportType;
    }

    public void setReportType(ReportType reportType) {
        this.reportType = reportType;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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

    public String getInsResult() {
        return insResult;
    }

    public void setInsResult(String insResult) {
        this.insResult = insResult;
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
        ReportItem that = (ReportItem) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(compId, that.compId) &&
                Objects.equals(reportId, that.reportId) &&
                reportType == that.reportType &&
                Objects.equals(typeId, that.typeId) &&
                Objects.equals(typeName, that.typeName) &&
                Objects.equals(content, that.content) &&
                Objects.equals(detail, that.detail) &&
                Objects.equals(lawItem, that.lawItem) &&
                checkResult == that.checkResult &&
                Objects.equals(danDescribe, that.danDescribe) &&
                Objects.equals(danSuggest, that.danSuggest) &&
                Arrays.equals(images, that.images) &&
                Objects.equals(insResult, that.insResult) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, compId, reportId, reportType, typeId, typeName, content, detail, lawItem, checkResult, danDescribe, danSuggest, insResult, createTime);
        result = 31 * result + Arrays.hashCode(images);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("compId", compId)
                .append("reportId", reportId)
                .append("reportType", reportType)
                .append("typeId", typeId)
                .append("typeName", typeName)
                .append("content", content)
                .append("detail", detail)
                .append("lawItem", lawItem)
                .append("checkResult", checkResult)
                .append("danDescribe", danDescribe)
                .append("danSuggest", danSuggest)
                .append("images", images)
                .append("insResult", insResult)
                .append("createTime", createTime)
                .toString();
    }
}
