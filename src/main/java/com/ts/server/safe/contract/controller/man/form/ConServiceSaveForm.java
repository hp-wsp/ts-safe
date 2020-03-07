package com.ts.server.safe.contract.controller.man.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.List;

/**
 * 新增合同服务提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ConServiceSaveForm {
    @ApiModelProperty(value = "服务名称", required = true)
    @NotBlank
    private String name;
    @ApiModelProperty(value = "服务企业编号", required =  true)
    @NotBlank
    private String compId;
    @ApiModelProperty(value = "合同编号", required = true)
    @NotBlank
    private String conId;
    @ApiModelProperty(value = "负责人编号", required = true)
    @NotBlank
    private String leaId;
    @ApiModelProperty(value = "服务项目集合")
    @Valid
    private List<ConServiceItemForm> items;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public String getConId() {
        return conId;
    }

    public void setConId(String conId) {
        this.conId = conId;
    }

    public String getLeaId() {
        return leaId;
    }

    public void setLeaId(String leaId) {
        this.leaId = leaId;
    }

    public List<ConServiceItemForm> getItems() {
        return items;
    }

    public void setItems(List<ConServiceItemForm> items) {
        this.items = items;
    }

    public static class ConServiceItemForm {
        @ApiModelProperty(value = "服务项目编号", required = true)
        private Integer itemId;
        @ApiModelProperty(value = "服务项目名称", required = true)
        private String itemName;
        @ApiModelProperty(value = "服务项目值")
        private String itemValue;

        public Integer getItemId() {
            return itemId;
        }

        public void setItemId(Integer itemId) {
            this.itemId = itemId;
        }

        public String getItemName() {
            return itemName;
        }

        public void setItemName(String itemName) {
            this.itemName = itemName;
        }

        public String getItemValue() {
            return itemValue;
        }

        public void setItemValue(String itemValue) {
            this.itemValue = itemValue;
        }
    }
}
