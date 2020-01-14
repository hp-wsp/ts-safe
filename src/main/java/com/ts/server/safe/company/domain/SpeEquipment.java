package com.ts.server.safe.company.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 特种设备
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SpeEquipment {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("企业编号")
    private String compId;
    @ApiModelProperty("设备名称")
    private String name;
    @ApiModelProperty("出厂编号")
    private String num;
    @ApiModelProperty("设备隐患")
    private String devDanger;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDevDanger() {
        return devDanger;
    }

    public void setDevDanger(String devDanger) {
        this.devDanger = devDanger;
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
        SpeEquipment that = (SpeEquipment) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(compId, that.compId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(num, that.num) &&
                Objects.equals(devDanger, that.devDanger) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, compId, name, num, devDanger, remark, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("compId", compId)
                .append("name", name)
                .append("num", num)
                .append("devDanger", devDanger)
                .append("remark", remark)
                .append("createTime", createTime)
                .toString();
    }
}
