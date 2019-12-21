package com.ts.server.safe.base.controller.logger;

import com.ts.server.safe.controller.vo.OkVo;
import com.ts.server.safe.controller.vo.ResultVo;
import com.ts.server.safe.base.controller.sys.form.UniRiskChemicalUpdateForm;
import com.ts.server.safe.base.domain.UniRiskChemical;
import com.ts.server.safe.logger.aop.annotation.ApiLogDetailBuilder;
import org.aspectj.lang.JoinPoint;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * 构建危化品目录操作日志
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class RiskChemistryLogDetailBuilder {
    /**
     * 构建删除危化品目录日志
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
     * 构建新增危化品目录日志
     */
    public final static class SaveBuilder implements ApiLogDetailBuilder {
        @Override
        @SuppressWarnings("unchecked")
        public String build(JoinPoint joinPoint, ServletRequestAttributes attributes, Object returnObj) {
            joinPoint.getSignature();
            ResultVo<UniRiskChemical> result = (ResultVo<UniRiskChemical>)returnObj;
            UniRiskChemical t = result.getRs();
            return String.format("编号:%s;名称:%s", t.getId(), t.getName());
        }
    }

    /**
     * 构建修改危化品目录日志
     */
    public final static class UpdateBuilder implements ApiLogDetailBuilder {
        @Override
        public String build(JoinPoint joinPoint, ServletRequestAttributes attributes, Object returnObj) {
            UniRiskChemicalUpdateForm form = (UniRiskChemicalUpdateForm) joinPoint.getArgs()[0];
            return String.format("编号:%s;详情:%s", form.getId(), form.toDomain().toString());
        }
    }
}
