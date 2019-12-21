package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniRiskChemical;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增危化目录提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniRiskChemicalSaveForm {
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    private String name;
    @ApiModelProperty(value = "别名")
    private String alias;
    @ApiModelProperty(value = "CAS编号")
    private String cas;
    @ApiModelProperty(value = "备注")
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getCas() {
        return cas;
    }

    public void setCas(String cas) {
        this.cas = cas;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UniRiskChemical toDomain(){
        UniRiskChemical t = new UniRiskChemical();

        t.setName(name);
        t.setAlias(alias);
        t.setCas(cas);
        t.setRemark(remark);

        return  t;
    }
}
