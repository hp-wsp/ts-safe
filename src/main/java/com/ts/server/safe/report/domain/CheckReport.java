package com.ts.server.safe.report.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.List;
import java.util.Objects;

/**
 * 检查报告
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CheckReport {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "服务商编号")
    private String channelId;
    @ApiModelProperty(value = "服务商名称")
    private String channelName;
    @ApiModelProperty(value = "企业编号")
    private String compId;
    @ApiModelProperty(value = "企业名称")
    private String compName;
    @ApiModelProperty("委托单位形式")
    private int entCompType;
    @ApiModelProperty("所属行业")
    private String industry;
    @ApiModelProperty("企业规模")
    private int entScale;
    @ApiModelProperty("所属区域")
    private String area;
    @ApiModelProperty("检查日期")
    private String checkDate;


    /**
     * 编制说明
     */
    public static class BzDetail {
        @ApiModelProperty("委托单位")
        private String company;
        @ApiModelProperty("合同编号")
        private String conNum;
        @ApiModelProperty("周期和内容")
        private String cycleContent;
        @ApiModelProperty("服务主要依据")
        private String serviceBase;
        @ApiModelProperty("服务方法")
        private String serviceMethod;
        @ApiModelProperty("服务范围")
        private String serviceRange;
        @ApiModelProperty("受委托单位")
        private String chanName;
        @ApiModelProperty("受委托地址")
        private String chanAddress;
        @ApiModelProperty("受委托经营范围")
        private String chanBusScope;
        @ApiModelProperty(value = "联系电话")
        private String chanPhone;
        @ApiModelProperty(value = "手机")
        private String chanMobile;
        @ApiModelProperty(value = "法人")
        private String chanContact;
        @ApiModelProperty("组长")
        private PersonInfo leader;
        @ApiModelProperty("分工")
        private List<PersonInfo> workers;
        @ApiModelProperty("主要负责人")
        private PersonInfo principalPerson;
        @ApiModelProperty("报告审核人")
        private PersonInfo auditPerson;
        @ApiModelProperty("报告编制人")
        private PersonInfo reportPerson;

        public String getCompany() {
            return company;
        }

        public void setCompany(String company) {
            this.company = company;
        }

        public String getConNum() {
            return conNum;
        }

        public void setConNum(String conNum) {
            this.conNum = conNum;
        }

        public String getCycleContent() {
            return cycleContent;
        }

        public void setCycleContent(String cycleContent) {
            this.cycleContent = cycleContent;
        }

        public String getServiceBase() {
            return serviceBase;
        }

        public void setServiceBase(String serviceBase) {
            this.serviceBase = serviceBase;
        }

        public String getServiceMethod() {
            return serviceMethod;
        }

        public void setServiceMethod(String serviceMethod) {
            this.serviceMethod = serviceMethod;
        }

        public String getServiceRange() {
            return serviceRange;
        }

        public void setServiceRange(String serviceRange) {
            this.serviceRange = serviceRange;
        }

        public String getChanName() {
            return chanName;
        }

        public void setChanName(String chanName) {
            this.chanName = chanName;
        }

        public String getChanAddress() {
            return chanAddress;
        }

        public void setChanAddress(String chanAddress) {
            this.chanAddress = chanAddress;
        }

        public String getChanBusScope() {
            return chanBusScope;
        }

        public void setChanBusScope(String chanBusScope) {
            this.chanBusScope = chanBusScope;
        }

        public String getChanPhone() {
            return chanPhone;
        }

        public void setChanPhone(String chanPhone) {
            this.chanPhone = chanPhone;
        }

        public String getChanMobile() {
            return chanMobile;
        }

        public void setChanMobile(String chanMobile) {
            this.chanMobile = chanMobile;
        }

        public String getChanContact() {
            return chanContact;
        }

        public void setChanContact(String chanContact) {
            this.chanContact = chanContact;
        }

        public PersonInfo getLeader() {
            return leader;
        }

        public void setLeader(PersonInfo leader) {
            this.leader = leader;
        }

        public List<PersonInfo> getWorkers() {
            return workers;
        }

        public void setWorkers(List<PersonInfo> workers) {
            this.workers = workers;
        }

        public PersonInfo getPrincipalPerson() {
            return principalPerson;
        }

        public void setPrincipalPerson(PersonInfo principalPerson) {
            this.principalPerson = principalPerson;
        }

        public PersonInfo getAuditPerson() {
            return auditPerson;
        }

        public void setAuditPerson(PersonInfo auditPerson) {
            this.auditPerson = auditPerson;
        }

        public PersonInfo getReportPerson() {
            return reportPerson;
        }

        public void setReportPerson(PersonInfo reportPerson) {
            this.reportPerson = reportPerson;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            BzDetail bzDetail = (BzDetail) o;
            return Objects.equals(company, bzDetail.company) &&
                    Objects.equals(conNum, bzDetail.conNum) &&
                    Objects.equals(cycleContent, bzDetail.cycleContent) &&
                    Objects.equals(serviceBase, bzDetail.serviceBase) &&
                    Objects.equals(serviceMethod, bzDetail.serviceMethod) &&
                    Objects.equals(serviceRange, bzDetail.serviceRange) &&
                    Objects.equals(chanName, bzDetail.chanName) &&
                    Objects.equals(chanAddress, bzDetail.chanAddress) &&
                    Objects.equals(chanBusScope, bzDetail.chanBusScope) &&
                    Objects.equals(chanPhone, bzDetail.chanPhone) &&
                    Objects.equals(chanMobile, bzDetail.chanMobile) &&
                    Objects.equals(chanContact, bzDetail.chanContact) &&
                    Objects.equals(leader, bzDetail.leader) &&
                    Objects.equals(workers, bzDetail.workers) &&
                    Objects.equals(principalPerson, bzDetail.principalPerson) &&
                    Objects.equals(auditPerson, bzDetail.auditPerson) &&
                    Objects.equals(reportPerson, bzDetail.reportPerson);
        }

        @Override
        public int hashCode() {
            return Objects.hash(company, conNum, cycleContent, serviceBase, serviceMethod, serviceRange, chanName, chanAddress, chanBusScope, chanPhone, chanMobile, chanContact, leader, workers, principalPerson, auditPerson, reportPerson);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("company", company)
                    .append("conNum", conNum)
                    .append("cycleContent", cycleContent)
                    .append("serviceBase", serviceBase)
                    .append("serviceMethod", serviceMethod)
                    .append("serviceRange", serviceRange)
                    .append("chanName", chanName)
                    .append("chanAddress", chanAddress)
                    .append("chanBusScope", chanBusScope)
                    .append("chanPhone", chanPhone)
                    .append("chanMobile", chanMobile)
                    .append("chanContact", chanContact)
                    .append("leader", leader)
                    .append("workers", workers)
                    .append("principalPerson", principalPerson)
                    .append("auditPerson", auditPerson)
                    .append("reportPerson", reportPerson)
                    .toString();
        }
    }

    /**
     * 人员信息
     */
    static class PersonInfo {
        @ApiModelProperty("名称")
        private String name;
        @ApiModelProperty("职务")
        private String duty;
        @ApiModelProperty("联系电话")
        private String phone;
        @ApiModelProperty("手机")
        private String mobile;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDuty() {
            return duty;
        }

        public void setDuty(String duty) {
            this.duty = duty;
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

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("name", name)
                    .append("duty", duty)
                    .append("phone", phone)
                    .append("mobile", mobile)
                    .toString();
        }
    }

    /**
     * 受检企业基本情况
     */
    static class CompBaseInfo {
        private String compName;
        private String regAddress;
        @ApiModelProperty("社会信用代码")
        private String creditCode;
        private PersonInfo conPerson;
        private PersonInfo safePerson;
        private PersonInfo contractPerson;
        private String profile;

    }
}
