package com.xushu.dt.client.exception;

/**
 * @author xushu
 */
public class DTException extends RuntimeException {
    private static final long serialVersionUID = 3800664248881155143L;

    public DTException(String message) {
        super(message);
    }

    public DTException(String message, Throwable cause) {
        super(message, cause);
    }

}
