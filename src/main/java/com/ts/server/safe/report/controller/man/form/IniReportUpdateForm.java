package com.ts.server.safe.report.controller.man.form;

import com.ts.server.safe.report.domain.IniReport;
import io.swagger.annotations.ApiModelProperty;

/**
 * 修改检查报表提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class IniReportUpdateForm extends IniReportSaveForm {
    @ApiModelProperty("编号")
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public IniReport toDomain() {
        IniReport t = super.toDomain();

        t.setId(id);

        return t;
    }
}
