package com.codecentric.retailbank.exception.runtime;

public class InvalidOldPasswordException extends RuntimeException {

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
