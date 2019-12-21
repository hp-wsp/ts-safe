package com.ts.server.safe.controller.main.logger;

import com.ts.server.safe.base.domain.Manager;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.logger.aop.annotation.ApiLogDetailBuilder;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 构建管理基础日志
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class AccountSysLogDetailBuilder {

    /**
     * 构建修改密码日志
     */
    public static class UpdatePasswordBuilder implements ApiLogDetailBuilder {
        @Override
        @SuppressWarnings("unchecked")
        public String build(JoinPoint joinPoint, ServletRequestAttributes attributes, Object returnObj) {
            ResultVo<OkVo> result = (ResultVo<OkVo>)returnObj;
            return String.format("修改密码:%s", result.getRs().isOk()? "成功": "失败");
        }
    }

    /**
     * 更新管理员信息
     */
    public static class UpdateAccountBuilder implements ApiLogDetailBuilder {

        @Override
        @SuppressWarnings("unchecked")
        public String build(JoinPoint joinPoint, ServletRequestAttributes attributes, Object returnObj) {
            ResultVo<Manager> result = (ResultVo<Manager>)returnObj;
            Manager m = result.getRs();
            return String.format("用户名:%s;姓名:%s;电话:%s", m.getUsername(), m.getName(), m.getPhone());
        }
    }
}
