package com.ts.server.safe.task.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 核查报表内容
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class InsReportContent {
    @ApiModelProperty(value = "服务商名称")
    private String channelName;
    @ApiModelProperty(value = "企业名称")
    private String compName;
    @ApiModelProperty("所属区域")
    private String area;
    @ApiModelProperty("所属行业")
    private String industry;
    @ApiModelProperty("委托单位形式")
    private int entCompType;
    @ApiModelProperty("上次检查时间")
    private String lastTime;
    @ApiModelProperty("本次检查时间")
    private String currentTime;
    @ApiModelProperty("创建时间")
    private Date createTime;

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getEntCompType() {
        return entCompType;
    }

    public void setEntCompType(int entCompType) {
        this.entCompType = entCompType;
    }

    public String getLastTime() {
        return lastTime;
    }

    public void setLastTime(String lastTime) {
        this.lastTime = lastTime;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
