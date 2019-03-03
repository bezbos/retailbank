package com.codecentric.retailbank.exception.nullpointer;

public class InvalidOperationException extends NullPointerException{

    /** Use this in case you need to return data when the exception is thrown. **/
    private Object preservedData;

    public InvalidOperationException(String s) {
        super(s);
    }

    public InvalidOperationException(String s, Object object) {
        super(s);
        this.preservedData = object;
    }

    public Object getPreservedData() {
        return preservedData;
    }
}
