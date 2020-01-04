package com.ts.server.safe.channel.controller.man;

import com.ts.server.safe.base.service.DistrictService;
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
    private final DistrictService districtService;

    @Autowired
    public ChannelManController(ChannelService service, DistrictService districtService) {
        this.service = service;
        this.districtService = districtService;
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
        setDistrict(t);
        return ResultVo.success(service.update(t));
    }

    private void setDistrict(Channel t){
        districtService.setDistrictName(t.getProvinceId(), e -> t.setProvince(e.getName()), "省份不存在");
        districtService.setDistrictName(t.getCityId(), e -> t.setCity(e.getName()), "城市不存在");
        districtService.setDistrictName(t.getCountryId(), e -> t.setCountry(e.getName()), "县区不存在");
    }
    
    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
