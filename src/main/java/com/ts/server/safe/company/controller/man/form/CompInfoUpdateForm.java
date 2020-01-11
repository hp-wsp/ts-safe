package com.ts.server.safe.company.controller.man.form;

import com.ts.server.safe.company.domain.CompInfo;
import io.swagger.annotations.ApiModelProperty;

/**
 * 修改公司信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CompInfoUpdateForm extends CompInfoSaveForm {
    @ApiModelProperty(value = "编号")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CompInfo toDomain(){
        CompInfo t = super.toDomain();
        t.setId(id);

        return t;
    }
}
