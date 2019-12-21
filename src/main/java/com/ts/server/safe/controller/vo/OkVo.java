package com.ts.server.safe.controller.vo;

/**
 * 执行是否成功
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class OkVo {
    private final boolean ok;

    public OkVo(boolean ok) {
        this.ok = ok;
    }

    public boolean isOk() {
        return ok;
    }
}
