package com.ts.server.safe.base.controller.sys;

import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.base.controller.logger.SpeIndustryLogDetailBuilder;
import com.ts.server.safe.base.controller.sys.form.UniSpeIndustrySaveForm;
import com.ts.server.safe.base.controller.sys.form.UniSpeIndustryUpdateForm;
import com.ts.server.safe.base.domain.UniSpeIndustry;
import com.ts.server.safe.base.service.UniSpeIndustryService;
import com.ts.server.safe.logger.aop.annotation.EnableApiLogger;
import com.ts.server.safe.security.annotation.ApiACL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理特种行业API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@ApiACL("ROLE_SYS")
@RequestMapping("/sys/speIndustry")
@Api(value = "/sys/speIndustry", tags = "S-管理特种行业API接口")
public class UniSpeIndustrySysController {
    private final UniSpeIndustryService service;

    @Autowired
    public UniSpeIndustrySysController(UniSpeIndustryService service) {
        this.service = service;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "新增特种行业", buildDetail = SpeIndustryLogDetailBuilder.SaveBuilder.class)
    @ApiOperation("新增特种行业")
    public ResultVo<UniSpeIndustry> save(@Valid @RequestBody UniSpeIndustrySaveForm form){
        return ResultVo.success(service.save(form.toDomain()));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "修改特种行业", buildDetail = SpeIndustryLogDetailBuilder.UpdateBuilder.class)
    @ApiOperation("修改特种行业")
    public ResultVo<UniSpeIndustry> update(@Valid @RequestBody UniSpeIndustryUpdateForm form){
        return ResultVo.success(service.update(form.toDomain()));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "删除特种行业", buildDetail = SpeIndustryLogDetailBuilder.DeleteBuilder.class)
    @ApiOperation("删除特种行业")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        return ResultVo.success(new OkVo(service.delete(id)));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到特种行业")
    public ResultVo<UniSpeIndustry> get(@PathVariable("id")String id){
        return ResultVo.success(service.get(id));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询特种行业")
    public ResultPageVo<UniSpeIndustry> query(
            @ApiParam(value = "名称") @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        return new ResultPageVo.Builder<>(page, rows, service.query(name, page * rows, rows))
                .count(isCount, () -> service.count(name))
                .build();
    }
}
