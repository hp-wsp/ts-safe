package com.ts.server.safe.contract.controller.man.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 合同完成提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ContractCompleteForm {
    @ApiModelProperty("合同编号")
    @NotBlank
    private String id;
    @ApiModelProperty("是否完成")
    @NotNull
    private Boolean complete;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getComplete() {
        return complete;
    }

    public void setComplete(Boolean complete) {
        this.complete = complete;
    }
}
