package com.ts.server.safe.channel.controller.sys;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.controller.logger.MemberLogDetailBuilder;
import com.ts.server.safe.channel.domain.Member;
import com.ts.server.safe.channel.service.MemberService;
import com.ts.server.safe.controller.form.PasswordResetForm;
import com.ts.server.safe.controller.vo.HasVo;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.logger.aop.annotation.EnableApiLogger;
import com.ts.server.safe.security.annotation.ApiACL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
/**
 * 管理服务商用户API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/member")
@ApiACL({"ROLE_SYS"})
@Api(value = "/sys/member", tags = "S-管理服务商用户API接口")
public class MemberSysController {
    private final MemberService service;

    @Autowired
    public MemberSysController(MemberService service) {
        this.service = service;
    }

    @GetMapping(value = "root", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("获取服务商管理员")
    public ResultVo<Member> getRoot(@RequestParam("channelId")String channelId){
        return ResultVo.success(service.getRoot(channelId));
    }

    @GetMapping(value = "hasUsername", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("用户名是否存在")
    public ResultVo<OkVo> hasUsername(@RequestParam("username")String username){
        return ResultVo.success(new OkVo(service.hasUsername(username)));
    }

    @PutMapping(value = "resetPassword", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "重置管理员密码", buildDetail = MemberLogDetailBuilder.ResetPasswordBuilder.class)
    @ApiOperation("重置管理员密码")
    public ResultVo<OkVo> resetPassword(@Valid @RequestBody PasswordResetForm form){
        Member m = service.get(form.getId());
        if(!m.isRoot()){
            throw new BaseException("不能重置非管理员密码");
        }
        return ResultVo.success(new OkVo(service.resetPassword(form.getId(), form.getNewPassword())));
    }
}
