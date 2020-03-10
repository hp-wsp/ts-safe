package com.ts.server.safe.task.controller.man.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ts.server.safe.task.domain.TaskCheck;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 新增检查任务
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class TaskCheckSaveForm {
    @NotBlank
    @ApiModelProperty(value = "服务编号", required = true)
    private String serviceId;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "检查开始时间", required = true)
    private Date checkTimeFrom;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @ApiModelProperty(value = "检查结束时间",required = true)
    private Date checkTimeTo;
    @NotEmpty
    @ApiModelProperty(value = "检查人员编号集合", required = true)
    private String[] checkUserIds;
    @NotEmpty
    @ApiModelProperty(value = "检查项目")
    private List<@Valid ItemForm> items;
    @ApiModelProperty(value = "复查以前查出的隐患")
    private boolean review;

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public Date getCheckTimeFrom() {
        return checkTimeFrom;
    }

    public void setCheckTimeFrom(Date checkTimeFrom) {
        this.checkTimeFrom = checkTimeFrom;
    }

    public Date getCheckTimeTo() {
        return checkTimeTo;
    }

    public void setCheckTimeTo(Date checkTimeTo) {
        this.checkTimeTo = checkTimeTo;
    }

    public String[] getCheckUserIds() {
        return checkUserIds;
    }

    public void setCheckUserIds(String[] checkUserIds) {
        this.checkUserIds = checkUserIds;
    }

    public List<ItemForm> getItems() {
        return items;
    }

    public void setItems(List<ItemForm> items) {
        this.items = items;
    }

    public boolean isReview() {
        return review;
    }

    public void setReview(boolean review) {
        this.review = review;
    }

    public TaskCheck toDomain(){
        TaskCheck t = new TaskCheck();

        t.setServiceId(serviceId);
        t.setCheckTimeFrom(checkTimeFrom);
        t.setCheckTimeTo(checkTimeTo);
        t.setCheckUsers(buildUsers());
        t.setReview(review);

        return t;
    }

    private List<TaskCheck.CheckUser> buildUsers(){
        return Arrays.stream(checkUserIds).map(e -> {
            TaskCheck.CheckUser t = new TaskCheck.CheckUser();
            t.setId(e);
            return t;
        }).collect(Collectors.toList());
    }

    @Valid
    public static class ItemForm {
        @ApiModelProperty("检查类别编号")
        @NotBlank
        private String typeId;
        @ApiModelProperty("检查类别名称")
        @NotBlank
        private String typeName;
        @ApiModelProperty("检查内容")
        @NotBlank
        private String content;
        @ApiModelProperty("检查内容明细")
        private String conDetail;
        @ApiModelProperty("法律依据")
        private String lawItem;

        public String getTypeId() {
            return typeId;
        }

        public void setTypeId(String typeId) {
            this.typeId = typeId;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
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

        public String getLawItem() {
            return lawItem;
        }

        public void setLawItem(String lawItem) {
            this.lawItem = lawItem;
        }
    }
}
