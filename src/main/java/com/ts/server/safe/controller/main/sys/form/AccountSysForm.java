package com.ts.server.safe.controller.main.sys.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 管理员修改信息
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class AccountSysForm {
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "联系电话", required = true)
    @NotBlank
    private String phone;
    @ApiModelProperty(value = "电子邮件")
    private String email;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
