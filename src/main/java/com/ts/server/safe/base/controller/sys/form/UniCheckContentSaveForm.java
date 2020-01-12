package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniCheckContent;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 新增检查表提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniCheckContentSaveForm {
    @ApiModelProperty(value = "检查内容", required = true)
    @NotBlank
    private String content;
    @ApiModelProperty(value = "检查内容明细", required = true)
    @NotBlank
    private String conDetail;
    @ApiModelProperty(value = "检查依据", required = true)
    @NotBlank
    private String lawItem;
    @ApiModelProperty(value = "检查项目编号", required = true)
    @NotBlank
    private String itemId;

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

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public UniCheckContent toDomain(){
        UniCheckContent t = new UniCheckContent();

        t.setContent(content);
        t.setConDetail(conDetail);
        t.setLawItem(lawItem);
        t.setItemId(itemId);

        return t;
    }
}
