package com.ts.server.safe.company.controller.man;

import com.ts.server.safe.base.domain.UniIndCtg;
import com.ts.server.safe.base.service.DistrictService;
import com.ts.server.safe.base.service.UniIndCtgService;
import com.ts.server.safe.company.controller.logger.CompInfoLogDetailBuilder;
import com.ts.server.safe.company.controller.man.form.CompInfoSaveForm;
import com.ts.server.safe.company.controller.man.form.CompInfoUpdateForm;
import com.ts.server.safe.company.domain.CompInfo;
import com.ts.server.safe.company.service.CompInfoService;
import com.ts.server.safe.controller.credential.ManCredential;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.logger.aop.annotation.EnableApiLogger;
import com.ts.server.safe.security.CredentialContextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 公司信息管理API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/company")
@Api(value = "/man/company", tags = "M-公司信息管理API接口")
public class CompInfoManController {
    private final CompInfoService service;
    private final DistrictService districtService;
    private final UniIndCtgService indCtgService;

    @Autowired
    public CompInfoManController(CompInfoService service, DistrictService districtService,
                                 UniIndCtgService indCtgService) {
        this.service = service;
        this.districtService = districtService;
        this.indCtgService = indCtgService;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "新增公司", buildDetail = CompInfoLogDetailBuilder.SaveBuilder.class)
    @ApiOperation("新增公司信息")
    public ResultVo<CompInfo> save(@Valid @RequestBody CompInfoSaveForm form){
        CompInfo t = form.toDomain();
        t.setChannelId(getCredential().getChannelId());
        setDistrict(t);
        setIndCtgs(t);
        return ResultVo.success(service.save(t));
    }

    private void setDistrict(CompInfo t){
        districtService.setDistrictName(t.getProvinceId(), e -> t.setProvince(e.getName()), "省份不存在");
        districtService.setDistrictName(t.getCityId(), e -> t.setCity(e.getName()), "城市不存在");
        districtService.setDistrictName(t.getCountryId(), e -> t.setCountry(e.getName()), "县区不存在");
    }

    private void setIndCtgs(CompInfo t){
        t.getIndCtgs().forEach(e -> {
            UniIndCtg s = indCtgService.get(e.getId());
            e.setName(s.getName());
        });
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "修改公司信息", buildDetail = CompInfoLogDetailBuilder.UpdateBuilder.class)
    @ApiOperation("修改公司信息")
    public ResultVo<CompInfo> update(@Valid @RequestBody CompInfoUpdateForm form){
        CompInfo t = form.toDomain();
        t.setChannelId(getCredential().getChannelId());
        setDistrict(t);
        setIndCtgs(t);
        return ResultVo.success(service.update(t));
    }

    @GetMapping(value = "hasName", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("公司名称是否存在")
    public ResultVo<OkVo> hasName(@RequestParam("name")String name){
        String channelId = getCredential().getChannelId();
        return ResultVo.success(new OkVo(service.hasName(channelId, name)));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "删除公司", buildDetail = CompInfoLogDetailBuilder.DeleteBuilder.class)
    @ApiOperation("删除公司信息")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        String channelId = getCredential().getChannelId();
        return ResultVo.success(new OkVo(service.delete(id, channelId)));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到公司信息")
    public ResultVo<CompInfo> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询服务商")
    public ResultPageVo<CompInfo> query(
            @ApiParam(value = "公司名称") @RequestParam(required = false)String name,
            @ApiParam(value = "省份") @RequestParam(required = false)String province,
            @ApiParam(value = "城市") @RequestParam(required = false)String city,
            @ApiParam(value = "县区") @RequestParam(required = false)String country,
            @ApiParam(value = "行业分类编号") @RequestParam(required = false)String indCtgId,
            @ApiParam(value = "联系人") @RequestParam(required = false)String contact,
            @ApiParam(value = "联系电话") @RequestParam(required = false) String phone,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

      String channelId = getCredential().getChannelId();
        return new ResultPageVo.Builder<>(page, rows, service.query(channelId, name, province, city, country, indCtgId, contact, phone, page * rows, rows))
                .count(isCount, () -> service.count(channelId, name, province, city, country, indCtgId, contact, phone))
                .build();
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
