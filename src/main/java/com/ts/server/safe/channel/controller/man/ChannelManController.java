package com.ts.server.safe.channel.controller.man;

import com.ts.server.safe.channel.controller.man.form.ChannelUpdateForm;
import com.ts.server.safe.channel.domain.Channel;
import com.ts.server.safe.channel.service.ChannelService;
import com.ts.server.safe.controller.credential.ManCredential;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.security.CredentialContextUtils;
import com.ts.server.safe.security.annotation.ApiACL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 服务商信息管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@ApiACL("ROLE_MAN_SYS")
@RequestMapping("/man/channel")
@Api(value = "/man/channel", tags = "M-服务商信息管理")
public class ChannelManController {
    private final ChannelService service;

    @Autowired
    public ChannelManController(ChannelService service) {
        this.service = service;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到服务商信息")
    public ResultVo<Channel> get(){
        String channelId = getCredential().getChannelId();
        Channel t = service.get(channelId);
        return ResultVo.success(t);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("修改服务商信息")
    public ResultVo<Channel> update(@Valid @RequestBody ChannelUpdateForm form){
        String channelId = getCredential().getChannelId();
        Channel t = form.toDomain();
        t.setId(channelId);
        return ResultVo.success(service.update(t));
    }
    
    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
