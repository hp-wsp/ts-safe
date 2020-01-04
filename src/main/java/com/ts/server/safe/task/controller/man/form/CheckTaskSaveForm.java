package com.ts.server.safe.task.controller.man.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ts.server.safe.task.domain.CheckTask;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 新增检查任务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CheckTaskSaveForm {
    @NotBlank
    @ApiModelProperty(value = "服务编号", required = true)
    private String serviceId;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "检查开始时间", required = true)
    private Date checkTimeFrom;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "检查结束时间",required = true)
    private Date checkTimeTo;
    @NotEmpty
    @ApiModelProperty(value = "检查人员编号集合", required = true)
    private String[] checkUserIds;
    @NotEmpty
    @ApiModelProperty(value = "行业（检查表）编号集合", required = true)
    private String[] checkSupIds;
    @NotEmpty
    @ApiModelProperty(value = "检查内容编号")
    private String[] contentIds;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Date getCheckTimeFrom() {
        return checkTimeFrom;
    }

    public void setCheckTimeFrom(Date checkTimeFrom) {
        this.checkTimeFrom = checkTimeFrom;
    }

    public Date getCheckTimeTo() {
        return checkTimeTo;
    }

    public void setCheckTimeTo(Date checkTimeTo) {
        this.checkTimeTo = checkTimeTo;
    }

    public String[] getCheckUserIds() {
        return checkUserIds;
    }

    public void setCheckUserIds(String[] checkUserIds) {
        this.checkUserIds = checkUserIds;
    }

    public String[] getCheckSupIds() {
        return checkSupIds;
    }

    public void setCheckSupIds(String[] checkSupIds) {
        this.checkSupIds = checkSupIds;
    }

    public String[] getContentIds() {
        return contentIds;
    }

    public void setContentIds(String[] contentIds) {
        this.contentIds = contentIds;
    }

    public CheckTask toDomain(){
        CheckTask t = new CheckTask();

        t.setServiceId(serviceId);
        t.setCheckTimeFrom(checkTimeFrom);
        t.setCheckTimeTo(checkTimeTo);
        t.setCheckSupIds(checkSupIds);
        t.setCheckUserIds(checkUserIds);

        return t;
    }
}
