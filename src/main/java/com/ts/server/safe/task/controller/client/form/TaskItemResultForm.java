package com.ts.server.safe.task.controller.client.form;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * 任务结果提交数据
 *
 * <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class TaskItemResultForm {
    @ApiModelProperty(value = "编号", required = true)
    @NotBlank
    private String id;
    @ApiModelProperty(value = "检查结果",required = true)
    @NotBlank @Pattern(regexp = "^WAIT$|^PASS$|^NOT_PASS$")
    @NotBlank
    private String checkResult;
    @ApiModelProperty(value = "隐患级别", required = true)
    @NotBlank @Pattern(regexp = "^NONE$|^COMMON$|^GREAT$")
    private String dangerLevel;
    @ApiModelProperty(value = "隐患描述")
    private String danDescribe;
    @ApiModelProperty(value = "整改建议")
    private String danSuggest;
    @ApiModelProperty(value = "图片")
    private String[] images;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(String checkResult) {
        this.checkResult = checkResult;
    }

    public String getDangerLevel() {
        return dangerLevel;
    }

    public void setDangerLevel(String dangerLevel) {
        this.dangerLevel = dangerLevel;
    }

    public String getDanDescribe() {
        return danDescribe;
    }

    public void setDanDescribe(String danDescribe) {
        this.danDescribe = danDescribe;
    }

    public String getDanSuggest() {
        return danSuggest;
    }

    public void setDanSuggest(String danSuggest) {
        this.danSuggest = danSuggest;
    }

    public String[] getImages() {
        return images;
    }

    public void setImages(String[] images) {
        this.images = images;
    }
}
