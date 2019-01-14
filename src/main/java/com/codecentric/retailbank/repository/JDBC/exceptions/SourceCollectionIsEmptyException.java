package com.codecentric.retailbank.repository.JDBC.exceptions;

public class SourceCollectionIsEmptyException extends NullPointerException {

    /**
     * Constructs a {@code SourceCollectionIsEmptyException} with no detail message.
     */
    public SourceCollectionIsEmptyException() {
        super();
    }

    /**
     * Constructs a {@code SourceCollectionIsEmptyException} with the specified
     * detail message.
     *
     * @param s the detail message.
     */
    public SourceCollectionIsEmptyException(String s) {
        super(s);
    }
}
