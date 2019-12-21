package com.ts.server.safe.controller.main.man.form;

import io.swagger.annotations.ApiModelProperty;

/**
 * 申报员修改信息
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class AccountManForm {
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "联系电话")
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
