package com.ts.server.safe.company.controller.man.form;

import com.ts.server.safe.company.domain.SpeEquipment;
import io.swagger.annotations.ApiModelProperty;

public class SpeEquipmentUpdateForm {
    @ApiModelProperty(value = "编号", required = true)
    private String id;
    @ApiModelProperty(value = "设备名称", required = true)
    private String name;
    @ApiModelProperty("出厂编号")
    private String num;
    @ApiModelProperty("设备隐患")
    private String devDanger;
    @ApiModelProperty("备注")
    private String remark;

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

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDevDanger() {
        return devDanger;
    }

    public void setDevDanger(String devDanger) {
        this.devDanger = devDanger;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public SpeEquipment toDomain(){
        SpeEquipment t = new SpeEquipment();

        t.setId(id);
        t.setName(name);
        t.setNum(num);
        t.setDevDanger(devDanger);
        t.setRemark(remark);

        return t;
    }
}
