package com.ts.server.safe.company.controller.man.form;

import com.ts.server.safe.company.domain.RiskChemical;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 修改危化品存储提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class RiskChemicalUpdateForm {
    @ApiModelProperty(value = "编号", required = true)
    @NotBlank
    private String id;
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    private String name;
    @ApiModelProperty(value = "别名")
    private String alias;
    @ApiModelProperty(value = "CAS")
    private String cas;
    @ApiModelProperty(value = "最多存储量")
    private String maxStore;
    @ApiModelProperty(value = "备注")
    private String remark;

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

    public String getMaxStore() {
        return maxStore;
    }

    public void setMaxStore(String maxStore) {
        this.maxStore = maxStore;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public RiskChemical toDomain(){
        RiskChemical t = new RiskChemical();

        t.setId(id);
        t.setName(name);
        t.setAlias(alias);
        t.setCas(cas);
        t.setMaxStore(maxStore);
        t.setRemark(remark);

        return t;
    }

}
