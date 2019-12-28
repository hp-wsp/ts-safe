package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniCheckItem;
import io.swagger.annotations.ApiModelProperty;

/**
 * 新增检查项目
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniCheckItemSaveForm {
    @ApiModelProperty("类别编号")
    private String typeId;
    @ApiModelProperty("名称")
    private String name;
    @ApiModelProperty("备注")
    private String remark;

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UniCheckItem toDomain(){
        UniCheckItem t = new UniCheckItem();

        t.setName(name);
        t.setTypeId(typeId);
        t.setRemark(remark);

        return t;
    }
}
