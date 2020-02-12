package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniRisk;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增危险识别提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniRiskSaveForm {
    @ApiModelProperty(value = "名称", required = true)
    @NotBlank
    private String name;
    @ApiModelProperty(value = "备注")
    private String remark;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UniRisk toDomain(){
        UniRisk t = new UniRisk();

        t.setName(name);
        t.setRemark(remark);

        return t;
    }
}
