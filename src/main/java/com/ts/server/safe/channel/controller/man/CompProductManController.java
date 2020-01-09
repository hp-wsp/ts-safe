package com.ts.server.safe.channel.controller.man;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.controller.man.form.CompProductSaveForm;
import com.ts.server.safe.channel.domain.CompInfo;
import com.ts.server.safe.channel.domain.CompProduct;
import com.ts.server.safe.channel.service.CompInfoService;
import com.ts.server.safe.channel.service.CompProductService;
import com.ts.server.safe.controller.credential.ManCredential;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.security.CredentialContextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理企业生产信息API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/compProduct")
@Api(value = "/man/compProduct", tags = "M-企业生产信息")
public class CompProductManController {
    private final CompProductService service;
    private final CompInfoService infoService;

    @Autowired
    public CompProductManController(CompProductService service, CompInfoService infoService) {
        this.service = service;
        this.infoService = infoService;
    }

    @PostMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("保存企业生产信息")
    public ResultVo<List<CompProduct>> save(@Valid @RequestBody CompProductSaveForm form){
        CompInfo info = infoService.get(form.getCompId());
        if(!StringUtils.equals(info.getChannelId(), getCredential().getChannelId())){
            throw new BaseException("不能添加企业生产信息");
        }
        List<CompProduct> products = form.getProducts().stream().map(e -> {
            CompProduct t = new CompProduct();
            t.setProKey(e.getKey());
            t.setProValue(e.getValue());
            return t;
        }).collect(Collectors.toList());

        List<CompProduct> data = service.save(info, products);

        return ResultVo.success(data);
    }

    @GetMapping(value = "{compId}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("获取企业生产信息")
    public ResultVo<List<CompProduct>> query(@PathVariable("compId")String compId){
        return ResultVo.success(service.query(compId));
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
