package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniCheckType;
import io.swagger.annotations.ApiModelProperty;

/**
 * 新增检查类别
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniCheckTypeSaveForm {
    @ApiModelProperty("检查类别名称")
    private String name;
    @ApiModelProperty("备注")
    private String remark;

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

    public UniCheckType toDomain(){
        UniCheckType t = new UniCheckType();

        t.setName(name);
        t.setRemark(remark);

        return t;
    }
}
