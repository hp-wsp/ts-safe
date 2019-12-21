package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniSpeIndustry;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增特种行业提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniSpeIndustrySaveForm {
    @ApiModelProperty("名称")
    @NotBlank
    private String name;
    @ApiModelProperty("备注")
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

    public UniSpeIndustry toDomain(){
        UniSpeIndustry t = new UniSpeIndustry();

        t.setName(name);
        t.setRemark(remark);

        return t;
    }
}
