package com.ts.server.safe.controller.vo;

/**
 * 查询记录存在
 *
 * @author <a href="mailto:hhywangwei@gmail.com">WangWei</a>
 */
public class HasVo<T> {
    private final boolean has;
    private final T data;

    private HasVo(boolean has, T data) {
        this.has = has;
        this.data = data;
    }

    public boolean isHas() {
        return has;
    }

    public T getData() {
        return data;
    }

    public static <T> HasVo<T> has(T t){
        return new HasVo<>(true, t);
    }

    public static <T> HasVo<T> notHas(){
        return new HasVo<>(false, null);
    }
}
