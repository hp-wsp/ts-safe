package com.ts.server.safe.base.controller.sys.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增检查表提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniCheckTableSaveForm {
    @ApiModelProperty(value = "检查类型编号", required = true)
    @NotBlank
    private String supId;
    @ApiModelProperty(value = "检查内容", required = true)
    @NotBlank
    private String content;
    @ApiModelProperty(value = "检查内容明细")
    private String conDetail;
    @ApiModelProperty(value = "法律条目")
    private String lawItem;
    @ApiModelProperty(value = "法律内容")
    private String lawContent;
    @ApiModelProperty("检查类型")
    private int checkType;
    @ApiModelProperty("显示排序")
    private int showOrder;
}
