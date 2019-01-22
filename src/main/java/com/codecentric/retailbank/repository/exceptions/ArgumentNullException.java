package com.codecentric.retailbank.repository.exceptions;

public class ArgumentNullException extends NullPointerException {

    /**
     * Constructs a {@code ArgumentNullException} with no detail message.
     */
    public ArgumentNullException() {
        super();
    }

    /**
     * Constructs a {@code ArgumentNullException} with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public ArgumentNullException(String s) {
        super(s);
    }
}
