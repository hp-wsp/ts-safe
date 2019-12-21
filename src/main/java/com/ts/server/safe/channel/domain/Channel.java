package com.ts.server.safe.channel.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 服务商
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class Channel {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "服务商名称")
    private String name;
    @ApiModelProperty("省份")
    private String province;
    @ApiModelProperty("城市")
    private String city;
    @ApiModelProperty("地址")
    private String address;
    @ApiModelProperty("注册地址")
    private String regAddress;
    @ApiModelProperty(value = "联系电话")
    private String phone;
    @ApiModelProperty(value = "手机")
    private String mobile;
    @ApiModelProperty(value = "法人")
    private String contact;
    @ApiModelProperty(value = "经营方位")
    private String busScope;
    @ApiModelProperty(value = "服务商状态")
    private Status status;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    /**
     * 服务商状态
     */
    public enum Status {
        WAIT, OPEN, CLOSED
    }

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRegAddress() {
        return regAddress;
    }

    public void setRegAddress(String regAddress) {
        this.regAddress = regAddress;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getBusScope() {
        return busScope;
    }

    public void setBusScope(String busScope) {
        this.busScope = busScope;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
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
        Channel channel = (Channel) o;
        return Objects.equals(id, channel.id) &&
                Objects.equals(name, channel.name) &&
                Objects.equals(province, channel.province) &&
                Objects.equals(city, channel.city) &&
                Objects.equals(address, channel.address) &&
                Objects.equals(regAddress, channel.regAddress) &&
                Objects.equals(phone, channel.phone) &&
                Objects.equals(mobile, channel.mobile) &&
                Objects.equals(contact, channel.contact) &&
                Objects.equals(busScope, channel.busScope) &&
                status == channel.status &&
                Objects.equals(updateTime, channel.updateTime) &&
                Objects.equals(createTime, channel.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, province, city, address, regAddress, phone, mobile,
                contact, busScope, status, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("province", province)
                .append("city", city)
                .append("address", address)
                .append("regAddress", regAddress)
                .append("phone", phone)
                .append("mobile", mobile)
                .append("contact", contact)
                .append("busScope", busScope)
                .append("status", status)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
