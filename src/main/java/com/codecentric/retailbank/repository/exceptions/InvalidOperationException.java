package com.codecentric.retailbank.repository.exceptions;

public class InvalidOperationException extends NullPointerException{

    /**
     * Constructs a {@code InvalidOperationException} with no detail message.
     */
    public InvalidOperationException() {
        super();
    }

    /**
     * Constructs a {@code InvalidOperationException} with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public InvalidOperationException(String s) {
        super(s);
    }
}
