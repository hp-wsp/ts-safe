package com.ts.server.safe.controller.main.form;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 登陆提交数据
 *
 * @author WangWei
 */
@ApiModel("登陆提交数据")
public class LoginForm {
    @NotBlank
    @ApiModelProperty(value = "登陆用户名", required = true)
    private String username;
    @NotBlank
    @ApiModelProperty(value = "登陆密码", required = true)
    private String password;
    @ApiModelProperty(value = "验证码key")
    private String codeKey;
    @ApiModelProperty(value = "验证码")
    private String code;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCodeKey() {
        return codeKey;
    }

    public void setCodeKey(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
