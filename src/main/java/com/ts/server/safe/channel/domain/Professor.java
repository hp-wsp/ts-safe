package com.ts.server.safe.channel.domain;

import io.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.Date;
import java.util.Objects;

/**
 * 专家
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class Professor {
    @ApiModelProperty("编号")
    private String id;
    @ApiModelProperty("服务商编号")
    private String channelId;
    @ApiModelProperty("姓名")
    private String name;
    @ApiModelProperty("出生年月日")
    private String birthday;
    @ApiModelProperty("擅长领域")
    private String profession;
    @ApiModelProperty("联系电话")
    private String phone;
    @ApiModelProperty("创建时间")
    private Date createTime;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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
        Professor professor = (Professor) o;
        return Objects.equals(id, professor.id) &&
                Objects.equals(channelId, professor.channelId) &&
                Objects.equals(name, professor.name) &&
                Objects.equals(birthday, professor.birthday) &&
                Objects.equals(profession, professor.profession) &&
                Objects.equals(phone, professor.phone) &&
                Objects.equals(createTime, professor.createTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, channelId, name, birthday, profession, phone, createTime);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("channelId", channelId)
                .append("name", name)
                .append("birthday", birthday)
                .append("profession", profession)
                .append("phone", phone)
                .append("createTime", createTime)
                .toString();
    }
}
