package com.ts.server.safe.controller.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotEmpty;

/**
 * 批量删除提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class BatchDeleteForm {
    @ApiModelProperty(value = "删除数据编号集合", required = true)
    @NotEmpty
    private String[] ids;

    public String[] getIds() {
        return ids;
    }

    public void setIds(String[] ids) {
        this.ids = ids;
    }
}
