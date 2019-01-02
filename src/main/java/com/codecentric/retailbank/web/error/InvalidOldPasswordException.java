package com.codecentric.retailbank.web.error;

public class InvalidOldPasswordException extends RuntimeException {
    private static long serialVersionUID = 5861310537366287163L;

    public InvalidOldPasswordException() {
        super();
    }

    public InvalidOldPasswordException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidOldPasswordException(String message) {
        super(message);
    }

    public InvalidOldPasswordException(Throwable cause) {
        super(cause);
    }
}
