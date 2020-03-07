package com.ts.server.safe.report.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Objects;

/**
 * 复检报告
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class InsReport {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("初检报告编号")
    private String iniId;
    @ApiModelProperty("初检开始日期")
    private String iniDateFrom;
    @ApiModelProperty("初检结束日期")
    private String iniDateTo;
    @ApiModelProperty("检查开始日期")
    private String checkDateFrom;
    @ApiModelProperty("检查结束日期")
    private String checkDateTo;
    @ApiModelProperty("报告负责人")
    private Person reportPerson;
    @ApiModelProperty("报告审核人")
    private Person auditPerson;


    /**
     * 人员信息
     */
    public static class Person {
        @ApiModelProperty("人员编号")
        private String id;
        @ApiModelProperty("姓名")
        private String name;
        @ApiModelProperty("专业")
        private String profession;
        @ApiModelProperty("职称")
        private String proLevel;
        @ApiModelProperty("手机")
        private String mobile;

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

        public String getProfession() {
            return profession;
        }

        public void setProfession(String profession) {
            this.profession = profession;
        }

        public String getProLevel() {
            return proLevel;
        }

        public void setProLevel(String proLevel) {
            this.proLevel = proLevel;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Person person = (Person) o;
            return Objects.equals(id, person.id) &&
                    Objects.equals(name, person.name) &&
                    Objects.equals(profession, person.profession) &&
                    Objects.equals(proLevel, person.proLevel) &&
                    Objects.equals(mobile, person.mobile);
        }

        @Override
        public int hashCode() {
            return Objects.hash(id, name, profession, proLevel, mobile);
        }

        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("id", id)
                    .append("name", name)
                    .append("profession", profession)
                    .append("proLevel", proLevel)
                    .append("mobile", mobile)
                    .toString();
        }
    }
}
