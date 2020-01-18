package com.ts.server.safe.report.controller.man.form;

import com.ts.server.safe.report.domain.CheckReport;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增检查报表提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CheckReportSaveForm {
    @ApiModelProperty(value = "任务编号", required = true)
    @NotBlank
    private String taskId;
    @ApiModelProperty(value = "任务描述", required = true)
    @NotBlank
    private String taskDetail;
    @ApiModelProperty(value = "检查周期", required = true)
    @NotBlank
    private String cycleName;
    @ApiModelProperty(value = "委托单位形式", required = true)
    private int entCompType;
    @ApiModelProperty(value = "所属行业")
    private String industry;
    @ApiModelProperty(value = "企业规模", required = true)
    private int entScale;
    @ApiModelProperty(value = "所属区域")
    private String area;
    @ApiModelProperty(value = "检查日期", required = true)
    @NotBlank
    private String checkDate;
    @ApiModelProperty(value = "编制说明", required = true)
    private CheckReport.BzDetail bzDetail;
    @ApiModelProperty(value = "受检企业基本情况", required = true)
    private CheckReport.CompBaseInfo compBaseInfo;
    @ApiModelProperty(value = "安全生产社会化检查服务", required = true)
    private CheckReport.SafeProduct safeProduct;

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    public String getCycleName() {
        return cycleName;
    }

    public void setCycleName(String cycleName) {
        this.cycleName = cycleName;
    }

    public int getEntCompType() {
        return entCompType;
    }

    public void setEntCompType(int entCompType) {
        this.entCompType = entCompType;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getEntScale() {
        return entScale;
    }

    public void setEntScale(int entScale) {
        this.entScale = entScale;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public CheckReport.BzDetail getBzDetail() {
        return bzDetail;
    }

    public void setBzDetail(CheckReport.BzDetail bzDetail) {
        this.bzDetail = bzDetail;
    }

    public CheckReport.CompBaseInfo getCompBaseInfo() {
        return compBaseInfo;
    }

    public void setCompBaseInfo(CheckReport.CompBaseInfo compBaseInfo) {
        this.compBaseInfo = compBaseInfo;
    }

    public CheckReport.SafeProduct getSafeProduct() {
        return safeProduct;
    }

    public void setSafeProduct(CheckReport.SafeProduct safeProduct) {
        this.safeProduct = safeProduct;
    }

    public CheckReport toDomain(){
        CheckReport t = new CheckReport();

        t.setTaskId(taskId);
        t.setTaskDetail(taskDetail);
        t.setCycleName(cycleName);
        t.setEntCompType(entCompType);
        t.setIndustry(industry);
        t.setEntScale(entScale);
        t.setArea(area);
        t.setCheckDate(checkDate);
        t.setBzDetail(bzDetail);
        t.setCompBaseInfo(compBaseInfo);
        t.setSafeProduct(safeProduct);

        return t;
    }
}
