package com.ts.server.safe.base.controller.logger;

import com.ts.server.safe.base.controller.sys.form.UniCheckContentUpdateForm;
import com.ts.server.safe.base.domain.UniCheckContent;
import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.logger.aop.annotation.ApiLogDetailBuilder;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 构建检查表操作日志
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class UniCheckTableLogDetailBuilder {

    /**
     * 构建删除检查表日志
     */
    public final static class DeleteBuilder implements ApiLogDetailBuilder {
        @Override
        @SuppressWarnings("unchecked")
        public String build(JoinPoint joinPoint, ServletRequestAttributes attributes, Object returnObj) {
            ResultVo<OkVo> result = (ResultVo<OkVo>)returnObj;
            boolean ok = result.getRs().isOk();
            String id = (String)joinPoint.getArgs()[0];
            return String.format("删除:%s;编号:%s", ok?"成功":"失败", id);
        }
    };

    /**
     * 构建新增检查表日志
     */
    public final static class SaveBuilder implements ApiLogDetailBuilder {
        @Override
        @SuppressWarnings("unchecked")
        public String build(JoinPoint joinPoint, ServletRequestAttributes attributes, Object returnObj) {
            joinPoint.getSignature();
            ResultVo<UniCheckContent> result = (ResultVo<UniCheckContent>)returnObj;
            UniCheckContent t = result.getRs();
            return String.format("编号:%s;检查内容:%s", t.getId(), t.getContent());
        }
    }

    /**
     * 构建修改检查表日志
     */
    public final static class UpdateBuilder implements ApiLogDetailBuilder {
        @Override
        public String build(JoinPoint joinPoint, ServletRequestAttributes attributes, Object returnObj) {
            UniCheckContentUpdateForm form = (UniCheckContentUpdateForm) joinPoint.getArgs()[0];
            return String.format("编号:%s;检查内容:%s", form.getId(), form.toDomain().getContent());
        }
    }
}
