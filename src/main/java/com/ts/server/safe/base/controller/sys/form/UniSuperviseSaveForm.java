package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniSupervise;
import io.swagger.annotations.ApiModelProperty;

/**
 * 新增监管行业分类提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniSuperviseSaveForm {
    @ApiModelProperty("分类编号")
    private String num;
    @ApiModelProperty("分类名称")
    private String name;
    @ApiModelProperty("上级分类编号")
    private String parentId;
    @ApiModelProperty("备注")
    private String remark;

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public UniSupervise toDomain(){
        UniSupervise t = new UniSupervise();

        t.setName(name);
        t.setNum(num);
        t.setParentId(parentId);
        t.setRemark(remark);

        return t;
    }
}
