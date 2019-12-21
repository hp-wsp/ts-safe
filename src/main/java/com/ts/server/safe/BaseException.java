package com.ts.server.safe;

/**
 * 自定义异常
 *
 * @author <a href="hhywangwei@gmail.com">WangWei</a>
 */
public class BaseException extends RuntimeException {

    private final int code;
    private String type;

    public BaseException(String msg) {
        this(1000, msg);
        this.type = "";
    }

    public BaseException(int code, String msg){
        super(msg);
        this.code = code;
    }

    @Deprecated
    public BaseException(String type, String msg) {
        this(1000, msg);
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public int getCode() {
        return code;
    }
}