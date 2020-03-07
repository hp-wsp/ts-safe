package com.ts.server.safe.report.controller.man.form;

import com.ts.server.safe.report.domain.IniReport;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增检查报表提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class IniReportSaveForm {
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
    @ApiModelProperty("检查开始时间")
    @NotBlank
    private String checkTimeFrom;
    @ApiModelProperty("检查结束时间")
    @NotBlank
    private String checkTimeTo;
    @ApiModelProperty(value = "编制说明", required = true)
    private IniReport.ReportDetail reportDetail;
    @ApiModelProperty(value = "受检企业基本情况", required = true)
    private IniReport.CompBaseInfo compBaseInfo;
    @ApiModelProperty(value = "安全生产社会化检查服务", required = true)
    private IniReport.SafeProduct safeProduct;
    @ApiModelProperty("报告份数说明")
    @NotBlank
    private String printDetail;

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

    public String getCheckTimeFrom() {
        return checkTimeFrom;
    }

    public void setCheckTimeFrom(String checkTimeFrom) {
        this.checkTimeFrom = checkTimeFrom;
    }

    public String getCheckTimeTo() {
        return checkTimeTo;
    }

    public void setCheckTimeTo(String checkTimeTo) {
        this.checkTimeTo = checkTimeTo;
    }

    public IniReport.ReportDetail getReportDetail() {
        return reportDetail;
    }

    public void setReportDetail(IniReport.ReportDetail reportDetail) {
        this.reportDetail = reportDetail;
    }

    public IniReport.CompBaseInfo getCompBaseInfo() {
        return compBaseInfo;
    }

    public void setCompBaseInfo(IniReport.CompBaseInfo compBaseInfo) {
        this.compBaseInfo = compBaseInfo;
    }

    public IniReport.SafeProduct getSafeProduct() {
        return safeProduct;
    }

    public void setSafeProduct(IniReport.SafeProduct safeProduct) {
        this.safeProduct = safeProduct;
    }

    public String getPrintDetail() {
        return printDetail;
    }

    public void setPrintDetail(String printDetail) {
        this.printDetail = printDetail;
    }

    public IniReport toDomain(){
        IniReport t = new IniReport();

        t.setTaskId(taskId);
        t.setTaskDetail(taskDetail);
        t.setCycleName(cycleName);
        t.setEntCompType(entCompType);
        t.setIndustry(industry);
        t.setEntScale(entScale);
        t.setArea(area);
        t.setCheckTimeFrom(checkTimeFrom);
        t.setCheckTimeTo(checkTimeTo);
        t.setReportDetail(reportDetail);
        t.setCompBaseInfo(compBaseInfo);
        t.setSafeProduct(safeProduct);
        t.setPrintDetail(printDetail);

        return t;
    }
}
