package com.ts.server.safe.controller.main.sys;

import com.ts.server.safe.base.domain.Manager;
import com.ts.server.safe.base.service.ManagerService;
import com.ts.server.safe.controller.form.PasswordUpdateForm;
import com.ts.server.safe.controller.main.logger.AccountSysLogDetailBuilder;
import com.ts.server.safe.controller.main.sys.form.AccountSysForm;
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
 * 管理端账户API接口
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
@RestController
@RequestMapping("/sys/account")
@ApiACL("ROLE_AUTHENTICATION")
@Api(value = "/sys/account", tags = "S-管理端账户API接口")
public class AccountSysController {

    private final ManagerService managerService;

    @Autowired
    public AccountSysController(ManagerService managerService) {
        this.managerService = managerService;
    }

    @PostMapping(value = "updatePassword", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "修改管理员密码", buildDetail = AccountSysLogDetailBuilder.UpdatePasswordBuilder.class)
    @ApiOperation("修改密码")
    public ResultVo<OkVo> updatePassword(@Validated @RequestBody PasswordUpdateForm form){
        String id = getCredential().getId();
        return ResultVo.success(new OkVo(managerService.updatePassword(id, form.getPassword(), form.getNewPassword())));
    }

    @PutMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @EnableApiLogger(name = "修改管理员信息", buildDetail = AccountSysLogDetailBuilder.UpdateAccountBuilder.class)
    @ApiOperation("修改管理员信息")
    public ResultVo<Manager> updateAccount(@Validated @RequestBody AccountSysForm form){
        Manager m = managerService.get(getCredential().getId());
        m.setName(form.getName());
        m.setPhone(form.getPhone());
        m.setEmail(form.getEmail());

        return ResultVo.success(managerService.update(m));
    }

    private Credential getCredential(){
        return CredentialContextUtils.getCredential();
    }
}
