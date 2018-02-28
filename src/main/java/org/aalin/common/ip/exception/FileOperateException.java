package org.aalin.common.ip.exception;


/**
 * 文件操作异常。
 */
public class FileOperateException extends RuntimeException {

    /**
     * 版本号
     */
    private static final long serialVersionUID = 8855661878793608943L;

    public FileOperateException() {
        super();
    }

    public FileOperateException(String msg) {
        super(msg);
    }

    public FileOperateException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public FileOperateException(Throwable cause) {
        super(cause);
    }
}
