package com.ts.server.safe.report.domain;

import com.ts.server.safe.channel.domain.CheckContent;
import com.ts.server.safe.company.domain.CompProduct;
import com.ts.server.safe.company.domain.RiskChemical;
import com.ts.server.safe.company.domain.SpePerson;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
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
    @ApiModelProperty("检查任务编号")
    private String taskId;
    @ApiModelProperty("检查任务描述")
    private String taskDetail;
    @ApiModelProperty("服务系统编号")
    private String serviceId;
    @ApiModelProperty("服务名称")
    private String serviceName;
    @ApiModelProperty("检查周期")
    private String cycleName;
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
    @ApiModelProperty("编制说明")
    private BzDetail bzDetail;
    @ApiModelProperty("受检企业基本情况")
    private CompBaseInfo compBaseInfo;
    @ApiModelProperty("安全生产社会化检查服务")
    private SafeProduct safeProduct;
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

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskDetail() {
        return taskDetail;
    }

    public void setTaskDetail(String taskDetail) {
        this.taskDetail = taskDetail;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
    }

    public String getCycleName() {
        return cycleName;
    }

    public void setCycleName(String cycleName) {
        this.cycleName = cycleName;
    }

    public int getEntCompType() {
        return entCompType;
    }

    public void setEntCompType(int entCompType) {
        this.entCompType = entCompType;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getEntScale() {
        return entScale;
    }

    public void setEntScale(int entScale) {
        this.entScale = entScale;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public BzDetail getBzDetail() {
        return bzDetail;
    }

    public void setBzDetail(BzDetail bzDetail) {
        this.bzDetail = bzDetail;
    }

    public CompBaseInfo getCompBaseInfo() {
        return compBaseInfo;
    }

    public void setCompBaseInfo(CompBaseInfo compBaseInfo) {
        this.compBaseInfo = compBaseInfo;
    }

    public SafeProduct getSafeProduct() {
        return safeProduct;
    }

    public void setSafeProduct(SafeProduct safeProduct) {
        this.safeProduct = safeProduct;
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
        CheckReport that = (CheckReport) o;
        return entCompType == that.entCompType &&
                entScale == that.entScale &&
                Objects.equals(id, that.id) &&
                Objects.equals(channelId, that.channelId) &&
                Objects.equals(channelName, that.channelName) &&
                Objects.equals(compId, that.compId) &&
                Objects.equals(compName, that.compName) &&
                Objects.equals(taskId, that.taskId) &&
                Objects.equals(taskDetail, that.taskDetail) &&
                Objects.equals(serviceId, that.serviceId) &&
                Objects.equals(serviceName, that.serviceName) &&
                Objects.equals(cycleName, that.cycleName) &&
                Objects.equals(industry, that.industry) &&
                Objects.equals(area, that.area) &&
                Objects.equals(checkDate, that.checkDate) &&
                Objects.equals(bzDetail, that.bzDetail) &&
                Objects.equals(compBaseInfo, that.compBaseInfo) &&
                Objects.equals(safeProduct, that.safeProduct) &&
                Objects.equals(updateTime, that.updateTime) &&
                Objects.equals(createTime, that.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channelId, channelName, compId, compName, taskId, taskDetail, serviceId, serviceName, cycleName, entCompType, industry, entScale, area, checkDate, bzDetail, compBaseInfo, safeProduct, updateTime, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("channelId", channelId)
                .append("channelName", channelName)
                .append("compId", compId)
                .append("compName", compName)
                .append("taskId", taskId)
                .append("taskDetail", taskDetail)
                .append("serviceId", serviceId)
                .append("serviceName", serviceName)
                .append("cycleName", cycleName)
                .append("entCompType", entCompType)
                .append("industry", industry)
                .append("entScale", entScale)
                .append("area", area)
                .append("checkDate", checkDate)
                .append("bzDetail", bzDetail)
                .append("compBaseInfo", compBaseInfo)
                .append("safeProduct", safeProduct)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }

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
        private PersonInfo chanContact;
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

        public PersonInfo getChanContact() {
            return chanContact;
        }

        public void setChanContact(PersonInfo chanContact) {
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
    public static class PersonInfo {
        @ApiModelProperty("名称")
        private String name;
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
                    .append("phone", phone)
                    .append("mobile", mobile)
                    .toString();
        }
    }

    /**
     * 受检企业基本情况
     */
    public static class CompBaseInfo {
        @ApiModelProperty("企业名称")
        private String compName;
        @ApiModelProperty("注册地址")
        private String regAddress;
        @ApiModelProperty("社会信用代码")
        private String creditCode;
        @ApiModelProperty("法定代表人")
        private PersonInfo conPerson;
        @ApiModelProperty("安全管理人")
        private PersonInfo safePerson;
        @ApiModelProperty("联系人")
        private PersonInfo contractPerson;
        @ApiModelProperty("企业简介")
        private String profile;
        @ApiModelProperty("生产信息")
        private List<CompProduct> products;
        @ApiModelProperty("危险化学品")
        private List<RiskChemical> riskChemicals;

        public String getCompName() {
            return compName;
        }

        public void setCompName(String compName) {
            this.compName = compName;
        }

        public String getRegAddress() {
            return regAddress;
        }

        public void setRegAddress(String regAddress) {
            this.regAddress = regAddress;
        }

        public String getCreditCode() {
            return creditCode;
        }

        public void setCreditCode(String creditCode) {
            this.creditCode = creditCode;
        }

        public PersonInfo getConPerson() {
            return conPerson;
        }

        public void setConPerson(PersonInfo conPerson) {
            this.conPerson = conPerson;
        }

        public PersonInfo getSafePerson() {
            return safePerson;
        }

        public void setSafePerson(PersonInfo safePerson) {
            this.safePerson = safePerson;
        }

        public PersonInfo getContractPerson() {
            return contractPerson;
        }

        public void setContractPerson(PersonInfo contractPerson) {
            this.contractPerson = contractPerson;
        }

        public String getProfile() {
            return profile;
        }

        public void setProfile(String profile) {
            this.profile = profile;
        }

        public List<CompProduct> getProducts() {
            return products;
        }

        public void setProducts(List<CompProduct> products) {
            this.products = products;
        }

        public List<RiskChemical> getRiskChemicals() {
            return riskChemicals;
        }

        public void setRiskChemicals(List<RiskChemical> riskChemicals) {
            this.riskChemicals = riskChemicals;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            CompBaseInfo that = (CompBaseInfo) o;
            return Objects.equals(compName, that.compName) &&
                    Objects.equals(regAddress, that.regAddress) &&
                    Objects.equals(creditCode, that.creditCode) &&
                    Objects.equals(conPerson, that.conPerson) &&
                    Objects.equals(safePerson, that.safePerson) &&
                    Objects.equals(contractPerson, that.contractPerson) &&
                    Objects.equals(profile, that.profile) &&
                    Objects.equals(products, that.products) &&
                    Objects.equals(riskChemicals, that.riskChemicals);
        }

        @Override
        public int hashCode() {
            return Objects.hash(compName, regAddress, creditCode, conPerson, safePerson, contractPerson, profile, products, riskChemicals);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("compName", compName)
                    .append("regAddress", regAddress)
                    .append("creditCode", creditCode)
                    .append("conPerson", conPerson)
                    .append("safePerson", safePerson)
                    .append("contractPerson", contractPerson)
                    .append("profile", profile)
                    .append("products", products)
                    .append("riskChemicals", riskChemicals)
                    .toString();
        }
    }

    /**
     * 安全生产社会化检查服务
     */
    public static class SafeProduct {
        @ApiModelProperty("基础管理隐患描述及治理措施")
        private List<CheckContent> baseContents;
        @ApiModelProperty("现场管理隐患描述及治理措施")
        private List<CheckContent> sceneContents;
        @ApiModelProperty("涉及特种人员和证书")
        private boolean spePerson;
        @ApiModelProperty("涉及特种人员和证书")
        private List<SpePerson> spePersons;
        @ApiModelProperty("检查判定企业综合安全风险状况")
        private int safeRiskLevel;
        @ApiModelProperty("风险判定说明")
        private String riskExplain;
        @ApiModelProperty("安全生产事故类型风险辨识")
        private String safeProductRisk;
        @ApiModelProperty("受检查企业意见")
        private String checkCompany;

        public List<CheckContent> getBaseContents() {
            return baseContents;
        }

        public void setBaseContents(List<CheckContent> baseContents) {
            this.baseContents = baseContents;
        }

        public List<CheckContent> getSceneContents() {
            return sceneContents;
        }

        public void setSceneContents(List<CheckContent> sceneContents) {
            this.sceneContents = sceneContents;
        }

        public boolean isSpePerson() {
            return spePerson;
        }

        public void setSpePerson(boolean spePerson) {
            this.spePerson = spePerson;
        }

        public List<SpePerson> getSpePersons() {
            return spePersons;
        }

        public void setSpePersons(List<SpePerson> spePersons) {
            this.spePersons = spePersons;
        }

        public int getSafeRiskLevel() {
            return safeRiskLevel;
        }

        public void setSafeRiskLevel(int safeRiskLevel) {
            this.safeRiskLevel = safeRiskLevel;
        }

        public String getRiskExplain() {
            return riskExplain;
        }

        public void setRiskExplain(String riskExplain) {
            this.riskExplain = riskExplain;
        }

        public String getSafeProductRisk() {
            return safeProductRisk;
        }

        public void setSafeProductRisk(String safeProductRisk) {
            this.safeProductRisk = safeProductRisk;
        }

        public String getCheckCompany() {
            return checkCompany;
        }

        public void setCheckCompany(String checkCompany) {
            this.checkCompany = checkCompany;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            SafeProduct that = (SafeProduct) o;
            return spePerson == that.spePerson &&
                    safeRiskLevel == that.safeRiskLevel &&
                    Objects.equals(baseContents, that.baseContents) &&
                    Objects.equals(sceneContents, that.sceneContents) &&
                    Objects.equals(spePersons, that.spePersons) &&
                    Objects.equals(riskExplain, that.riskExplain) &&
                    Objects.equals(safeProductRisk, that.safeProductRisk) &&
                    Objects.equals(checkCompany, that.checkCompany);
        }

        @Override
        public int hashCode() {
            return Objects.hash(baseContents, sceneContents, spePerson, spePersons, safeRiskLevel, riskExplain, safeProductRisk, checkCompany);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("baseContents", baseContents)
                    .append("sceneContents", sceneContents)
                    .append("spePerson", spePerson)
                    .append("spePersons", spePersons)
                    .append("safeRiskLevel", safeRiskLevel)
                    .append("riskExplain", riskExplain)
                    .append("safeProductRisk", safeProductRisk)
                    .append("checkCompany", checkCompany)
                    .toString();
        }
    }
}
