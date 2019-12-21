package com.ts.server.safe.controller.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 重置用户密码
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@ApiModel("重置密码")
public class PasswordResetForm {
    @NotBlank
    @ApiModelProperty(value = "用户编号", required = true)
    private String id;
    @NotBlank
    @Size(min = 8, max = 20)
    @ApiModelProperty(value = "新密码", required = true)
    private String newPassword;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }
}
