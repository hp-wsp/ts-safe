package com.ts.server.safe.controller.main.sys;

import com.ts.server.safe.BaseException;
import com.ts.server.safe.base.domain.Manager;
import com.ts.server.safe.base.service.ManagerService;
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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * 管理端通用API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/main")
@Api(value = "/sys/main", tags = "S-管理端Main API接口")
public class MainSysController {

    private final ManagerService managerService;
    private final TokenService tokenService;
    private final LoginLimitService loginLimitService;
    private final KaptchaService kaptchaService;

    @Autowired
    public MainSysController(ManagerService managerService, TokenService tokenService,
                             LoginLimitService loginLimitService, KaptchaService kaptchaService) {

        this.managerService = managerService;
        this.tokenService = tokenService;
        this.loginLimitService = loginLimitService;
        this.kaptchaService= kaptchaService;
    }

    @PostMapping(value = "login", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "管理员登录", type = "loginMain", buildDetail = LoginLogDetailBuilder.class, obtainUsername = ObtainLoginUsername.class)
    @ApiOperation("管理员登录")
    public ResultVo<LoginVo<Manager>> login(@Valid @RequestBody LoginForm form, HttpServletRequest request){

        //验证码验证
        int failCount = loginLimitService.incFail(form.getUsername());
        if(failCount > 3){
            if(StringUtils.isBlank(form.getCode())){
                throw new BaseException(103, "验证码不能为空");
            }
            if(!kaptchaService.validate(form.getCodeKey(),form.getCode())){
                throw new BaseException(104, "验证码错误");
            }
        }

        return managerService.getValidate(form.getUsername(), form.getPassword())
                .map(e -> {
                    loginLimitService.resetFail(form.getUsername());
                    Credential credential = new Credential(e.getId(), e.getUsername(), buildRoles(e.getRoles()));
                    String token = tokenService.generate(credential);
                    return ResultVo.success(new LoginVo<>(token, e));
                }).orElseThrow(() -> {
                    int errCode = loginLimitService.getFail(form.getUsername()) >= 3? 102: 101;
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
    @ApiOperation("管理员退出")
    public ResultVo<OkVo> logout(@RequestHeader(value = "token", required = false) String token,
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
