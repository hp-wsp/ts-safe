package com.ts.server.safe.channel.controller.man.form;

import com.ts.server.safe.channel.domain.Professor;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增专家
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ProfessorSaveForm {
    @ApiModelProperty(value = "姓名", required = true)
    @NotBlank
    private String name;
    @ApiModelProperty(value = "联系电话", required = true)
    @NotBlank
    private String phone;
    @ApiModelProperty(value = "擅长领域")
    private String profession;
    @ApiModelProperty(value = "生日")
    private String birthday;

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

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Professor toDomain(String channelId){
        Professor t = new Professor();

        t.setChannelId(channelId);
        t.setName(name);
        t.setPhone(phone);
        t.setProfession(profession);
        t.setBirthday(birthday);

        return t;
    }
}
