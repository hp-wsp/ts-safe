package com.ts.server.safe.channel.controller.man.form;

import com.ts.server.safe.channel.domain.Member;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 修改服务商人员数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class MemberUpdateForm {
    @ApiModelProperty(value = "编号", required = true)
    @NotBlank
    private String id;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "联系电话", required = true)
    @NotBlank
    private String phone;
    @ApiModelProperty(value = "角色")
    private String[] roles;
    @ApiModelProperty(value = "用户状态", required = true)
    @NotBlank
    private String status;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Member toDomain(){
        Member t = new Member();

        t.setId(id);
        t.setName(name);
        t.setPhone(phone);
        t.setRoles(roles);
        t.setStatus(Member.Status.valueOf(status));

        return t;
    }
}
