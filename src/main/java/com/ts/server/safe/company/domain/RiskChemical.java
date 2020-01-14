package com.ts.server.safe.company.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 危化品存储
 *
 * @author <a href="mail:hhywangwei@gmail.com">WangWei</a>
 */
public class RiskChemical {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("企业编号")
    private String compId;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("别名")
    private String alias;
    @ApiModelProperty("CAS编号")
    private String cas;
    @ApiModelProperty("最多存储量")
    private String maxStore;
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

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCas() {
        return cas;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }

    public String getMaxStore() {
        return maxStore;
    }

    public void setMaxStore(String maxStore) {
        this.maxStore = maxStore;
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
        RiskChemical that = (RiskChemical) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(compId, that.compId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(alias, that.alias) &&
                Objects.equals(cas, that.cas) &&
                Objects.equals(maxStore, that.maxStore) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, compId, name, alias, cas, maxStore, remark, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("compId", compId)
                .append("name", name)
                .append("alias", alias)
                .append("cas", cas)
                .append("maxStore", maxStore)
                .append("remark", remark)
                .append("createTime", createTime)
                .toString();
    }
}
