package com.ts.server.safe.channel.controller.man.form;

import com.ts.server.safe.channel.domain.CompInfo;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 新增公司
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CompInfoSaveForm {
    @ApiModelProperty(value = "企业名称", required = true)
    @NotBlank
    private String name;
    @ApiModelProperty(value = "省份")
    private String province;
    @ApiModelProperty(value = "城市")
    private String city;
    @ApiModelProperty(value = "国家")
    private String country;
    @ApiModelProperty("企业地址")
    private String address;
    @ApiModelProperty("注册地址")
    private String regAddress;
    @ApiModelProperty("注册日期")
    private String regDate;
    @ApiModelProperty("经营状态")
    private int busStatus;
    @ApiModelProperty(value = "法人", required = true)
    @NotBlank
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
    @ApiModelProperty(value = "联系人", required = true)
    @NotBlank
    private String contact;
    @ApiModelProperty("电话")
    private String phone;
    @ApiModelProperty(value = "手机", required = true)
    @NotBlank
    private String mobile;
    @ApiModelProperty(value = "社会信用代码", required = true)
    @NotBlank
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

    public CompInfo toDomain(){
        CompInfo t = new CompInfo();

        t.setName(name);
        t.setProvince(province);
        t.setCity(city);
        t.setCountry(country);
        t.setAddress(address);
        t.setRegAddress(regAddress);
        t.setRegDate(regDate);
        t.setBusStatus(busStatus);
        t.setLegalPerson(legalPerson);
        t.setLegalPhone(legalPhone);
        t.setLegalMobile(legalMobile);
        t.setSafePerson(safePerson);
        t.setSafePhone(safePhone);
        t.setSafeMobile(safeMobile);
        t.setContact(contact);
        t.setPhone(phone);
        t.setMobile(mobile);
        t.setCreditCode(creditCode);
        t.setPostCode(postCode);
        t.setIndCtgIds(indCtgIds);
        t.setIndCtgNames(indCtgNames);
        t.setIndPhone(indPhone);
        t.setEntScale(entScale);
        t.setIndOfCompany(indOfCompany);
        t.setMemFun(memFun);
        t.setManProduct(manProduct);
        t.setProfile(profile);
        t.setImages(images);

        return t;
    }
}
