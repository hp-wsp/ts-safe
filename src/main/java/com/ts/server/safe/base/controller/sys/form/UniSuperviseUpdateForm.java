package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniSupervise;
import io.swagger.annotations.ApiModelProperty;

/**
 * 修改监管行业分类
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniSuperviseUpdateForm extends UniSuperviseSaveForm {
    @ApiModelProperty("编号")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public UniSupervise toDomain() {
         UniSupervise t = super.toDomain();

         t.setId(id);

         return t;
    }
}
