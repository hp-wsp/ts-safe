package com.ts.server.safe.channel.controller.man.form;

import com.ts.server.safe.channel.domain.Contract;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * 新增合同
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ContractSaveForm {
    @ApiModelProperty(value = "合同编号", required = true)
    @NotBlank
    private String num;
    @ApiModelProperty(value = "合同名称", required = true)
    @NotBlank
    private String name;
    @ApiModelProperty(value = "咨询服务项目")
    private String conProject;
    @ApiModelProperty(value = "委托单位形式", required = true)
    @NotNull
    private int delCompanyType;
    @ApiModelProperty(value = "委托单位")
    private String delCompany;
    @ApiModelProperty(value = "服务企业编号", required = true)
    @NotBlank
    private String serCompanyId;
    @ApiModelProperty("委托服务地址")
    private String serAddress;
    @ApiModelProperty(value = "签订合同时间", required = true)
    @NotNull
    private Date sigConDate;
    @ApiModelProperty(value = "项目属地")
    private String proAddress;
    @ApiModelProperty(value = "合同约定服务周期开始时间")
    private Date serConDateFrom;
    @ApiModelProperty(value = "合同约定服务周期结束时间")
    private Date serConDateTo;
    @ApiModelProperty(value = "合同金额", required = true)
    @Min(0)
    private int amount;
    @ApiModelProperty(value = "甲方联系人")
    private String ownPerson;
    @ApiModelProperty(value = "甲方联系方式")
    private String ownPhone;
    @ApiModelProperty(value = "我方签单人")
    private String sigPerson;
    @ApiModelProperty(value = "我方签单单位")
    private String sigCompany;

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

    public String getConProject() {
        return conProject;
    }

    public void setConProject(String conProject) {
        this.conProject = conProject;
    }

    public int getDelCompanyType() {
        return delCompanyType;
    }

    public void setDelCompanyType(int delCompanyType) {
        this.delCompanyType = delCompanyType;
    }

    public String getDelCompany() {
        return delCompany;
    }

    public void setDelCompany(String delCompany) {
        this.delCompany = delCompany;
    }

    public String getSerCompanyId() {
        return serCompanyId;
    }

    public void setSerCompanyId(String serCompanyId) {
        this.serCompanyId = serCompanyId;
    }

    public String getSerAddress() {
        return serAddress;
    }

    public void setSerAddress(String serAddress) {
        this.serAddress = serAddress;
    }

    public Date getSigConDate() {
        return sigConDate;
    }

    public void setSigConDate(Date sigConDate) {
        this.sigConDate = sigConDate;
    }

    public String getProAddress() {
        return proAddress;
    }

    public void setProAddress(String proAddress) {
        this.proAddress = proAddress;
    }

    public Date getSerConDateFrom() {
        return serConDateFrom;
    }

    public void setSerConDateFrom(Date serConDateFrom) {
        this.serConDateFrom = serConDateFrom;
    }

    public Date getSerConDateTo() {
        return serConDateTo;
    }

    public void setSerConDateTo(Date serConDateTo) {
        this.serConDateTo = serConDateTo;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getOwnPerson() {
        return ownPerson;
    }

    public void setOwnPerson(String ownPerson) {
        this.ownPerson = ownPerson;
    }

    public String getOwnPhone() {
        return ownPhone;
    }

    public void setOwnPhone(String ownPhone) {
        this.ownPhone = ownPhone;
    }

    public String getSigPerson() {
        return sigPerson;
    }

    public void setSigPerson(String sigPerson) {
        this.sigPerson = sigPerson;
    }

    public String getSigCompany() {
        return sigCompany;
    }

    public void setSigCompany(String sigCompany) {
        this.sigCompany = sigCompany;
    }

    public Contract toDomain(){
        Contract t = new Contract();

        t.setNum(num);
        t.setName(name);
        t.setConProject(conProject);
        t.setDelCompanyType(delCompanyType);
        t.setDelCompany(delCompany);
        t.setSerCompanyId(serCompanyId);
        t.setSerAddress(serAddress);
        t.setSigConDate(sigConDate);
        t.setProAddress(proAddress);
        t.setSerConDateFrom(serConDateFrom);
        t.setSerConDateTo(serConDateTo);
        t.setAmount(amount);
        t.setOwnPerson(ownPerson);
        t.setOwnPhone(ownPhone);
        t.setSigPerson(sigPerson);
        t.setSigCompany(sigCompany);

        return t;
    }
}
