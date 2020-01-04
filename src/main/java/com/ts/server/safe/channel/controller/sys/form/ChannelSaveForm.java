package com.ts.server.safe.channel.controller.sys.form;

import com.ts.server.safe.channel.domain.Channel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增服务商提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ChannelSaveForm {
    @NotBlank
    @ApiModelProperty(value = "单位名称", required = true)
    private String name;
    @NotBlank
    @ApiModelProperty(value = "省份编号", required = true)
    private String provinceId;
    @NotBlank
    @ApiModelProperty(value = "城市编号", required = true)
    private String cityId;
    @ApiModelProperty(value = "县区编号")
    private String countryId;
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
    @ApiModelProperty(value = "管理员用户名", required = true)
    @NotBlank
    private String manUsername;
    @ApiModelProperty(value = "管理员密码", required = true)
    @NotBlank
    private String manPassword;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(String provinceId) {
        this.provinceId = provinceId;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
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

    public String getManUsername() {
        return manUsername;
    }

    public void setManUsername(String manUsername) {
        this.manUsername = manUsername;
    }

    public String getManPassword() {
        return manPassword;
    }

    public void setManPassword(String manPassword) {
        this.manPassword = manPassword;
    }

    public Channel toDomain(){
        Channel t = new Channel();

        t.setName(name);
        t.setProvinceId(provinceId);
        t.setCityId(cityId);
        t.setCountryId(countryId);
        t.setAddress(address);
        t.setRegAddress(regAddress);
        t.setPhone(phone);
        t.setMobile(mobile);
        t.setContact(contact);
        t.setBusScope(busScope);
        t.setStatus(Channel.Status.WAIT);

        return t;
    }
}
