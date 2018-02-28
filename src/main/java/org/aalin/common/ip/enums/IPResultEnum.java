package org.aalin.common.ip.enums;

/**
 * @author shuijing
 */
public enum IPResultEnum {
    LOOKUP_FAILED("LOOKUP_FAILED", "未查到ip相关数据"),

    /**
     * 未知异常
     */
    UN_KNOWN_EXCEPTION("UN_KNOWN_EXCEPTION", "未知异常"),

    /**
     * 执行成功
     */
    EXECUTE_SUCCESS("EXECUTE_SUCCESS", "执行成功");

    /**
     * 枚举值
     */
    private final String code;

    /**
     * 枚举描述
     */
    private final String message;

    /**
     * 构造一个<code>MessageResultEnum</code>枚举对象
     *
     * @param code
     * @param message
     */
    private IPResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
