package com.ts.server.safe.channel.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 公司基础信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CompInfo {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("服务商编号")
    private String channelId;
    @ApiModelProperty("企业名称")
    private String name;
    @ApiModelProperty("省份编号")
    private String provinceId;
    @ApiModelProperty("省份")
    private String province;
    @ApiModelProperty("城市编号")
    private String cityId;
    @ApiModelProperty("城市")
    private String city;
    @ApiModelProperty("县区编号")
    private String countryId;
    @ApiModelProperty("县区")
    private String country;
    @ApiModelProperty("企业地址")
    private String address;
    @ApiModelProperty("注册地址")
    private String regAddress;
    @ApiModelProperty("注册日期")
    private String regDate;
    @ApiModelProperty("经营状态")
    private int busStatus;
    @ApiModelProperty("法人")
    private String legalPerson;
    @ApiModelProperty("法人电话")
    private String legalPhone;
    @ApiModelProperty("法人手机")
    private String legalMobile;
    @ApiModelProperty("安全管理员")
    private String safePerson;
    @ApiModelProperty("安全管理员电话")
    private String safePhone;
    @ApiModelProperty("安全管理员手机")
    private String safeMobile;
    @ApiModelProperty("联系")
    private String contact;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty("手机")
    private String mobile;
    @ApiModelProperty("社会信用代码")
    private String creditCode;
    @ApiModelProperty("邮编")
    private String postCode;
    @ApiModelProperty("行业分类编号集合")
    private List<String> indCtgIds;
    @ApiModelProperty("行业分类名称集合")
    private List<String> indCtgNames;
    @ApiModelProperty("联系电话")
    private String indPhone;
    @ApiModelProperty("企业规模")
    private int entScale;
    @ApiModelProperty("行业主管单位")
    private String indOfCompany;
    @ApiModelProperty("隶属关系")
    private String memFun;
    @ApiModelProperty("经营产品")
    private String manProduct;
    @ApiModelProperty("企业简介")
    private String profile;
    @ApiModelProperty("附图")
    private List<String> images;
    @ApiModelProperty("更新时间")
    private Date updateTime;
    @ApiModelProperty("创建时间")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

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

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountryId() {
        return countryId;
    }

    public void setCountryId(String countryId) {
        this.countryId = countryId;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public int getBusStatus() {
        return busStatus;
    }

    public void setBusStatus(int busStatus) {
        this.busStatus = busStatus;
    }

    public String getLegalPerson() {
        return legalPerson;
    }

    public void setLegalPerson(String legalPerson) {
        this.legalPerson = legalPerson;
    }

    public String getLegalPhone() {
        return legalPhone;
    }

    public void setLegalPhone(String legalPhone) {
        this.legalPhone = legalPhone;
    }

    public String getLegalMobile() {
        return legalMobile;
    }

    public void setLegalMobile(String legalMobile) {
        this.legalMobile = legalMobile;
    }

    public String getSafePerson() {
        return safePerson;
    }

    public void setSafePerson(String safePerson) {
        this.safePerson = safePerson;
    }

    public String getSafePhone() {
        return safePhone;
    }

    public void setSafePhone(String safePhone) {
        this.safePhone = safePhone;
    }

    public String getSafeMobile() {
        return safeMobile;
    }

    public void setSafeMobile(String safeMobile) {
        this.safeMobile = safeMobile;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
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

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public List<String> getIndCtgIds() {
        return indCtgIds;
    }

    public void setIndCtgIds(List<String> indCtgIds) {
        this.indCtgIds = indCtgIds;
    }

    public List<String> getIndCtgNames() {
        return indCtgNames;
    }

    public void setIndCtgNames(List<String> indCtgNames) {
        this.indCtgNames = indCtgNames;
    }

    public String getIndPhone() {
        return indPhone;
    }

    public void setIndPhone(String indPhone) {
        this.indPhone = indPhone;
    }

    public int getEntScale() {
        return entScale;
    }

    public void setEntScale(int entScale) {
        this.entScale = entScale;
    }

    public String getIndOfCompany() {
        return indOfCompany;
    }

    public void setIndOfCompany(String indOfCompany) {
        this.indOfCompany = indOfCompany;
    }

    public String getMemFun() {
        return memFun;
    }

    public void setMemFun(String memFun) {
        this.memFun = memFun;
    }

    public String getManProduct() {
        return manProduct;
    }

    public void setManProduct(String manProduct) {
        this.manProduct = manProduct;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
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
        CompInfo compInfo = (CompInfo) o;
        return busStatus == compInfo.busStatus &&
                entScale == compInfo.entScale &&
                Objects.equals(id, compInfo.id) &&
                Objects.equals(channelId, compInfo.channelId) &&
                Objects.equals(name, compInfo.name) &&
                Objects.equals(provinceId, compInfo.provinceId) &&
                Objects.equals(province, compInfo.province) &&
                Objects.equals(cityId, compInfo.cityId) &&
                Objects.equals(city, compInfo.city) &&
                Objects.equals(countryId, compInfo.countryId) &&
                Objects.equals(country, compInfo.country) &&
                Objects.equals(address, compInfo.address) &&
                Objects.equals(regAddress, compInfo.regAddress) &&
                Objects.equals(regDate, compInfo.regDate) &&
                Objects.equals(legalPerson, compInfo.legalPerson) &&
                Objects.equals(legalPhone, compInfo.legalPhone) &&
                Objects.equals(legalMobile, compInfo.legalMobile) &&
                Objects.equals(safePerson, compInfo.safePerson) &&
                Objects.equals(safePhone, compInfo.safePhone) &&
                Objects.equals(safeMobile, compInfo.safeMobile) &&
                Objects.equals(contact, compInfo.contact) &&
                Objects.equals(phone, compInfo.phone) &&
                Objects.equals(mobile, compInfo.mobile) &&
                Objects.equals(creditCode, compInfo.creditCode) &&
                Objects.equals(postCode, compInfo.postCode) &&
                Objects.equals(indCtgIds, compInfo.indCtgIds) &&
                Objects.equals(indCtgNames, compInfo.indCtgNames) &&
                Objects.equals(indPhone, compInfo.indPhone) &&
                Objects.equals(indOfCompany, compInfo.indOfCompany) &&
                Objects.equals(memFun, compInfo.memFun) &&
                Objects.equals(manProduct, compInfo.manProduct) &&
                Objects.equals(profile, compInfo.profile) &&
                Objects.equals(images, compInfo.images) &&
                Objects.equals(updateTime, compInfo.updateTime) &&
                Objects.equals(createTime, compInfo.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channelId, name, provinceId, province, cityId, city, countryId, country, address, regAddress, regDate, busStatus, legalPerson, legalPhone, legalMobile, safePerson, safePhone, safeMobile, contact, phone, mobile, creditCode, postCode, indCtgIds, indCtgNames, indPhone, entScale, indOfCompany, memFun, manProduct, profile, images, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("channelId", channelId)
                .append("name", name)
                .append("provinceId", provinceId)
                .append("province", province)
                .append("cityId", cityId)
                .append("city", city)
                .append("countryId", countryId)
                .append("country", country)
                .append("address", address)
                .append("regAddress", regAddress)
                .append("regDate", regDate)
                .append("busStatus", busStatus)
                .append("legalPerson", legalPerson)
                .append("legalPhone", legalPhone)
                .append("legalMobile", legalMobile)
                .append("safePerson", safePerson)
                .append("safePhone", safePhone)
                .append("safeMobile", safeMobile)
                .append("contact", contact)
                .append("phone", phone)
                .append("mobile", mobile)
                .append("creditCode", creditCode)
                .append("postCode", postCode)
                .append("indCtgIds", indCtgIds)
                .append("indCtgNames", indCtgNames)
                .append("indPhone", indPhone)
                .append("entScale", entScale)
                .append("indOfCompany", indOfCompany)
                .append("memFun", memFun)
                .append("manProduct", manProduct)
                .append("profile", profile)
                .append("images", images)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
