package com.ts.server.safe.company.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 特殊作业人员和证书
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SpePerson {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("企业编号")
    private String compId;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("工种")
    private String type;
    @ApiModelProperty("证书号")
    private String num;
    @ApiModelProperty("证书有效期开始日期")
    private String fromDate;
    @ApiModelProperty("证书有效期结束日期")
    private String toDate;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
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
        SpePerson spePerson = (SpePerson) o;
        return Objects.equals(id, spePerson.id) &&
                Objects.equals(compId, spePerson.compId) &&
                Objects.equals(name, spePerson.name) &&
                Objects.equals(type, spePerson.type) &&
                Objects.equals(num, spePerson.num) &&
                Objects.equals(fromDate, spePerson.fromDate) &&
                Objects.equals(toDate, spePerson.toDate) &&
                Objects.equals(createTime, spePerson.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, compId, name, type, num, fromDate, toDate, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("compId", compId)
                .append("name", name)
                .append("type", type)
                .append("num", num)
                .append("fromDate", fromDate)
                .append("toDate", toDate)
                .append("createTime", createTime)
                .toString();
    }
}
