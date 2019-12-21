package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniSpeIndustry;
import io.swagger.annotations.ApiModelProperty;

/**
 * 更新特种行业提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniSpeIndustryUpdateForm extends UniSpeIndustrySaveForm {
    @ApiModelProperty("编号")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public UniSpeIndustry toDomain(){
        UniSpeIndustry t = super.toDomain();

        t.setId(id);

        return t;
    }
}
