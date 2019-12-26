package com.ts.server.safe.channel.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 合同
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class Contract {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("服务商编号")
    private String channelId;
    @ApiModelProperty("服务编号")
    private String serviceId;
    @ApiModelProperty("合同编号")
    private String num;
    @ApiModelProperty("合同名称")
    private String name;
    @ApiModelProperty("咨询服务项目")
    private String conProject;
    @ApiModelProperty("委托单位形式")
    private int entCompType;
    @ApiModelProperty("委托单位")
    private String entCompName;
    @ApiModelProperty("服务企业编号")
    private String serCompId;
    @ApiModelProperty("服务企业名称")
    private String serCompName;
    @ApiModelProperty("委托服务地址")
    private String serAddress;
    @ApiModelProperty("签订合同时间")
    private Date sigConDate;
    @ApiModelProperty("项目属地")
    private String proAddress;
    @ApiModelProperty("合同约定服务周期开始时间")
    private Date serConDateFrom;
    @ApiModelProperty("合同约定服务周期结束时间")
    private Date serConDateTo;
    @ApiModelProperty("合同金额")
    private int amount;
    @ApiModelProperty("甲方联系人")
    private String ownPerson;
    @ApiModelProperty("甲方联系方式")
    private String ownPhone;
    @ApiModelProperty("我方签单人")
    private String sigPerson;
    @ApiModelProperty("我方签单单位")
    private String sigCompany;
    @ApiModelProperty("修改时间")
    private Date updateTime;
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

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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

    public String getConProject() {
        return conProject;
    }

    public void setConProject(String conProject) {
        this.conProject = conProject;
    }

    public int getEntCompType() {
        return entCompType;
    }

    public void setEntCompType(int entCompType) {
        this.entCompType = entCompType;
    }

    public String getEntCompName() {
        return entCompName;
    }

    public void setEntCompName(String entCompName) {
        this.entCompName = entCompName;
    }

    public String getSerCompId() {
        return serCompId;
    }

    public void setSerCompId(String serCompId) {
        this.serCompId = serCompId;
    }

    public String getSerCompName() {
        return serCompName;
    }

    public void setSerCompName(String serCompName) {
        this.serCompName = serCompName;
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
        Contract contract = (Contract) o;
        return entCompType == contract.entCompType &&
                amount == contract.amount &&
                Objects.equals(id, contract.id) &&
                Objects.equals(channelId, contract.channelId) &&
                Objects.equals(serviceId, contract.serviceId) &&
                Objects.equals(num, contract.num) &&
                Objects.equals(name, contract.name) &&
                Objects.equals(conProject, contract.conProject) &&
                Objects.equals(entCompName, contract.entCompName) &&
                Objects.equals(serCompId, contract.serCompId) &&
                Objects.equals(serCompName, contract.serCompName) &&
                Objects.equals(serAddress, contract.serAddress) &&
                Objects.equals(sigConDate, contract.sigConDate) &&
                Objects.equals(proAddress, contract.proAddress) &&
                Objects.equals(serConDateFrom, contract.serConDateFrom) &&
                Objects.equals(serConDateTo, contract.serConDateTo) &&
                Objects.equals(ownPerson, contract.ownPerson) &&
                Objects.equals(ownPhone, contract.ownPhone) &&
                Objects.equals(sigPerson, contract.sigPerson) &&
                Objects.equals(sigCompany, contract.sigCompany) &&
                Objects.equals(updateTime, contract.updateTime) &&
                Objects.equals(createTime, contract.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channelId, serviceId, num, name, conProject, entCompType, entCompName, serCompId, serCompName, serAddress, sigConDate, proAddress, serConDateFrom, serConDateTo, amount, ownPerson, ownPhone, sigPerson, sigCompany, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("channelId", channelId)
                .append("serviceId", serviceId)
                .append("num", num)
                .append("name", name)
                .append("conProject", conProject)
                .append("entCompType", entCompType)
                .append("entCompName", entCompName)
                .append("serCompId", serCompId)
                .append("serCompName", serCompName)
                .append("serAddress", serAddress)
                .append("sigConDate", sigConDate)
                .append("proAddress", proAddress)
                .append("serConDateFrom", serConDateFrom)
                .append("serConDateTo", serConDateTo)
                .append("amount", amount)
                .append("ownPerson", ownPerson)
                .append("ownPhone", ownPhone)
                .append("sigPerson", sigPerson)
                .append("sigCompany", sigCompany)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
