package com.ts.server.safe.controller.main.man;

import com.ts.server.safe.channel.domain.Member;
import com.ts.server.safe.channel.service.MemberService;
import com.ts.server.safe.controller.form.PasswordUpdateForm;
import com.ts.server.safe.controller.main.logger.AccountManLogDetailBuilder;
import com.ts.server.safe.controller.main.man.form.AccountManForm;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.logger.aop.annotation.EnableApiLogger;
import com.ts.server.safe.security.Credential;
import com.ts.server.safe.security.CredentialContextUtils;
import com.ts.server.safe.security.annotation.ApiACL;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 申报端基础API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/account")
@ApiACL("ROLE_AUTHENTICATION")
@Api(value = "/man/account", tags = "申报端基础API接口")
public class AccountDecController {

    private final MemberService memberService;

    @Autowired
    public AccountDecController(MemberService memberService) {
        this.memberService = memberService;
    }


    @PostMapping(value = "updatePassword", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "修改申报员密码", buildDetail = AccountManLogDetailBuilder.UpdatePasswordBuilder.class)
    @ApiOperation("修改申报员密码")
    public ResultVo<OkVo> updatePassword(@Validated @RequestBody PasswordUpdateForm form){
        String id = getCredential().getId();
        return ResultVo.success(new OkVo(memberService.updatePassword(id, form.getPassword(), form.getNewPassword())));
    }

    @PutMapping(value = "account", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "修改管理员信息", buildDetail = AccountManLogDetailBuilder.UpdateAccountBuilder.class)
    @ApiOperation("修改申报员信息")
    public ResultVo<Member> updateAccount(@Validated @RequestBody AccountManForm form){
        Member m = memberService.get(getCredential().getId());
        m.setName(form.getName());
        m.setPhone(form.getPhone());

        return ResultVo.success(memberService.update(m));
    }

    private Credential getCredential(){
        return CredentialContextUtils.getCredential();
    }
}
