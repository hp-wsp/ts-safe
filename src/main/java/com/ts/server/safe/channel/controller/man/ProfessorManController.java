package com.ts.server.safe.channel.controller.man;

import com.ts.server.safe.channel.controller.man.form.ProfessorSaveForm;
import com.ts.server.safe.channel.controller.man.form.ProfessorUpdateForm;
import com.ts.server.safe.channel.domain.Professor;
import com.ts.server.safe.channel.service.ProfessorService;
import com.ts.server.safe.controller.credential.ManCredential;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.security.CredentialContextUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 专家管理API接口
 *
 * @author <a href="mailto:hhywangwei@mgail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/professor")
@Api(value = "/man/professor", tags = "M-专家管理API接口")
public class ProfessorManController {
    private final ProfessorService service;

    @Autowired
    public ProfessorManController(ProfessorService service) {
        this.service = service;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("新增专家")
    public ResultVo<Professor> save(@Valid @RequestBody ProfessorSaveForm form){
        ManCredential credential = getCredential();
        Professor t = form.toDomain(credential.getChannelId());
        return ResultVo.success(service.save(t));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ApiOperation("修改专家")
    public ResultVo<Professor> update(@Valid @RequestBody ProfessorUpdateForm form){
        ManCredential credential = getCredential();
        Professor t = form.toDomain(credential.getChannelId());
        return ResultVo.success(service.update(t));
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("删除专家")
    public ResultVo<OkVo> delete(@PathVariable("id")String id){
        ManCredential credential = getCredential();
        return ResultVo.success(new OkVo(service.delete(id, credential.getChannelId())));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询专家")
    public ResultPageVo<Professor> query(
            @ApiParam(value = "用户名") @RequestParam(required = false) String name,
            @ApiParam(value = "电话号码") @RequestParam(required = false) String phone,
            @ApiParam(value = "擅长领域") @RequestParam(required = false) String profession,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String channelId = getCredential().getChannelId();
        return new ResultPageVo.Builder<>(page, rows, service.query(channelId, name, phone, profession,page * rows, rows))
                .count(isCount, () -> service.count(channelId, name, phone, profession))
                .build();
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
