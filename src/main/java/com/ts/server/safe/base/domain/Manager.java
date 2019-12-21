package com.ts.server.safe.base.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 系统管理员
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class Manager {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "联系电话")
    private String phone;
    @ApiModelProperty(value = "电子邮件")
    private String email;
    @ApiModelProperty(value = "登录密码")
    @JsonIgnore
    private String password;
    @ApiModelProperty(value = "用户角色")
    private String[] roles;
    @ApiModelProperty(value = "用户是否禁用")
    private boolean forbid;
    @ApiModelProperty(value = "是超级管理员")
    private boolean root;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public boolean isForbid() {
        return forbid;
    }

    public void setForbid(boolean forbid) {
        this.forbid = forbid;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Manager manager = (Manager) o;
        return forbid == manager.forbid &&
                root == manager.root &&
                Objects.equals(id, manager.id) &&
                Objects.equals(username, manager.username) &&
                Objects.equals(name, manager.name) &&
                Objects.equals(phone, manager.phone) &&
                Objects.equals(email, manager.email) &&
                Objects.equals(password, manager.password) &&
                Arrays.equals(roles, manager.roles) &&
                Objects.equals(updateTime, manager.updateTime) &&
                Objects.equals(createTime, manager.createTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, username, name, phone, email, password, forbid, root, updateTime, createTime);
        result = 31 * result + Arrays.hashCode(roles);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("username", username)
                .append("name", name)
                .append("phone", phone)
                .append("email", email)
                .append("password", password)
                .append("roles", roles)
                .append("forbid", forbid)
                .append("root", root)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
