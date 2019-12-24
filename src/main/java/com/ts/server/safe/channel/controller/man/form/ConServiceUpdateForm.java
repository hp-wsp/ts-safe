package com.ts.server.safe.channel.controller.man.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 修改合同服务提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ConServiceUpdateForm extends ConServiceSaveForm {
    @ApiModelProperty(value = "编号", required = true)
    @NotBlank
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
