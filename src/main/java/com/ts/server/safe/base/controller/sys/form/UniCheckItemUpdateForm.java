package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniCheckItem;
import io.swagger.annotations.ApiModelProperty;

/**
 * 修改检查项目
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniCheckItemUpdateForm extends UniCheckItemSaveForm {
    @ApiModelProperty("编号")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UniCheckItem toDomain(){
        UniCheckItem t = super.toDomain();

        t.setId(id);

        return t;
    }
}
