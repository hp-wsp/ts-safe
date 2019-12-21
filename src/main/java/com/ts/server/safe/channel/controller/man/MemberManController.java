package com.ts.server.safe.channel.controller.man;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.controller.man.form.MemberSaveForm;
import com.ts.server.safe.channel.controller.man.form.MemberUpdateForm;
import com.ts.server.safe.channel.controller.logger.MemberLogDetailBuilder;
import com.ts.server.safe.channel.domain.Member;
import com.ts.server.safe.channel.service.MemberService;
import com.ts.server.safe.controller.credential.ManCredential;
import com.ts.server.safe.controller.form.PasswordResetForm;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultPageVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.logger.aop.annotation.EnableApiLogger;
import com.ts.server.safe.security.CredentialContextUtils;
import com.ts.server.safe.security.annotation.ApiACL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理用户API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/member")
@ApiACL({"ROLE_MAN_SYS"})
@Api(value = "/man/member", tags = "M-管理用户API接口")
public class MemberManController {
    private final MemberService service;

    @Autowired
    public MemberManController(MemberService service) {
        this.service = service;
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "添加用户", buildDetail = MemberLogDetailBuilder.SaveBuilder.class)
    @ApiOperation("添加用户")
    public ResultVo<Member> save(@Valid @RequestBody MemberSaveForm form){
        Member t = form.toDomain();
        t.setChannelId(getCredential().getChannelId());
        Member member = service.save(t);
        return ResultVo.success(member);
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "修改添加用户", buildDetail = MemberLogDetailBuilder.UpdateBuilder.class)
    @ApiOperation("修改添加用户")
    public ResultVo<Member> update(@Valid @RequestBody MemberUpdateForm form){
        validateChannel(form.getId());
        Member member = service.update(form.toDomain());
        return ResultVo.success(member);
    }

    /**
     * 验证用户所属服务商
     *
     * @param id 用户编号
     */
    private void validateChannel(String id){
        Member m = service.get(id);
        String channelId = getCredential().getChannelId();
        if(!StringUtils.equals(m.getChannelId(), channelId)){
            throw new BaseException("不允许操作");
        }
    }

    @DeleteMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "删除添加用户", buildDetail = MemberLogDetailBuilder.DeleteBuilder.class)
    @ApiOperation("删除添加用户")
    public ResultVo<OkVo> delete(@PathVariable("id") String id){
        validateChannel(id);
        boolean ok = service.delete(id);
        return ResultVo.success(new OkVo(ok));
    }

    @GetMapping(value = "{id}", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("得到添加用户")
    public ResultVo<Member> get(@PathVariable("id") String id){
        return ResultVo.success(service.get(id));
    }

    @PutMapping(value = "resetPassword", produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "重置密码", buildDetail = MemberLogDetailBuilder.ResetPasswordBuilder.class)
    @ApiOperation("重置密码")
    public ResultVo<OkVo> resetPassword(@Valid @RequestBody PasswordResetForm form){
        validateChannel(form.getId());
        boolean ok = service.resetPassword(form.getId(), form.getNewPassword());
        return ResultVo.success(new OkVo(ok));
    }

    @PutMapping(value = "forbid/{id}", produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "禁用用户", buildDetail = MemberLogDetailBuilder.ForbidOrActiveBuilder.class)
    @ApiOperation("禁用用户")
    public ResultVo<Member> forbid(@PathVariable("id")String id){
        validateChannel(id);
        return ResultVo.success(service.forbid(id));
    }

    @PutMapping(value = "active/{id}", produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "激活用户", buildDetail = MemberLogDetailBuilder.ForbidOrActiveBuilder.class)
    @ApiOperation("激活用户")
    public ResultVo<Member> active(@PathVariable("id")String id){
        validateChannel(id);
        return ResultVo.success(service.active(id));
    }

    @GetMapping(value = "members", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询所有激活用户")
    public ResultVo<List<Member>> queryMembers(){
        String channelId = getCredential().getChannelId();
        return ResultVo.success(service.queryActiveMembers(channelId));
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @ApiOperation("查询用户")
    public ResultPageVo<Member> query(
            @ApiParam(value = "用户名") @RequestParam(required = false) String username,
            @ApiParam(value = "电话号码") @RequestParam(required = false) String phone,
            @RequestParam(defaultValue = "0") @ApiParam(value = "查询页数") int page,
            @RequestParam(defaultValue = "true") @ApiParam(value = "是否得到查询记录数") boolean isCount,
            @RequestParam(defaultValue = "15") @ApiParam(value = "查询每页记录数") int rows){

        String channelId = getCredential().getChannelId();
        return new ResultPageVo.Builder<>(page, rows, service.query(channelId, username, phone, page * rows, rows))
                .count(isCount, () -> service.count(channelId, username, phone))
                .build();
    }

    private ManCredential getCredential(){
        return (ManCredential) CredentialContextUtils.getCredential();
    }
}
