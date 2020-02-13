package com.ts.server.safe.company.controller.man.form;

import com.ts.server.safe.company.domain.OccDiseaseJob;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 修改职业病岗位
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class OccDiseaseJobUpdateForm extends OccDiseaseJobSaveForm {
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
    public OccDiseaseJob toDomain() {
        OccDiseaseJob t = super.toDomain();

        t.setId(id);

        return t;
    }
}
