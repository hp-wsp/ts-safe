package com.ts.server.safe.base.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 风险识别
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniRisk {
    private String id;
    private String name;
    private String remark;
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        UniRisk uniRisk = (UniRisk) o;
        return Objects.equals(id, uniRisk.id) &&
                Objects.equals(name, uniRisk.name) &&
                Objects.equals(remark, uniRisk.remark) &&
                Objects.equals(createTime, uniRisk.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, remark, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("remark", remark)
                .append("createTime", createTime)
                .toString();
    }
}
