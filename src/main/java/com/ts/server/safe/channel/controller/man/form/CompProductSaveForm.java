package com.ts.server.safe.channel.controller.man.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

/**
 * 企业生产信息
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class CompProductSaveForm {
    @ApiModelProperty("企业编号")
    private String compId;
    @ApiModelProperty("生产信息集合")
    private List<ProductForm> products = new ArrayList<>();

    public String getCompId() {
        return compId;
    }

    public void setCompId(String compId) {
        this.compId = compId;
    }

    public List<ProductForm> getProducts() {
        return products;
    }

    public void setProducts(List<ProductForm> products) {
        this.products = products;
    }

    @Valid
    public static class ProductForm {
        @ApiModelProperty(value = "key", required = true)
        @NotBlank
        private String key;
        @ApiModelProperty(value = "值", required = true)
        @NotNull
        private Integer value;

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public Integer getValue() {
            return value;
        }

        public void setValue(Integer value) {
            this.value = value;
        }

    }
}
