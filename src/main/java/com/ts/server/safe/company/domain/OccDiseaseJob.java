package com.ts.server.safe.company.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 职业病岗位
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class OccDiseaseJob {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("企业编号")
    private String compId;
    @ApiModelProperty("作业岗位")
    private String job;
    @ApiModelProperty("作业方式")
    private String jobWay;
    @ApiModelProperty("作业形式")
    private String jobFormal;
    @ApiModelProperty("存在职业病危害因素")
    private String[] risks;
    @ApiModelProperty("备注")
    private String remark;
    @ApiModelProperty("创建时间")
    private Date createTime;

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
        OccDiseaseJob that = (OccDiseaseJob) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(compId, that.compId) &&
                Objects.equals(job, that.job) &&
                Objects.equals(jobWay, that.jobWay) &&
                Objects.equals(jobFormal, that.jobFormal) &&
                Arrays.equals(risks, that.risks) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, compId, job, jobWay, jobFormal, remark, createTime);
        result = 31 * result + Arrays.hashCode(risks);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("compId", compId)
                .append("job", job)
                .append("jobWay", jobWay)
                .append("jobFormal", jobFormal)
                .append("risks", risks)
                .append("remark", remark)
                .append("createTime", createTime)
                .toString();
    }
}
