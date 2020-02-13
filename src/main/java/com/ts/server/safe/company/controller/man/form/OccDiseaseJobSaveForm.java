package com.ts.server.safe.company.controller.man.form;

import com.ts.server.safe.company.domain.OccDiseaseJob;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

/**
 * 新增职业病岗位
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class OccDiseaseJobSaveForm {
    @ApiModelProperty(value = "企业编号", required = true)
    @NotBlank
    private String compId;
    @ApiModelProperty(value = "作业岗位", required = true)
    @NotBlank
    private String job;
    @ApiModelProperty("作业方式")
    private String jobWay;
    @ApiModelProperty("作业形式")
    private String jobFormal;
    @ApiModelProperty(value = "存在职业病危害因素", required = true)
    @NotEmpty
    private String[] risks;
    @ApiModelProperty("备注")
    private String remark;

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getJobWay() {
        return jobWay;
    }

    public void setJobWay(String jobWay) {
        this.jobWay = jobWay;
    }

    public String getJobFormal() {
        return jobFormal;
    }

    public void setJobFormal(String jobFormal) {
        this.jobFormal = jobFormal;
    }

    public String[] getRisks() {
        return risks;
    }

    public void setRisks(String[] risks) {
        this.risks = risks;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public OccDiseaseJob toDomain(){
        OccDiseaseJob t = new OccDiseaseJob();

        t.setCompId(compId);
        t.setJob(job);
        t.setJobWay(jobWay);
        t.setJobFormal(jobFormal);
        t.setRisks(risks);
        t.setRemark(remark);

        return t;
    }
}
