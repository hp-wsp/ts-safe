package com.ts.server.safe.channel.controller.sys.form;

import com.ts.server.safe.channel.domain.Channel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 修改服务商提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ChannelUpdateForm {
    @NotBlank
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @NotBlank
    @ApiModelProperty(value = "单位名称", required = true)
    private String name;
    @ApiModelProperty(value = "省份", required = true)
    private String province;
    @ApiModelProperty(value = "城市", required = true)
    private String city;
    @ApiModelProperty(value = "地址")
    private String address;
    @ApiModelProperty(value = "注册地址")
    private String regAddress;
    @ApiModelProperty(value = "联系电话", required = true)
    @NotBlank
    private String phone;
    @ApiModelProperty(value = "手机")
    private String mobile;
    @ApiModelProperty(value = "法人", required = true)
    @NotBlank
    private String contact;
    @ApiModelProperty(value = "经营范围")
    private String busScope;

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

    public String getBusScope() {
        return busScope;
    }

    public void setBusScope(String busScope) {
        this.busScope = busScope;
    }

    public Channel toDomain(){
        Channel t = new Channel();

        t.setId(id);
        t.setName(name);
        t.setProvince(province);
        t.setCity(city);
        t.setAddress(address);
        t.setRegAddress(regAddress);
        t.setPhone(phone);
        t.setMobile(mobile);
        t.setContact(contact);
        t.setBusScope(busScope);

        return t;
    }
}
