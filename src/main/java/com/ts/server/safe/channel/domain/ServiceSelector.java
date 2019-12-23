package com.ts.server.safe.channel.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 服务选项
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public enum ServiceSelector {

    SAFE_CONSULT(100, "企业安全生产标准咨询"),
    SAFE_TRUST(101, "企业安全生产托管"),
    SAFE_CHECK(102, "安全生产隐患排查"),
    SAFE_TRAIN(103, "安全生产教育培训"),
    SCHEME_GUIDE(201, "诊治方案编制与指导"),
    PRE_PLAN_GUIDE(202, "安全生产事故应急预案编制的咨询辅导"),
    HEALTH_GUIDE(301, "职业卫生基础档案辅导"),
    OD_HARM(302, "职业病危害预评价"),
    OD_CONTROLLER(303, "职业病危害控制效果评价"),
    OD_STATUS(304, "职业病危害危害因素现状评价"),
    OD_FENCE(305, "职业病防护设施设计专篇"),
    SAFE_PRE(401, "安全预评价"),
    SAFE_STATUS(402, "安全现状评价"),
    SAFE_ACCEPT(403, "安全验收评价"),
    RISK_PROXY(501, "危险品经验许可事项待办"),
    OIL_PROXY(502, "成品油经营许可事项待办"),
    PRO_SAFE_TUTOR(601, "建设项目安全设施\"三同时\"辅导咨询"),
    PRO_PROFESSION_TUTOR(602, "建设项目职业卫生\"三同时\"辅导咨询");

    private final int id;
    private final String name;

    ServiceSelector(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .toString();
    }
}
