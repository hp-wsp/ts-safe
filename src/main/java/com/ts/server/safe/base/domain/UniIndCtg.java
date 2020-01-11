package com.ts.server.safe.base.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 统一行业分类
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniIndCtg {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("分类编号")
    private String num;
    @ApiModelProperty("分类名称")
    private String name;
    @ApiModelProperty("上级分类编号")
    private String parentId;
    @ApiModelProperty("分类级别")
    private int  level;
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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
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
        UniIndCtg that = (UniIndCtg) o;
        return level == that.level &&
                Objects.equals(id, that.id) &&
                Objects.equals(num, that.num) &&
                Objects.equals(name, that.name) &&
                Objects.equals(parentId, that.parentId) &&
                Objects.equals(remark, that.remark) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, num, name, parentId, level, remark, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("num", num)
                .append("name", name)
                .append("parentId", parentId)
                .append("level", level)
                .append("remark", remark)
                .append("createTime", createTime)
                .toString();
    }
}
