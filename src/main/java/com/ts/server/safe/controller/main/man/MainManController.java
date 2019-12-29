package com.ts.server.safe.controller.main.man;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.channel.domain.Member;
import com.ts.server.safe.channel.service.MemberService;
import com.ts.server.safe.controller.credential.ManCredential;
import com.ts.server.safe.controller.main.form.LoginForm;
import com.ts.server.safe.controller.main.logger.LoginLogDetailBuilder;
import com.ts.server.safe.controller.main.logger.ObtainLoginUsername;
import com.ts.server.safe.controller.main.vo.LoginVo;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.logger.aop.annotation.EnableApiLogger;
import com.ts.server.safe.security.Credential;
import com.ts.server.safe.security.authenticate.GlobalRole;
import com.ts.server.safe.security.kaptcha.KaptchaService;
import com.ts.server.safe.security.limit.LoginLimitService;
import com.ts.server.safe.security.token.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 服务商端通用API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/man/main")
@Api(value = "/man/main", tags = "M-服务商端通用API接口")
public class MainManController {

    private final MemberService memberService;
    private final TokenService tokenService;
    private final LoginLimitService loginLimitService;
    private final KaptchaService kaptchaService;

    @Autowired
    public MainManController(MemberService memberService, TokenService tokenService,
                             LoginLimitService loginLimitService, KaptchaService kaptchaService) {

        this.memberService = memberService;
        this.tokenService = tokenService;
        this.loginLimitService = loginLimitService;
        this.kaptchaService= kaptchaService;
    }


    @PostMapping(value = "login", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "用户登录", type = "loginMan", buildDetail = LoginLogDetailBuilder.class, obtainUsername = ObtainLoginUsername.class)
    @ApiOperation("申报人员登录")
    public ResultVo<LoginVo<Member>> login(@Valid @RequestBody LoginForm form){

        //放置于管理员用户名冲突
        String username = String.format("DEC_%s", form.getUsername());
        int failCount = loginLimitService.incFail(username);
        //验证码验证
        if(failCount > 3){
            if(StringUtils.isBlank(form.getCode())){
                throw new BaseException(103, "验证码不能为空");
            }
            if(!kaptchaService.validate(form.getCodeKey(),form.getCode())){
                throw new BaseException(104, "验证码错误");
            }
        }

        return memberService.getValidate(form.getUsername(), form.getPassword())
                .map(e -> {
                    loginLimitService.resetFail(username);
                    Credential credential = new ManCredential(e.getId(), e.getUsername(), buildRoles(e.getRoles()), e.getChannelId());
                    String token = tokenService.generate(credential);
                    return ResultVo.success(new LoginVo<>(token, e));
                }).orElseThrow(() -> {
                    int errCode = failCount >= 3? 102: 101;
                    return new BaseException(errCode, "用户名或密码错误");
                });
    }

    private List<String> buildRoles(String[] roles){
        List<String> list = new ArrayList<>(roles.length + 1);

        Collections.addAll(list, roles);
        list.add(GlobalRole.ROLE_AUTHENTICATION.name());

        return list;
    }

    @GetMapping(value = "logout", produces = APPLICATION_JSON_VALUE)
    @ApiOperation("用户退出")
    public ResultVo<OkVo> memberLogout(@RequestHeader(value = "token", required = false) String token,
                                       @RequestHeader(value = "Authorization", required = false) String auth){
        String t = "";
        if(StringUtils.isNotBlank(token)){
            t = token;
        }
        if(StringUtils.isNotBlank(auth)){
            t = StringUtils.trim(StringUtils.removeStart(auth, "Bearer"));
        }
        if(StringUtils.isNotBlank(t)){
            tokenService.destroy(token);
        }
        return ResultVo.success(new OkVo(true));
    }
}
