package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniCheckType;
import io.swagger.annotations.ApiModelProperty;

/**
 * 修改检查类别
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniCheckTypeUpdateForm extends UniCheckTypeSaveForm {
    @ApiModelProperty("编号")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public UniCheckType toDomain() {
        UniCheckType t =  super.toDomain();

        t.setId(id);

        return t;
    }
}
