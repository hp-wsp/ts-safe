package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.Manager;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 新增管理员提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ManagerSaveForm {
    @ApiModelProperty(value = "用户名")
    @NotBlank
    private String username;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "联系电话")
    private String phone;
    @ApiModelProperty(value = "电子邮件")
    private String email;
    @ApiModelProperty(value = "登录密码")
    @NotBlank
    private String password;
    @ApiModelProperty(value = "用户角色")
    @NotEmpty
    private String[] roles;
    @ApiModelProperty(value = "是否禁用")
    @NotNull
    private Boolean forbid = false;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Boolean getForbid() {
        return forbid;
    }

    public void setForbid(Boolean forbid) {
        this.forbid = forbid;
    }

    public Manager toDomain(){
        Manager t = new Manager();

        t.setUsername(username);
        t.setName(name);
        t.setPassword(password);
        t.setPhone(phone);
        t.setEmail(email);
        t.setRoles(roles);
        t.setForbid(forbid);

        return t;
    }
}
