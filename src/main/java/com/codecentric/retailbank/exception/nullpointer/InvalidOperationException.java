package com.codecentric.retailbank.exception.nullpointer;

public class InvalidOperationException extends NullPointerException{

    public InvalidOperationException() {
        super();
    }

    public InvalidOperationException(String s) {
        super(s);
    }
}
