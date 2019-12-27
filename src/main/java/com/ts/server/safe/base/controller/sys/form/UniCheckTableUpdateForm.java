package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniCheckTable;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 修改检查表提交数据
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniCheckTableUpdateForm extends UniCheckTableSaveForm{
    @ApiModelProperty(value = "编号", required = true)
    @NotBlank
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public UniCheckTable toDomain() {
        UniCheckTable t =  super.toDomain();

        t.setId(id);

        return t;
    }
}
