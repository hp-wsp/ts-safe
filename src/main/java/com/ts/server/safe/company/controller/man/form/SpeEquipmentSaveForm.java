package com.ts.server.safe.company.controller.man.form;

import com.ts.server.safe.company.domain.SpeEquipment;
import io.swagger.annotations.ApiModelProperty;

/**
 * 新增企业特殊设备
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class SpeEquipmentSaveForm {
    @ApiModelProperty(value = "企业编号", required = true)
    private String compId;
    @ApiModelProperty(value = "设备名称", required = true)
    private String name;
    @ApiModelProperty("出厂编号")
    private String num;
    @ApiModelProperty("设备隐患")
    private String devDanger;
    @ApiModelProperty("备注")
    private String remark;

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
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

        t.setCompId(compId);
        t.setName(name);
        t.setNum(num);
        t.setDevDanger(devDanger);
        t.setRemark(remark);

        return t;
    }
}
