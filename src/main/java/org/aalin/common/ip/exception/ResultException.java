package org.aalin.common.ip.exception;

/**
 * 返回结果异常。
 */
public class ResultException extends RuntimeException {

    private static final long serialVersionUID = -6142198086517154408L;

    /**
     * 信息码
     */
    protected String code;

    /**
     * 描述
     */
    protected String description;

    /**
     * 构建一个 ResultException 。
     */
    public ResultException() {
    }

    /**
     * 构建一个 ResultException 。
     *
     * @param code 信息码。
     */
    public ResultException(String code) {
        this(code, null, null, null);
    }

    /**
     * 构建一个 ResultException 。
     *
     * @param code        信息码。
     * @param description 描述。
     */
    public ResultException(String code, String description) {
        this(code, description, null, null);
    }

    /**
     * 构建一个 ResultException 。
     *
     * @param code 信息码。
     * @param t    关联的异常。
     */
    public ResultException(String code, Throwable t) {
        this(code, null, null, t);
    }

    /**
     * 构建一个 ResultException 。
     *
     * @param code    信息码。
     * @param message 异常消息。
     * @param t       关联的异常。
     */
    public ResultException(String code, String message, Throwable t) {
        this(code, null, message, t);
    }

    /**
     * 构建一个 ResultException 。
     *
     * @param code        信息码。
     * @param description 描述。
     * @param message     异常消息。
     * @param t           关联的异常。
     */
    public ResultException(String code, String description, String message, Throwable t) {
        super(message, t);
        this.code = code;
        this.description = description;
    }

    /**
     * 构建一个 ResultException 。
     *
     * @param code               信息码。
     * @param description        描述。
     * @param message            异常消息。
     * @param cause              关联的异常。
     * @param enableSuppression  是否压制原始异常。
     * @param writableStackTrace 是否收集堆栈信息。
     */
    public ResultException(
            String code,
            String description,
            String message,
            Throwable cause,
            boolean enableSuppression,
            boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
        this.description = description;
    }

    /**
     * 得到信息码。
     *
     * @return 信息码。
     */
    public String getCode() {
        return this.code;
    }

    /**
     * 设置信息码。
     *
     * @param code 信息码。
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * 得到描述。
     *
     * @return 描述。
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * 设置描述。
     *
     * @param description 描述。
     */
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public synchronized Throwable fillInStackTrace() {
        return this;
    }
}
