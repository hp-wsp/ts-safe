package com.ts.server.safe.report.domain;

import com.ts.server.safe.company.domain.CompProduct;
import com.ts.server.safe.company.domain.RiskChemical;
import com.ts.server.safe.company.domain.SpePerson;
import com.ts.server.safe.task.domain.TaskContent;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
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
    @ApiModelProperty("检查开始时间")
    private Date checkTimeFrom;
    @ApiModelProperty("检查结束时间")
    private Date checkTimeTo;
    @ApiModelProperty("编制说明")
    private ReportDetail reportDetail;
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

    public Date getCheckTimeFrom() {
        return checkTimeFrom;
    }

    public void setCheckTimeFrom(Date checkTimeFrom) {
        this.checkTimeFrom = checkTimeFrom;
    }

    public Date getCheckTimeTo() {
        return checkTimeTo;
    }

    public void setCheckTimeTo(Date checkTimeTo) {
        this.checkTimeTo = checkTimeTo;
    }

    public ReportDetail getReportDetail() {
        return reportDetail;
    }

    public void setReportDetail(ReportDetail reportDetail) {
        this.reportDetail = reportDetail;
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
        CheckReport report = (CheckReport) o;
        return entCompType == report.entCompType &&
                entScale == report.entScale &&
                Objects.equals(id, report.id) &&
                Objects.equals(channelId, report.channelId) &&
                Objects.equals(channelName, report.channelName) &&
                Objects.equals(compId, report.compId) &&
                Objects.equals(compName, report.compName) &&
                Objects.equals(taskId, report.taskId) &&
                Objects.equals(taskDetail, report.taskDetail) &&
                Objects.equals(serviceId, report.serviceId) &&
                Objects.equals(serviceName, report.serviceName) &&
                Objects.equals(cycleName, report.cycleName) &&
                Objects.equals(industry, report.industry) &&
                Objects.equals(area, report.area) &&
                Objects.equals(checkTimeFrom, report.checkTimeFrom) &&
                Objects.equals(checkTimeTo, report.checkTimeTo) &&
                Objects.equals(reportDetail, report.reportDetail) &&
                Objects.equals(compBaseInfo, report.compBaseInfo) &&
                Objects.equals(safeProduct, report.safeProduct) &&
                Objects.equals(updateTime, report.updateTime) &&
                Objects.equals(createTime, report.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channelId, channelName, compId, compName, taskId, taskDetail, serviceId, serviceName, cycleName, entCompType, industry, entScale, area, checkTimeFrom, checkTimeTo, reportDetail, compBaseInfo, safeProduct, updateTime, createTime);
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
                .append("checkTimeFrom", checkTimeFrom)
                .append("checkTimeTo", checkTimeTo)
                .append("reportDetail", reportDetail)
                .append("compBaseInfo", compBaseInfo)
                .append("safeProduct", safeProduct)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }

    /**
     * 编制说明
     */
    public static class ReportDetail {
        @ApiModelProperty("委托单位")
        private String entCompName;
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
        @ApiModelProperty("成员")
        private List<PersonInfo> workers = new ArrayList<>();
        @ApiModelProperty("主要负责人")
        private PersonInfo principalPerson;
        @ApiModelProperty("报告审核人")
        private PersonInfo auditPerson;
        @ApiModelProperty("报告编制人")
        private PersonInfo reportPerson;
        @ApiModelProperty("本次检查结果总结并致辞")
        private String checkResult;

        public String getEntCompName() {
            return entCompName;
        }

        public void setEntCompName(String entCompName) {
            this.entCompName = entCompName;
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

        public String getCheckResult() {
            return checkResult;
        }

        public void setCheckResult(String checkResult) {
            this.checkResult = checkResult;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ReportDetail that = (ReportDetail) o;
            return Objects.equals(entCompName, that.entCompName) &&
                    Objects.equals(conNum, that.conNum) &&
                    Objects.equals(cycleContent, that.cycleContent) &&
                    Objects.equals(serviceBase, that.serviceBase) &&
                    Objects.equals(serviceMethod, that.serviceMethod) &&
                    Objects.equals(serviceRange, that.serviceRange) &&
                    Objects.equals(chanName, that.chanName) &&
                    Objects.equals(chanAddress, that.chanAddress) &&
                    Objects.equals(chanBusScope, that.chanBusScope) &&
                    Objects.equals(chanPhone, that.chanPhone) &&
                    Objects.equals(chanMobile, that.chanMobile) &&
                    Objects.equals(chanContact, that.chanContact) &&
                    Objects.equals(leader, that.leader) &&
                    Objects.equals(workers, that.workers) &&
                    Objects.equals(principalPerson, that.principalPerson) &&
                    Objects.equals(auditPerson, that.auditPerson) &&
                    Objects.equals(reportPerson, that.reportPerson) &&
                    Objects.equals(checkResult, that.checkResult);
        }

        @Override
        public int hashCode() {
            return Objects.hash(entCompName, conNum, cycleContent, serviceBase, serviceMethod, serviceRange, chanName, chanAddress, chanBusScope, chanPhone, chanMobile, chanContact, leader, workers, principalPerson, auditPerson, reportPerson, checkResult);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("entCompName", entCompName)
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
                    .append("checkResult", checkResult)
                    .toString();
        }
    }

    /**
     * 人员信息
     */
    public static class PersonInfo {
        @ApiModelProperty("姓名")
        private String name = "";
        @ApiModelProperty("联系电话")
        private String phone = "";
        @ApiModelProperty("手机")
        private String mobile = "";

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
        private String compProfile;
        @ApiModelProperty("生产信息")
        private List<CompProduct> products = new ArrayList<>();
        @ApiModelProperty("涉及重点关注的工艺、场所、物料等情况描述")
        private String productDetail;
        @ApiModelProperty("危险化学品")
        private List<RiskChemical> riskChemicals = new ArrayList<>();

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

        public String getCompProfile() {
            return compProfile;
        }

        public void setCompProfile(String compProfile) {
            this.compProfile = compProfile;
        }

        public List<CompProduct> getProducts() {
            return products;
        }

        public void setProducts(List<CompProduct> products) {
            this.products = products;
        }

        public String getProductDetail() {
            return productDetail;
        }

        public void setProductDetail(String productDetail) {
            this.productDetail = productDetail;
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
                    Objects.equals(compProfile, that.compProfile) &&
                    Objects.equals(products, that.products) &&
                    Objects.equals(productDetail, that.productDetail) &&
                    Objects.equals(riskChemicals, that.riskChemicals);
        }

        @Override
        public int hashCode() {
            return Objects.hash(compName, regAddress, creditCode, conPerson, safePerson, contractPerson, compProfile, products, productDetail, riskChemicals);
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
                    .append("compProfile", compProfile)
                    .append("products", products)
                    .append("productDetail", productDetail)
                    .append("riskChemicals", riskChemicals)
                    .toString();
        }
    }

    /**
     * 安全生产社会化检查服务
     */
    public static class SafeProduct {
        @ApiModelProperty("基础管理隐患描述及治理措施")
        private List<TaskContent> baseContents = new ArrayList<>();
        @ApiModelProperty("现场管理隐患描述及治理措施")
        private List<TaskContent> sceneContents = new ArrayList<>();
        @ApiModelProperty("涉及特种人员和证书")
        private boolean spePerson = false;
        @ApiModelProperty("涉及特种人员和证书")
        private List<SpePerson> spePersons = new ArrayList<>();
        @ApiModelProperty("检查判定企业综合安全风险状况")
        private int safeRiskLevel = 0;
        @ApiModelProperty("风险判定说明")
        private String riskExplain;
        @ApiModelProperty("安全生产事故类型风险辨识")
        private String safeProductRisk;
        @ApiModelProperty("受检查企业意见")
        private String checkCompanyIdea;

        public List<TaskContent> getBaseContents() {
            return baseContents;
        }

        public void setBaseContents(List<TaskContent> baseContents) {
            this.baseContents = baseContents;
        }

        public List<TaskContent> getSceneContents() {
            return sceneContents;
        }

        public void setSceneContents(List<TaskContent> sceneContents) {
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

        public String getCheckCompanyIdea() {
            return checkCompanyIdea;
        }

        public void setCheckCompanyIdea(String checkCompanyIdea) {
            this.checkCompanyIdea = checkCompanyIdea;
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
                    Objects.equals(checkCompanyIdea, that.checkCompanyIdea);
        }

        @Override
        public int hashCode() {
            return Objects.hash(baseContents, sceneContents, spePerson, spePersons, safeRiskLevel, riskExplain, safeProductRisk, checkCompanyIdea);
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
                    .append("checkCompanyIdea", checkCompanyIdea)
                    .toString();
        }
    }
}
