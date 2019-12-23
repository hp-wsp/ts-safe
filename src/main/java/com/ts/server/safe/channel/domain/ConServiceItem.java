package com.ts.server.safe.channel.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 *  合同服务项目
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ConServiceItem {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("服务编号")
    private String serviceId;
    @ApiModelProperty("项目编号")
    private int itemId;
    @ApiModelProperty("项目名称")
    private String itemName;
    @ApiModelProperty("项目值")
    private String itemValue;
    @ApiModelProperty("创建时间")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public int getItemId() {
        return itemId;
    }

    public void setItemId(int itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemValue() {
        return itemValue;
    }

    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
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
        ConServiceItem that = (ConServiceItem) o;
        return itemId == that.itemId &&
                Objects.equals(id, that.id) &&
                Objects.equals(serviceId, that.serviceId) &&
                Objects.equals(itemName, that.itemName) &&
                Objects.equals(itemValue, that.itemValue) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, serviceId, itemId, itemName, itemValue, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("serviceId", serviceId)
                .append("itemId", itemId)
                .append("itemName", itemName)
                .append("itemValue", itemValue)
                .append("createTime", createTime)
                .toString();
    }
}
