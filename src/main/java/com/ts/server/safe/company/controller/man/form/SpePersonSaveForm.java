package com.ts.server.safe.company.controller.man.form;

import com.ts.server.safe.company.domain.SpePerson;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增特殊作业人员和证书数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SpePersonSaveForm {
    @ApiModelProperty(value = "企业编号", required = true)
    @NotBlank
    private String compId;
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    private String name;
    @ApiModelProperty(value = "工种", required = true)
    @NotBlank
    private String type;
    @ApiModelProperty("证书号")
    private String num;
    @ApiModelProperty("证书有效期开始日期")
    private String fromDate;
    @ApiModelProperty("证书有效期结束日期")
    private String toDate;

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }

    public SpePerson toDomain(){
        SpePerson t = new SpePerson();

        t.setCompId(compId);
        t.setName(name);
        t.setNum(num);
        t.setType(type);
        t.setFromDate(fromDate);
        t.setToDate(toDate);

        return t;
    }
}
