package com.codecentric.retailbank.exception.nullpointer;

public class ArgumentNullException extends NullPointerException {

    public ArgumentNullException() {
        super();
    }

    public ArgumentNullException(String s) {
        super(s);
    }
}
