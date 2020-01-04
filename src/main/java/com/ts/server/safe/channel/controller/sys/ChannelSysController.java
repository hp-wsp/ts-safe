package com.ts.server.safe.channel.controller.sys;

import com.ts.server.safe.base.service.DistrictService;
import com.ts.server.safe.channel.controller.sys.form.ChannelSaveForm;
import com.ts.server.safe.channel.controller.sys.form.ChannelUpdateForm;
import com.ts.server.safe.channel.controller.logger.ChannelLogDetailBuilder;
import com.ts.server.safe.channel.domain.Channel;
import com.ts.server.safe.channel.service.ChannelService;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.logger.aop.annotation.EnableApiLogger;
import com.ts.server.safe.security.annotation.ApiACL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 服务商管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/channel")
@ApiACL({"ROLE_SYS"})
@Api(value = "/sys/channel", tags = "S-服务商管理API接口")
public class ChannelSysController {
    private final ChannelService service;
    private final DistrictService districtService;

    @Autowired
    public ChannelSysController(ChannelService service, DistrictService districtService) {
        this.service = service;
        this.districtService = districtService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "新增服务商", buildDetail = ChannelLogDetailBuilder.SaveBuilder.class)
    @ApiOperation("新增服务商")
    public ResultVo<Channel> save(@Valid @RequestBody ChannelSaveForm form){
        Channel t = form.toDomain();
        setDistrict(t);
        Channel n = service.save(t, form.getManUsername(), form.getManPassword());
        return ResultVo.success(n);
    }

    private void setDistrict(Channel t){
        districtService.setDistrictName(t.getProvinceId(), e -> t.setProvince(e.getName()), "省份不存在");
        districtService.setDistrictName(t.getCityId(), e -> t.setCity(e.getName()), "城市不存在");
        districtService.setDistrictName(t.getCountryId(), e -> t.setCountry(e.getName()), "县区不存在");
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "修改服务商", buildDetail = ChannelLogDetailBuilder.UpdateBuilder.class)
    @ApiOperation("修改服务商")
    public ResultVo<Channel> update(@Valid @RequestBody ChannelUpdateForm form){
        Channel t = form.toDomain();
        setDistrict(t);
        Channel n = service.update(t);
        return ResultVo.success(n);
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "删除服务商", buildDetail = ChannelLogDetailBuilder.DeleteBuilder.class)
    @ApiOperation("删除服务商")
    public ResultVo<OkVo> delete(@PathVariable("id") String id){
        boolean ok = service.delete(id);
        return ResultVo.success(new OkVo(ok));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到服务商")
    public ResultVo<Channel> get(@PathVariable("id") String id){
        return ResultVo.success(service.get(id));
    }

    @PutMapping(value = "open/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("开放服务商")
    public ResultVo<Channel> open(@PathVariable("id")String id){
        return ResultVo.success(service.open(id));
    }

    @PutMapping(value = "close/{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("关闭服务商")
    public ResultVo<Channel> close(@PathVariable("id")String id){
        return ResultVo.success(service.close(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询服务商")
    public ResultPageVo<Channel> query(
            @ApiParam(value = "服务商名称") @RequestParam(required = false) String name,
            @ApiParam(value = "状态") @RequestParam(required = false)String status,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        Channel.Status statusObj = StringUtils.isBlank(status)? null: Channel.Status.valueOf(status);
        return new ResultPageVo.Builder<>(page, rows, service.query( name, statusObj,page * rows, rows))
                .count(isCount, () -> service.count(name, statusObj))
                .build();
    }
}