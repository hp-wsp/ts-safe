package com.ts.server.safe.channel.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

/**
 * 服务商用户
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class Member {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "服务渠道商编号")
    private String channelId;
    @ApiModelProperty(value = "用户名")
    private String username;
    @ApiModelProperty(value = "姓名")
    private String name;
    @ApiModelProperty(value = "联系电话")
    private String phone;
    @ApiModelProperty(value = "出生年月日")
    private String birthday;
    @ApiModelProperty(value = "擅长领域")
    private String profession;
    @ApiModelProperty(value = "登录密码")
    @JsonIgnore
    private String password;
    @ApiModelProperty(value = "是否是超级管理员")
    private boolean root;
    @ApiModelProperty(value = "角色")
    private String[] roles;
    @ApiModelProperty(value = "状态")
    private Status status;
    @ApiModelProperty(value = "修改时间")
    private Date updateTime;
    @ApiModelProperty(value = "创建时间")
    private Date createTime;

    public enum Status {
        INACTIVE, //未激活（服务商关闭)
        ACTIVE,
        FORBID    //禁用
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannelId() {
        return channelId;
    }

    public void setChannelId(String channelId) {
        this.channelId = channelId;
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

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public String[] getRoles() {
        return roles;
    }

    public void setRoles(String[] roles) {
        this.roles = roles;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
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
        Member member = (Member) o;
        return root == member.root &&
                Objects.equals(id, member.id) &&
                Objects.equals(channelId, member.channelId) &&
                Objects.equals(username, member.username) &&
                Objects.equals(name, member.name) &&
                Objects.equals(phone, member.phone) &&
                Objects.equals(birthday, member.birthday) &&
                Objects.equals(profession, member.profession) &&
                Objects.equals(password, member.password) &&
                Arrays.equals(roles, member.roles) &&
                status == member.status &&
                Objects.equals(updateTime, member.updateTime) &&
                Objects.equals(createTime, member.createTime);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(id, channelId, username, name, phone, birthday, profession, password, root, status, updateTime, createTime);
        result = 31 * result + Arrays.hashCode(roles);
        return result;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("channelId", channelId)
                .append("username", username)
                .append("name", name)
                .append("phone", phone)
                .append("birthday", birthday)
                .append("profession", profession)
                .append("password", password)
                .append("root", root)
                .append("roles", roles)
                .append("status", status)
                .append("updateTime", updateTime)
                .append("createTime", createTime)
                .toString();
    }
}
