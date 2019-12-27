package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniCheckTable;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增检查表提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniCheckTableSaveForm {
    @ApiModelProperty(value = "检查内容", required = true)
    @NotBlank
    private String content;
    @ApiModelProperty(value = "检查内容明细", required = true)
    @NotBlank
    private String conDetail;
    @ApiModelProperty(value = "检查依据", required = true)
    @NotBlank
    private String lawItem;
    @ApiModelProperty(value = "检查类型", required = true)
    @NotBlank
    private String checkType;
    @ApiModelProperty(value = "检查项目", required = true)
    @NotBlank
    private String checkItem;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getConDetail() {
        return conDetail;
    }

    public void setConDetail(String conDetail) {
        this.conDetail = conDetail;
    }

    public String getLawItem() {
        return lawItem;
    }

    public void setLawItem(String lawItem) {
        this.lawItem = lawItem;
    }

    public String getCheckType() {
        return checkType;
    }

    public void setCheckType(String checkType) {
        this.checkType = checkType;
    }

    public String getCheckItem() {
        return checkItem;
    }

    public void setCheckItem(String checkItem) {
        this.checkItem = checkItem;
    }

    public UniCheckTable toDomain(){
        UniCheckTable t = new UniCheckTable();

        t.setCheckType(checkType);
        t.setCheckItem(checkItem);
        t.setContent(content);
        t.setConDetail(conDetail);
        t.setLawItem(lawItem);

        return t;
    }
}
