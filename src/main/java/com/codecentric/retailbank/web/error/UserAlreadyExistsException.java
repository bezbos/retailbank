package com.codecentric.retailbank.web.error;

public class UserAlreadyExistsException extends RuntimeException {
    private static long serialVersionUID = 5861310537366287163L;

    public UserAlreadyExistsException() {
        super();
    }

    public UserAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserAlreadyExistsException(String message) {
        super(message);
    }

    public UserAlreadyExistsException(Throwable cause) {
        super(cause);
    }
}
