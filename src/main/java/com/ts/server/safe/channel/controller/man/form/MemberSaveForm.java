package com.ts.server.safe.channel.controller.man.form;

import com.ts.server.safe.channel.domain.Member;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 *  新增服务商用户提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class MemberSaveForm {
    @ApiModelProperty(value = "用户名", required = true)
    @NotBlank
    private String username;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "联系电话", required = true)
    @NotBlank
    private String phone;
    @ApiModelProperty(value = "登录密码", required = true)
    @NotBlank
    private String password;
    @ApiModelProperty(value = "角色")
    private String[] roles;

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

    public Member toDomain(){
        Member t = new Member();

        t.setUsername(username);
        t.setName(name);
        t.setPhone(phone);
        t.setPassword(password);
        t.setRoles(roles);
        t.setStatus(Member.Status.ACTIVE);

        return t;
    }
}
