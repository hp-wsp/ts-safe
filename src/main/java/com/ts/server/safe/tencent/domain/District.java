package com.ts.server.safe.tencent.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 行政区域
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class District {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("全名")
    private String fullName;
    @ApiModelProperty("上级区域编号")
    private String parentId;
    @ApiModelProperty("地理位置")
    private String[] location;
    @ApiModelProperty("等级")
    private int level;
    @ApiModelProperty("创建时间")
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String[] getLocation() {
        return location;
    }

    public void setLocation(String[] location) {
        this.location = location;
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
        District district = (District) o;
        return level == district.level &&
                Objects.equals(id, district.id) &&
                Objects.equals(name, district.name) &&
                Objects.equals(fullName, district.fullName) &&
                Objects.equals(parentId, district.parentId) &&
                Arrays.equals(location, district.location) &&
                Objects.equals(createTime, district.createTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, name, fullName, parentId, level, createTime);
        result = 31 * result + Arrays.hashCode(location);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("fullName", fullName)
                .append("parentId", parentId)
                .append("location", location)
                .append("level", level)
                .append("createTime", createTime)
                .toString();
    }
}
