package com.ts.server.safe.contract.controller.man.form;

import com.ts.server.safe.contract.domain.Contract;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 修改合同提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ContractUpdateForm extends ContractSaveForm {
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
    public Contract toDomain() {
        Contract t = super.toDomain();

        t.setId(id);

        return t;
    }
}
