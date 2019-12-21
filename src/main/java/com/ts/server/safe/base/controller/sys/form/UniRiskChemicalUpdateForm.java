package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniRiskChemical;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 修改危化品目录提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniRiskChemicalUpdateForm extends UniRiskChemicalSaveForm {
    @ApiModelProperty(value = "编号", required = true)
    @NotBlank
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public UniRiskChemical toDomain() {
        UniRiskChemical t = super.toDomain();

        t.setId(id);

        return t;
    }
}
