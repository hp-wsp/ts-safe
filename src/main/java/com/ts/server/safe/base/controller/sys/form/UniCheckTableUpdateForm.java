package com.ts.server.safe.base.controller.sys.form;

import com.ts.server.safe.base.domain.UniCheckTable;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 修改检查表提交数据
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniCheckTableUpdateForm {
    @ApiModelProperty(value = "编号", required = true)
    @NotBlank
    private String id;
    @ApiModelProperty(value = "检查内容", required = true)
    @NotBlank
    private String content;
    @ApiModelProperty(value = "检查内容明细")
    private String conDetail;
    @ApiModelProperty(value = "法律名称")
    private String lawName;
    @ApiModelProperty(value = "法律条目")
    private String lawItem;
    @ApiModelProperty(value = "法律内容")
    private String lawContent;
    @ApiModelProperty("检查类型")
    private int checkType;
    @ApiModelProperty("显示排序")
    private int showOrder;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getLawName() {
        return lawName;
    }

    public void setLawName(String lawName) {
        this.lawName = lawName;
    }

    public String getLawItem() {
        return lawItem;
    }

    public void setLawItem(String lawItem) {
        this.lawItem = lawItem;
    }

    public String getLawContent() {
        return lawContent;
    }

    public void setLawContent(String lawContent) {
        this.lawContent = lawContent;
    }

    public int getCheckType() {
        return checkType;
    }

    public void setCheckType(int checkType) {
        this.checkType = checkType;
    }

    public int getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(int showOrder) {
        this.showOrder = showOrder;
    }

    public UniCheckTable toDomain(){
        UniCheckTable t = new UniCheckTable();

        t.setId(id);
        t.setContent(content);
        t.setConDetail(conDetail);
        t.setLawName(lawName);
        t.setLawItem(lawItem);
        t.setLawContent(lawContent);
        t.setCheckType(checkType);
        t.setShowOrder(showOrder);

        return t;
    }
}
