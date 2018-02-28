package org.aalin.common.ip.exception;

/**
 * @author shuijing
 */
public class AppException extends RuntimeException {
    private static final long serialVersionUID = -6784291639216132019L;
    protected int state;

    public AppException() {
        this.state = 500;
    }

    public AppException(String message) {
        super(message);
        this.state = 500;
    }

    public AppException(String message, int state) {
        super(message);
        this.state = state;
    }

    public int getState() {
        return state;
    }

    public Throwable fillInStackTrace() {
        return this;
    }
}
