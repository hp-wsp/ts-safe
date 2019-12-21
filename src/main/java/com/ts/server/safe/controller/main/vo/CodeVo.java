package com.ts.server.safe.controller.main.vo;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 验证码VO
 *
 * @author <a href="mailto:hhywangwei@gamil.com">WangWei</a>
 */
public class CodeVo {
    private final String codeKey;

    public CodeVo(String codeKey) {
        this.codeKey = codeKey;
    }

    public String getCodeKey() {
        return codeKey;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("codeKey", codeKey)
                .toString();
    }
}
