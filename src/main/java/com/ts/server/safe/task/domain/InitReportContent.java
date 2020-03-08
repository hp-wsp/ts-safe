package com.ts.server.safe.task.domain;

import com.ts.server.safe.company.domain.CompProduct;
import com.ts.server.safe.company.domain.OccDiseaseJob;
import com.ts.server.safe.company.domain.RiskChemical;
import com.ts.server.safe.company.domain.SpePerson;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.*;

/**
 * 初检报告内容
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class InitReportContent {
    @ApiModelProperty(value = "服务商名称")
    private String channelName;
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
    @ApiModelProperty("检查开始时间")
    private String checkTimeFrom;
    @ApiModelProperty("检查结束时间")
    private String checkTimeTo;
    @ApiModelProperty("编制说明")
    private ReportDetail reportDetail;
    @ApiModelProperty("受检企业基本情况")
    private CompBaseInfo compBaseInfo;
    @ApiModelProperty("安全生产社会化检查服务")
    private SafeProduct safeProduct;
    @ApiModelProperty("报告份数说明")
    private String printDetail;
    @ApiModelProperty("修改时间")

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getCompName() {
        return compName;
    }

    public void setCompName(String compName) {
        this.compName = compName;
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

    public String getCheckTimeFrom() {
        return checkTimeFrom;
    }

    public void setCheckTimeFrom(String checkTimeFrom) {
        this.checkTimeFrom = checkTimeFrom;
    }

    public String getCheckTimeTo() {
        return checkTimeTo;
    }

    public void setCheckTimeTo(String checkTimeTo) {
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

    public String getPrintDetail() {
        return printDetail;
    }

    public void setPrintDetail(String printDetail) {
        this.printDetail = printDetail;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("channelName", channelName)
                .append("compName", compName)
                .append("entCompType", entCompType)
                .append("industry", industry)
                .append("entScale", entScale)
                .append("area", area)
                .append("checkTimeFrom", checkTimeFrom)
                .append("checkTimeTo", checkTimeTo)
                .append("reportDetail", reportDetail)
                .append("compBaseInfo", compBaseInfo)
                .append("safeProduct", safeProduct)
                .append("printDetail", printDetail)
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
        private List<CheckItem> baseContents = new ArrayList<>();
        @ApiModelProperty("现场管理隐患描述及治理措施")
        private List<CheckItem> sceneContents = new ArrayList<>();
        @ApiModelProperty("职业健康危害因素")
        private List<OccDiseaseJob> occDiseaseJobs = new ArrayList<>();
        @ApiModelProperty("职业病危害风险分类辨识")
        private int occDiseaseLevel = 0;
        @ApiModelProperty("涉及特种人员和证书")
        private boolean spePerson = false;
        @ApiModelProperty("涉及特种人员和证书")
        private List<SpePerson> spePersons = new ArrayList<>();
        @ApiModelProperty("检查情况结论意见")
        private String checkResult;
        @ApiModelProperty("检查判定企业的综合安全风险状况")
        private int safeRiskLevel = 0;
        @ApiModelProperty("风险等级判定说明")
        private String riskLevelExplain;
        @ApiModelProperty("生产安全事故类型风险辨识")
        private String safeProductRisk;
        @ApiModelProperty("受检查企业意见")
        private String checkCompanyIdea;
        @ApiModelProperty("本报告相关附图")
        private String[] images = new String[0];

        public List<CheckItem> getBaseContents() {
            return baseContents;
        }

        public void setBaseContents(List<CheckItem> baseContents) {
            this.baseContents = baseContents;
        }

        public List<CheckItem> getSceneContents() {
            return sceneContents;
        }

        public void setSceneContents(List<CheckItem> sceneContents) {
            this.sceneContents = sceneContents;
        }

        public List<OccDiseaseJob> getOccDiseaseJobs() {
            return occDiseaseJobs;
        }

        public void setOccDiseaseJobs(List<OccDiseaseJob> occDiseaseJobs) {
            this.occDiseaseJobs = occDiseaseJobs;
        }

        public int getOccDiseaseLevel() {
            return occDiseaseLevel;
        }

        public void setOccDiseaseLevel(int occDiseaseLevel) {
            this.occDiseaseLevel = occDiseaseLevel;
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

        public String getCheckResult() {
            return checkResult;
        }

        public void setCheckResult(String checkResult) {
            this.checkResult = checkResult;
        }

        public int getSafeRiskLevel() {
            return safeRiskLevel;
        }

        public void setSafeRiskLevel(int safeRiskLevel) {
            this.safeRiskLevel = safeRiskLevel;
        }

        public String getRiskLevelExplain() {
            return riskLevelExplain;
        }

        public void setRiskLevelExplain(String riskLevelExplain) {
            this.riskLevelExplain = riskLevelExplain;
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

        public String[] getImages() {
            return images;
        }

        public void setImages(String[] images) {
            this.images = images;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("baseContents", baseContents)
                    .append("sceneContents", sceneContents)
                    .append("occDiseaseJobs", occDiseaseJobs)
                    .append("occDiseaseLevel", occDiseaseLevel)
                    .append("spePerson", spePerson)
                    .append("spePersons", spePersons)
                    .append("checkResult", checkResult)
                    .append("safeRiskLevel", safeRiskLevel)
                    .append("riskLevelExplain", riskLevelExplain)
                    .append("safeProductRisk", safeProductRisk)
                    .append("checkCompanyIdea", checkCompanyIdea)
                    .append("images", images)
                    .toString();
        }
    }

    public static class CheckItem {
        @ApiModelProperty("隐患描述")
        private String danDescribe;
        @ApiModelProperty("整改建议")
        private String danSuggest;
        @ApiModelProperty("隐患图片")
        private String[] images;
        @ApiModelProperty("备注")
        private String remark;

        public String getDanDescribe() {
            return danDescribe;
        }

        public void setDanDescribe(String danDescribe) {
            this.danDescribe = danDescribe;
        }

        public String getDanSuggest() {
            return danSuggest;
        }

        public void setDanSuggest(String danSuggest) {
            this.danSuggest = danSuggest;
        }

        public String[] getImages() {
            return images;
        }

        public void setImages(String[] images) {
            this.images = images;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("danDescribe", danDescribe)
                    .append("danSuggest", danSuggest)
                    .append("images", images)
                    .append("remark", remark)
                    .toString();
        }
    }
}
