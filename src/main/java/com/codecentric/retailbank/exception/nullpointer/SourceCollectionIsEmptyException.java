package com.codecentric.retailbank.exception.nullpointer;

public class SourceCollectionIsEmptyException extends NullPointerException {

    public SourceCollectionIsEmptyException() {
        super();
    }

    public SourceCollectionIsEmptyException(String s) {
        super(s);
    }
}
