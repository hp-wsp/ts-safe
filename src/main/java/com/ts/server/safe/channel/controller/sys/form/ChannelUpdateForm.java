package com.ts.server.safe.channel.controller.sys.form;

import com.ts.server.safe.channel.domain.Channel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * 修改服务商提交数据
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class ChannelUpdateForm extends ChannelSaveForm {
    @NotBlank
    @ApiModelProperty(value = "编号", required = true)
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Channel toDomain() {
        Channel t= super.toDomain();
        t.setId(id);

        return t;
    }
}
